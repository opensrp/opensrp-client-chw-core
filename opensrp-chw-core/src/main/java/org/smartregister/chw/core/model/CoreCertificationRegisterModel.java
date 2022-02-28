package org.smartregister.chw.core.model;

import static org.smartregister.chw.core.utils.CoreJsonFormUtils.ENCOUNTER_TYPE;

import android.content.Context;

import org.json.JSONObject;
import org.smartregister.chw.core.contract.CoreCertificationRegisterContract;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;

import java.util.Map;

public class CoreCertificationRegisterModel implements CoreCertificationRegisterContract.Model {

    @Override
    public JSONObject getFormAsJson(Context context, String formName, String entityId) throws Exception {
        return CoreJsonFormUtils.getJson(context, formName, entityId);
    }

    @Override
    public JSONObject getEditFormAsJson(Context context, String formName, String updateEventType, String entityId, Map<String, String> valueMap) throws Exception {
        JSONObject formJsonObject = getFormAsJson(context, formName, entityId);
        formJsonObject.put(ENCOUNTER_TYPE, updateEventType);
        CoreJsonFormUtils.populateJsonForm(formJsonObject, valueMap);
        return formJsonObject;
    }
}
