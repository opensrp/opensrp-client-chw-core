package org.smartregister.chw.core.contract;

import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

public interface BaseReferralNotificationFragmentContract {

    interface Interactor {
    }

    interface Presenter extends BaseRegisterFragmentContract.Presenter {
    }

    interface InteractorCallBack {
        void clientDetails(CommonPersonObjectClient client);
    }

    interface View extends BaseRegisterFragmentContract.View {
        void initializeAdapter();

        void setClient(CommonPersonObjectClient commonPersonObjectClient);
    }
}
