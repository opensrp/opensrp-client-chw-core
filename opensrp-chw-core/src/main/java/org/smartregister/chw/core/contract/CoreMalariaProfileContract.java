package org.smartregister.chw.core.contract;

import org.json.JSONObject;
import org.smartregister.chw.malaria.contract.MalariaProfileContract;
import org.smartregister.repository.AllSharedPreferences;

public class CoreMalariaProfileContract {
    public interface View extends MalariaProfileContract.View {
        void startFormActivity(JSONObject formJson);
    }

    public interface Presenter extends MalariaProfileContract.Presenter {
        void startHfMalariaFollowupForm();

        void createHfMalariaFollowupEvent(AllSharedPreferences allSharedPreferences, String jsonString, String entityID, String locationId) throws Exception;
    }

    public interface Interactor extends MalariaProfileContract.Interactor {
        void createHfMalariaFollowupEvent(AllSharedPreferences allSharedPreferences, String jsonString, String entityID, String locationId) throws Exception;
    }
}
