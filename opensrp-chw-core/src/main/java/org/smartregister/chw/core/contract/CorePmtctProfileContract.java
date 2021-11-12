package org.smartregister.chw.core.contract;

import org.json.JSONObject;
import org.smartregister.chw.pmtct.contract.PmtctProfileContract;
import org.smartregister.repository.AllSharedPreferences;

public class CorePmtctProfileContract {
    public interface View extends PmtctProfileContract.View {
        void startFormActivity(JSONObject formJson);
    }

    public interface Presenter extends PmtctProfileContract.Presenter {
        void startHfPmtctFollowupForm();

        void createHfPmtctFollowupEvent(AllSharedPreferences allSharedPreferences, String jsonString, String entityID) throws Exception;
    }

    public interface Interactor extends PmtctProfileContract.Interactor {
        void createHfPmtctFollowupEvent(AllSharedPreferences allSharedPreferences, String jsonString, String entityIDd) throws Exception;
    }
}
