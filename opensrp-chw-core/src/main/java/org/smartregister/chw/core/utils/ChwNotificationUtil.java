package org.smartregister.chw.core.utils;

import android.content.Context;

import org.smartregister.chw.core.R;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.util.JsonFormUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class ChwNotificationUtil {

    public static String getNotificationDetailsTable(Context context, String notificationType) {
        if (context.getString(R.string.notification_type_sick_child_follow_up).equals(notificationType)) {
            return "ec_sick_child_followup";
        } else if (context.getString(R.string.notification_type_pnc_danger_signs).equals(notificationType)) {
            return "ec_pnc_danger_signs_outcome";
        } else if (context.getString(R.string.notification_type_anc_danger_signs).equals(notificationType)) {
            return "ec_anc_danger_signs_outcome";
        } else if (context.getString(R.string.notification_type_malaria_follow_up).equals(notificationType)) {
            return "ec_malaria_followup_hf";
        } else if (context.getString(R.string.notification_type_family_planning).equals(notificationType)) {
            return "ec_family_planning_update";
        }
        return null;
    }

    public static String getNotificationEventType(Context context, String notificationType) {
        Map<String, String> notificationEventMap = new HashMap<>();
        notificationEventMap.put(context.getString(R.string.notification_type_sick_child_follow_up), CoreConstants.EventType.SICK_CHILD_NOTIFICATION_DISMISSAL);
        notificationEventMap.put(context.getString(R.string.notification_type_pnc_danger_signs), CoreConstants.EventType.PNC_NOTIFICATION_DISMISSAL);
        notificationEventMap.put(context.getString(R.string.notification_type_anc_danger_signs), CoreConstants.EventType.ANC_NOTIFICATION_DISMISSAL);
        notificationEventMap.put(context.getString(R.string.notification_type_malaria_follow_up), CoreConstants.EventType.MALARIA_NOTIFICATION_DISMISSAL);
        notificationEventMap.put(context.getString(R.string.notification_type_family_planning), CoreConstants.EventType.FAMILY_PLANNING_NOTIFICATION_DISMISSAL);

        return notificationEventMap.get(notificationType);
    }

    public static Event createNotificationDismissalBaseEvent(String baseEntityId, String notificationEventType) {
        Event baseEvent = null;
        try {
            baseEvent = (Event) new Event()
                    .withBaseEntityId(baseEntityId)
                    .withEventDate(new Date())
                    .withEventType(notificationEventType)
                    .withFormSubmissionId(JsonFormUtils.generateRandomUUIDString())
                    .withEntityType(null)
                    .withDateCreated(new Date());
        } catch (Exception ex) {
            Timber.e(ex);
        }
        return baseEvent;
    }
}
