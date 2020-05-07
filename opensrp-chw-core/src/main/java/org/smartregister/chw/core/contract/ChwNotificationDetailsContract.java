package org.smartregister.chw.core.contract;

import android.util.Pair;

import org.smartregister.chw.core.domain.NotificationItem;

public interface NotificationDetailsContract {

    interface View {
        void setNotificationDetails(NotificationItem notificationItem);

        void initPresenter();

        void disableMarkAsDoneAction(boolean disable);
    }

    interface Presenter {

        String getClientBaseEntityId();

        void getNotificationDetails(String baseEntityId, String notificationType);

        View getView();

        void onNotificationDetailsFetched(NotificationItem notificationItem);

        void showMemberProfile();

        void dismissNotification(String baseEntityId, String notificationType);

        void setClientBaseEntityId(String clientBaseEntityId);

        void setNotificationDates(Pair<String, String> dates);

        Pair<String, String> getNotificationDates();
    }

    interface Interactor {
        /**
         * Fetch the notification details
         *
         * @param baseEntityId     unique client baseEntityId
         * @param notificationType type of notification
         */
        void fetchNotificationDetails(String baseEntityId, String notificationType);

        /**
         * Crete a referral dismissal entry for the provided task id
         *
         * @param referralTaskId referral task id
         */
        void createReferralDismissalEvent(String referralTaskId);
    }
}
