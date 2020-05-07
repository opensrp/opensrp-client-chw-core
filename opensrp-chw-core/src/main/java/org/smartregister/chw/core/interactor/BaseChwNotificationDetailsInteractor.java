package org.smartregister.chw.core.interactor;

import android.app.Activity;
import android.content.Context;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.contract.ChwNotificationDetailsContract;
import org.smartregister.chw.core.dao.ChwNotificationDao;
import org.smartregister.chw.core.domain.NotificationItem;
import org.smartregister.chw.core.domain.NotificationRecord;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.clientandeventmodel.Obs;
import org.smartregister.family.FamilyLibrary;
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

public class BaseNotificationDetailsInteractor implements ChwNotificationDetailsContract.Interactor {

    private ChwNotificationDetailsContract.Presenter presenter;
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public BaseNotificationDetailsInteractor(ChwNotificationDetailsContract.Presenter presenter) {
        this.presenter = presenter;
        context = (Activity) presenter.getView();
    }

    @Override
    public void fetchNotificationDetails(String baseEntityId, String notificationType) {
        NotificationItem notificationItem = null;

        if (notificationType.equals(context.getString(R.string.notification_type_sick_child_follow_up))) {
            notificationItem = getDetailsForSickChildFollowUp(baseEntityId);
        } else if (notificationType.equals(context.getString(R.string.notification_type_pnc_danger_signs)) ||
                notificationType.contains(context.getString(R.string.notification_type_anc_danger_signs))) {
            notificationItem = getDetailsForPNCAndANCReferrals(); // TODO -> Check if these need splitting
        } else if (notificationType.contains(context.getString(R.string.notification_type_malaria_follow_up))) {
            notificationItem = getDetailsForMalariaReferral();
        } else if (notificationType.contains(context.getString(R.string.notification_type_family_planning))) {
            notificationItem = getDetailsForFamilyPlanningReferral();
        }
        presenter.onNotificationDetailsFetched(notificationItem);
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
    private NotificationItem getDetailsForSickChildFollowUp(String baseEntityId) {
        NotificationRecord notificationRecord = ChwNotificationDao.getSickChildFollowUpRecord(baseEntityId);
        String title = context.getString(R.string.followup_notification_title, notificationRecord.getClientName(), notificationRecord.getVisitDate());
        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.notification_care_giver, notificationRecord.getCareGiverName()));
        details.add(context.getString(R.string.notification_diagnosis, notificationRecord.getDiagnosis()));
        details.add(context.getString(R.string.notification_action_taken, notificationRecord.getActionTaken()));
        details.add(context.getString(R.string.notification_village, notificationRecord.getVillage()));
        return new NotificationItem(title, details);
    }

    @NotNull
    private NotificationItem getDetailsForPNCAndANCReferrals() {
        return new NotificationItem(null, null);
    }

    @NotNull
    private NotificationItem getDetailsForFamilyPlanningReferral() {
        return new NotificationItem(null, null);
    }

    @NotNull
    private NotificationItem getDetailsForMalariaReferral() {
        return new NotificationItem(null, null);
    }
}
