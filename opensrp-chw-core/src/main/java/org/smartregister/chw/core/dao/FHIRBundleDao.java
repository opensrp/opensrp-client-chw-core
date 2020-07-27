package org.smartregister.chw.core.dao;

import android.content.Context;
import android.util.Pair;

import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.dao.AbstractDao;
import org.smartregister.thinkmd.model.FHIRBundleModel;

import static org.smartregister.chw.core.dao.ChildDao.getChildProfileData;
import static org.smartregister.chw.core.utils.Utils.fetchMUACValues;
import static org.smartregister.chw.core.utils.Utils.getRandomGeneratedId;

public class FHIRBundleDao extends AbstractDao {

    protected FHIRBundleModel fetchFHIRDateModel(Context context, String childBaseEntityId) {
        FHIRBundleModel model = new FHIRBundleModel();
        model.setRandomlyGeneratedId(getRandomGeneratedId());
        model.setEncounterId(getRandomGeneratedId());
        Pair<String, String> muacPair = fetchMUACValues(childBaseEntityId);
        if (muacPair != null) {
            model.setMUACValueCode(muacPair.first);
            model.setMUACValueDisplay(muacPair.second);
        }
        Triple<String, String, String> userProfile = getChildProfileData(childBaseEntityId);
        if (userProfile != null) {
            model.setGender(userProfile.getRight());
            model.setDob(userProfile.getMiddle());
            model.setAgeInDays(userProfile.getLeft());
        }
        //Todo: these values needs to be query and set into model
        model.setPractitionerId(null);
        model.setPatientId(null);
        model.setUserName(null);
        model.setLocationId(null);
        model.setUniqueIdGeneratedForThinkMD(null);

        return model;
    }

}
