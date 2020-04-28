package org.smartregister.chw.core.interactor;

import org.json.JSONObject;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.util.NCUtils;
import org.smartregister.chw.core.contract.CorePncMemberProfileContract;
import org.smartregister.chw.core.dao.PNCDao;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.pnc.interactor.BasePncMemberProfileInteractor;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.repository.AllSharedPreferences;

public class CorePncMemberProfileInteractor extends BasePncMemberProfileInteractor implements CorePncMemberProfileContract.Interactor {

    @Override
    public MemberObject getMemberClient(String memberID) {
        // read all the member details from the database
        return PNCDao.getMember(memberID);
    }

    @Override
    public void createPncDangerSignsOutcomeEvent(AllSharedPreferences allSharedPreferences, String jsonString, String entityID) throws Exception {
        Event baseEvent = org.smartregister.chw.anc.util.JsonFormUtils.processJsonForm(allSharedPreferences, org.smartregister.chw.core.utils.CoreReferralUtils.setEntityId(jsonString, entityID), CoreConstants.TABLE_NAME.PNC_DANGER_SIGNS_OUTCOME);
        NCUtils.processEvent(baseEvent.getBaseEntityId(), new JSONObject(org.smartregister.chw.anc.util.JsonFormUtils.gson.toJson(baseEvent)));
    }
}
