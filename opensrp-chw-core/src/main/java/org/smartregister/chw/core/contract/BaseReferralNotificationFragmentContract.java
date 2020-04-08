package org.smartregister.chw.core.contract;

import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

import java.util.Set;

public interface BaseReferralNotificationFragmentContract {

    interface Interactor {
    }

    interface Model {
        String countQueryStatement(String childTable, String mainCondition);

        String mainQueryStatement(String taskTable, String entityTable, String mainCondition);
    }

    interface Presenter extends BaseRegisterFragmentContract.Presenter {
    }

    interface InteractorCallBack {
        void clientDetails(CommonPersonObjectClient client);
    }

    interface View extends BaseRegisterFragmentContract.View {
        void initializeAdapter(Set<org.smartregister.configurableviews.model.View> visibleColumns, String tableName);

        void setClient(CommonPersonObjectClient commonPersonObjectClient);
    }
}
