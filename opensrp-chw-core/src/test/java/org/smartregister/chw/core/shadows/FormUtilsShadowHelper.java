package org.smartregister.chw.core.shadows;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.util.FormUtils;

@Implements(FormUtils.class)
public class FormUtilsShadowHelper {

    public void __constructor__(Context context) {
        //do nothing
    }

    @Implementation
    public JSONObject getFormJson(String formIdentity) throws JSONException {
        return new JSONObject("{" + org.smartregister.family.util.JsonFormUtils.METADATA+ " : {}}");
    }
}
