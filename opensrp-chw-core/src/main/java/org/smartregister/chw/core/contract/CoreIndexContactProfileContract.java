package org.smartregister.chw.core.contract;

import org.json.JSONObject;
import org.smartregister.chw.hiv.contract.BaseIndexContactProfileContract;
import org.smartregister.chw.hiv.domain.HivIndexContactObject;
import org.smartregister.repository.AllSharedPreferences;

public interface CoreIndexContactProfileContract {

    interface View extends BaseIndexContactProfileContract.View {
        void startFormActivity(JSONObject formJson, HivIndexContactObject hivIndexContactObject, String formName);
    }

    interface Presenter extends BaseIndexContactProfileContract.Presenter {
        void createReferralEvent(AllSharedPreferences allSharedPreferences, String jsonString) throws Exception;

        void startHivReferral();
    }

    interface Interactor extends BaseIndexContactProfileContract.Interactor {
        void createReferralEvent(AllSharedPreferences allSharedPreferences, String jsonString, String entityID) throws Exception;
    }
}
