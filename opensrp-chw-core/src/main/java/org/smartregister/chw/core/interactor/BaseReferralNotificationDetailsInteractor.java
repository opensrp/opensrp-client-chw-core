package org.smartregister.chw.core.interactor;

import android.app.Activity;
import android.content.Context;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class BaseReferralNotificationDetailsInteractor implements BaseReferralNotificationDetailsContract.Interactor {

    private BaseReferralNotificationDetailsContract.Presenter presenter;
    private Context context;

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
        presenter.setClientBaseEntityId(record.getClientBaseEntityId());

        String title = context.getString(R.string.successful_referral_notification_title,
                record.getClientName(), getClientAge(record.getClientDateOfBirth()));
        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.referral_notification_phone, record.getPhone() != null ? record.getPhone() : context.getString(R.string.no_phone_provided)));
        details.add(context.getString(R.string.referral_notification_closure_date, record.getNotificationDate()));
        if (record.getVillage() != null) {
            details.add(context.getString(R.string.referral_notification_village, record.getVillage()));
        }
        details.add(context.getString(R.string.referral_notification_record_closed));
        return new ReferralNotificationItem(title, details).setClientBaseEntityId(record.getClientBaseEntityId());
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
