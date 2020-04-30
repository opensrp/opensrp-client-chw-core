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
    }
}
