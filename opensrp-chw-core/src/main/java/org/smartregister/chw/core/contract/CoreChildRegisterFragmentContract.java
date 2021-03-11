package org.smartregister.chw.core.contract;

import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

public interface CoreChildRegisterFragmentContract {

    interface View extends BaseRegisterFragmentContract.View {

        Presenter presenter();

    }

    interface Presenter extends BaseRegisterFragmentContract.Presenter {

        String getMainCondition(String tableName);

    }

    interface Model extends BaseRegisterFragmentContract.Model {

        RegisterConfiguration defaultRegisterConfiguration();

        String countSelect(String tableName, String mainCondition, String familyMemberTableName);

        String mainSelect(String tableName, String familyTableName, String familyMemberTableName, String mainCondition);

    }

}
