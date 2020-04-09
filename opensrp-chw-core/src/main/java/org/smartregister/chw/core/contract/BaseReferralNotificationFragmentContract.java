package org.smartregister.chw.core.contract;

import org.smartregister.view.contract.BaseRegisterFragmentContract;

public interface BaseReferralNotificationFragmentContract {

    interface Presenter extends BaseRegisterFragmentContract.Presenter {
        void displayDetailsActivity(String baseEntityID, String notificationType);
    }

    interface View extends BaseRegisterFragmentContract.View {
        void initializeAdapter();
    }
}
