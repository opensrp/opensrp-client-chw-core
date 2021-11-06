package org.smartregister.chw.core.interactor;

import org.jetbrains.annotations.NotNull;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.util.AppExecutors;
import org.smartregister.chw.core.contract.CoreIndexContactProfileContract;
import org.smartregister.chw.hiv.domain.HivIndexContactObject;
import org.smartregister.chw.hiv.interactor.BaseIndexContactProfileInteractor;
import org.smartregister.repository.AllSharedPreferences;

public class CoreHivIndexContactProfileInteractor extends BaseIndexContactProfileInteractor implements CoreIndexContactProfileContract.Interactor {

    public MemberObject toMember(HivIndexContactObject hivIndexContactObject) {
        MemberObject res = new MemberObject();
        res.setBaseEntityId(hivIndexContactObject.getBaseEntityId());
        res.setFirstName(hivIndexContactObject.getFirstName());
        res.setLastName(hivIndexContactObject.getLastName());
        res.setMiddleName(hivIndexContactObject.getMiddleName());
        res.setDob(hivIndexContactObject.getDob());
        return res;
    }

    @Override
    public void createReferralEvent(AllSharedPreferences allSharedPreferences, String jsonString, String entityID) throws Exception {
        //TODO implement this
    }
}