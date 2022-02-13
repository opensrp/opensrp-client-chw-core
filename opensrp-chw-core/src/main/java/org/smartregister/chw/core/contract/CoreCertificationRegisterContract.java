package org.smartregister.chw.core.contract;

import android.content.Context;

import org.json.JSONObject;
import org.smartregister.view.contract.BaseRegisterContract;

import java.util.Map;

public interface CoreCertificationRegisterContract {

    interface View extends BaseRegisterContract.View {
        Presenter presenter();

        void onRegistrationSaved();
    }

    interface Presenter extends BaseRegisterContract.Presenter {

        View getView();

        void startCertificationForm(String formName, String entityId) throws Exception;

        void startEditCertForm(String formName, String updateEventType, String entityId, Map<String, String> valueMap) throws Exception;

        void saveForm(String jsonString, String table);
    }

    interface Model {
        JSONObject getFormAsJson(Context context, String formName, String entityId) throws Exception;

        JSONObject getEditFormAsJson(Context context, String formName, String updateEventType, String entityId, Map<String, String> valueMap) throws Exception;
    }

    interface Interactor {
        void onDestroy(boolean isChangingConfiguration);

        void saveRegistration(final String jsonString, String table, InteractorCallBack callBack);
    }

    interface InteractorCallBack {
        void onRegistrationSaved();

    }
}
