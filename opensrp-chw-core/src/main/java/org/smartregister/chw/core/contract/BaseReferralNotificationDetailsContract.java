package org.smartregister.chw.core.contract;

import org.smartregister.chw.core.domain.ReferralNotificationItem;

public interface BaseReferralNotificationDetailsContract {

    interface View {
        void onReferralDetailsFetched(ReferralNotificationItem referralNotificationItem);

        void initPresenter();
    }

    interface Presenter {
        void getReferralDetails(String referralTaskId, String notificationType);

        View getView();
    }

    interface Interactor {
        void fetchReferralDetails(String referralTaskId, String referralType);
    }
}
