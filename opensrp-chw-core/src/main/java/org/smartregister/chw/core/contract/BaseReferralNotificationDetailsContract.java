package org.smartregister.chw.core.contract;

import org.smartregister.chw.core.domain.ReferralNotificationItem;

public interface BaseReferralNotificationDetailsContract {

    interface View {
        void setReferralNotificationDetails(ReferralNotificationItem referralNotificationItem);

        void initPresenter();

        void disableMarkAsDoneAction(boolean disable);
    }

    interface Presenter {

        String getClientBaseEntityId();

        void getReferralDetails(String referralTaskId, String notificationType);

        View getView();

        void onReferralDetailsFetched(ReferralNotificationItem referralNotificationItem);

        void showMemberProfile(String baseEntityId);

        void dismissReferralNotification(String referralTaskId);

        void setClientBaseEntityId(String clientBaseEntityId);
    }

    interface Interactor {
        /**
         * Fetch details for the referral
         * @param referralTaskId unique referral task id
         * @param referralType type of referral notification
         */
        void fetchReferralDetails(String referralTaskId, String referralType);

        /**
         * Crete a referral dismissal entry for the provided task id
         * @param referralTaskId referral task id
         */
        void createReferralDismissalEvent(String referralTaskId);
    }
}
