package org.smartregister.chw.core.model;

import android.content.Context;

import org.json.JSONObject;
import org.smartregister.chw.anc.util.JsonFormUtils;
import org.smartregister.chw.core.contract.CoreCertificationRegisterContract;
import org.smartregister.util.FormUtils;

public class CoreCertificationRegisterModel implements CoreCertificationRegisterContract.Model {
    @Override
    public JSONObject getFormAsJson(Context context, String formName, String entityId, String currentLocationId) throws Exception {
        JSONObject jsonObject = FormUtils.getInstance(context).getFormJson(formName);
        JsonFormUtils.getRegistrationForm(jsonObject, entityId, currentLocationId);

        return jsonObject;
    }
}
