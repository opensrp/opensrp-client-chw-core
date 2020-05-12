package org.smartregister.chw.core.interactor;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;
import org.smartregister.chw.core.dao.ReferralNotificationDao;
import org.smartregister.chw.core.domain.ReferralNotificationItem;
import org.smartregister.chw.core.domain.ReferralNotificationRecord;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.clientandeventmodel.Obs;
import org.smartregister.family.FamilyLibrary;
import org.smartregister.family.util.Utils;
import org.smartregister.opd.utils.OpdUtils;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.sync.helper.ECSyncHelper;
import org.smartregister.util.JsonFormUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class BaseReferralNotificationDetailsInteractor implements BaseReferralNotificationDetailsContract.Interactor {

    private BaseReferralNotificationDetailsContract.Presenter presenter;
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public BaseReferralNotificationDetailsInteractor(BaseReferralNotificationDetailsContract.Presenter presenter) {
        this.presenter = presenter;
        context = (Activity) presenter.getView();
    }

    @Override
    public void fetchReferralDetails(String referralTaskId, String referralType) {
        ReferralNotificationItem referralNotificationItem = null;

        if (referralType.contains(context.getString(R.string.referral_notification_type_successful))) {
            referralNotificationItem = getDetailsForSuccessfulReferral(referralTaskId);
        } else if (referralType.contains(context.getString(R.string.referral_notification_type_pnc)) ||
                referralType.contains(context.getString(R.string.referral_notification_type_anc))) {
            referralNotificationItem = getDetailsForPNCAndANCReferrals();
        } else if (referralType.contains(context.getString(R.string.referral_notification_type_malaria))) {
            referralNotificationItem = getDetailsForMalariaReferral();
        } else if (referralType.contains(context.getString(R.string.referral_notification_type_fp))) {
            referralNotificationItem = getDetailsForFamilyPlanningReferral();
        } else if (referralType.contains(context.getString(R.string.referral_notification_type_not_yet_done_referrals))) {
            referralNotificationItem = getDetailsForNotYetDoneReferral(referralTaskId);
        }
        presenter.onReferralDetailsFetched(referralNotificationItem);
    }

    @Override
    public void createReferralDismissalEvent(String referralTaskId) {
        try {
            AllSharedPreferences sharedPreferences = CoreChwApplication.getInstance().getContext().allSharedPreferences();
            ECSyncHelper syncHelper = FamilyLibrary.getInstance().getEcSyncHelper();
            String userLocationId = sharedPreferences.fetchUserLocalityId(sharedPreferences.fetchRegisteredANM());
            Event baseEvent = (Event) new Event()
                    .withBaseEntityId(presenter.getClientBaseEntityId())
                    .withEventDate(new Date())
                    .withEventType(CoreConstants.EventType.REFERRAL_DISMISSAL)
                    .withFormSubmissionId(JsonFormUtils.generateRandomUUIDString())
                    .withEntityType(CoreConstants.TABLE_NAME.REFERRAL_DISMISSAL)
                    .withProviderId(sharedPreferences.fetchRegisteredANM())
                    .withLocationId(userLocationId)
                    .withTeamId(sharedPreferences.fetchDefaultTeamId(sharedPreferences.fetchRegisteredANM()))
                    .withTeam(sharedPreferences.fetchDefaultTeam(sharedPreferences.fetchRegisteredANM()))
                    .withDateCreated(new Date());

            baseEvent.addObs((new Obs())
                    .withFormSubmissionField(CoreConstants.FORM_CONSTANTS.FORM_SUBMISSION_FIELD.REFERRAL_TASK)
                    .withValue(referralTaskId)
                    .withFieldCode(CoreConstants.FORM_CONSTANTS.FORM_SUBMISSION_FIELD.REFERRAL_TASK)
                    .withFieldType(CoreConstants.FORMSUBMISSION_FIELD).withFieldDataType(CoreConstants.TEXT)
                    .withParentCode("").withHumanReadableValues(new ArrayList<>()));

            baseEvent.addObs((new Obs())
                    .withFormSubmissionField(CoreConstants.FORM_CONSTANTS.FORM_SUBMISSION_FIELD.NOTIFICATION_DATE_CREATED)
                    .withValue(presenter.getNotificationDates().first)
                    .withFieldCode(CoreConstants.FORM_CONSTANTS.FORM_SUBMISSION_FIELD.NOTIFICATION_DATE_CREATED)
                    .withFieldType(CoreConstants.FORMSUBMISSION_FIELD).withFieldDataType(CoreConstants.DATE)
                    .withParentCode("").withHumanReadableValues(new ArrayList<>()));

            baseEvent.addObs((new Obs())
                    .withFormSubmissionField(CoreConstants.FORM_CONSTANTS.FORM_SUBMISSION_FIELD.NOTIFICATION_DISMISSAL_DATE)
                    .withValue(presenter.getNotificationDates().second)
                    .withFieldCode(CoreConstants.FORM_CONSTANTS.FORM_SUBMISSION_FIELD.NOTIFICATION_DISMISSAL_DATE)
                    .withFieldType(CoreConstants.FORMSUBMISSION_FIELD).withFieldDataType(CoreConstants.DATE)
                    .withParentCode("").withHumanReadableValues(new ArrayList<>()));

            CoreJsonFormUtils.tagSyncMetadata(sharedPreferences, baseEvent);

            baseEvent.setLocationId(userLocationId);

            JSONObject eventJson = new JSONObject(JsonFormUtils.gson.toJson(baseEvent));
            syncHelper.addEvent(referralTaskId, eventJson);
            long lastSyncTimeStamp = sharedPreferences.fetchLastUpdatedAtDate(0);
            Date lastSyncDate = new Date(lastSyncTimeStamp);
            List<String> formSubmissionIds = new ArrayList<>();
            formSubmissionIds.add(baseEvent.getFormSubmissionId());
            CoreChwApplication.getInstance().getClientProcessorForJava().processClient(syncHelper.getEvents(formSubmissionIds));
            sharedPreferences.saveLastUpdatedAtDate(lastSyncDate.getTime());
        } catch (Exception e) {
            Timber.e(e, "BaseReferralNotificationDetailsInteractor --> createReferralDismissalEvent");
        }
    }

    @NotNull
    private ReferralNotificationItem getDetailsForSuccessfulReferral(String referralTaskId) {
        ReferralNotificationRecord record = ReferralNotificationDao.getSuccessfulReferral(referralTaskId);
        List<String> details = setNotificationRecordDetails(record);
        details.add(context.getString(R.string.referral_notification_record_closed));
        String title = context.getString(R.string.successful_referral_notification_title,
                record.getClientName(), getClientAge(record.getClientDateOfBirth()));
        return new ReferralNotificationItem(title, details).setClientBaseEntityId(record.getClientBaseEntityId());
    }

    /**
     * This method is used to obtain the date when the referral will be dismissed from the updates
     * register
     *
     * @param eventCreationDate date the referral was created
     * @return new date returned after adding 3 to the provided date
     */
    private String getDismissalDate(String eventCreationDate) {

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(eventCreationDate));
        } catch (ParseException e) {
            Timber.e(e);
        }
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        return dateFormat.format(calendar.getTime());
    }

    @NotNull
    private ReferralNotificationItem getDetailsForPNCAndANCReferrals() {
        String title = context.getString(R.string.followup_referral_notification_title, "Victoria Rubadiri", "10 Jan 2019");
        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.referral_notification_danger_sign, "Difficulty in breathing"));
        details.add(context.getString(R.string.referral_notification_action_taken, "Nothing"));
        details.add(context.getString(R.string.referral_notification_village, "Miami Beach"));
        return new ReferralNotificationItem(title, details);
    }

    @NotNull
    private ReferralNotificationItem getDetailsForFamilyPlanningReferral() {
        String title = context.getString(R.string.followup_referral_notification_title, "Victoria Rubadiri", "10 Jan 2019");
        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.referral_notification_danger_sign, "Difficulty in breathing"));
        details.add(context.getString(R.string.referral_notification_action_taken, "Nothing"));
        details.add(context.getString(R.string.referral_notification_village, "Beketa Market"));
        return new ReferralNotificationItem(title, details);
    }

    @NotNull
    private ReferralNotificationItem getDetailsForNotYetDoneReferral(String referralTaskId) {
        ReferralNotificationRecord record = ReferralNotificationDao.getNotYetDoneReferral(referralTaskId);
        List<String> details = setNotificationRecordDetails(record);
        details.add(context.getString(R.string.referral_notification_record_not_yet_done));
        String title = context.getString(R.string.successful_referral_notification_title,
                record.getClientName(), getClientAge(record.getClientDateOfBirth()));
        return new ReferralNotificationItem(title, details).setClientBaseEntityId(record.getClientBaseEntityId());
    }

    private List<String> setNotificationRecordDetails(ReferralNotificationRecord record){
        presenter.setClientBaseEntityId(record.getClientBaseEntityId());

        Pair<String, String> notificationDatesPair = null;
        String notificationDate = record.getNotificationDate();
        try {
            notificationDate = dateFormat.format(dateFormat.parse(record.getNotificationDate()));
            notificationDatesPair = Pair.create(notificationDate, getDismissalDate(dateFormat.format(new Date())));
        } catch (ParseException e) {
            Timber.e(e, "Error Parsing date: %s", record.getNotificationDate());
        }
        presenter.setNotificationDates(notificationDatesPair);

        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.referral_notification_phone, record.getPhone() != null ? record.getPhone() : context.getString(R.string.no_phone_provided)));
        details.add(context.getString(R.string.referral_notification_closure_date, notificationDate));
        if (record.getVillage() != null) {
            details.add(context.getString(R.string.referral_notification_village, record.getVillage()));
        }
        return details;
    }

    @NotNull
    private ReferralNotificationItem getDetailsForMalariaReferral() {
        String title = context.getString(R.string.followup_referral_notification_title, "Victoria Rubadiri", "10 Jan 2019");
        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.referral_notification_selected_method, "Male condoms"));
        details.add(context.getString(R.string.referral_notification_village, "Somba Bay"));
        return new ReferralNotificationItem(title, details);
    }

    private String getClientAge(String dobString) {
        String translatedYearInitial = context.getResources().getString(R.string.abbrv_years);
        return OpdUtils.getClientAge(Utils.getDuration(dobString), translatedYearInitial);
    }
}
