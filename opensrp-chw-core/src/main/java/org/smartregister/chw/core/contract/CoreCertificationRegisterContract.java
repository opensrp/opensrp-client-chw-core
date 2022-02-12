package org.smartregister.chw.core.contract;

import android.content.Context;

import org.json.JSONObject;
import org.smartregister.view.contract.BaseRegisterContract;

public interface CoreCertificationRegisterContract {

    interface View extends BaseRegisterContract.View {
        Presenter presenter();

        void onRegistrationSaved();
    }

    interface Presenter extends BaseRegisterContract.Presenter {

        View getView();

        void saveForm(String jsonString, boolean isEditMode);
    }

    interface Model {
        JSONObject getFormAsJson(Context context, String formName, String entityId, String currentLocationId) throws Exception;
    }

    interface Interactor {
        void onDestroy(boolean isChangingConfiguration);

        void saveRegistration();
    }

    interface InteractorCallBack {

    }
}
