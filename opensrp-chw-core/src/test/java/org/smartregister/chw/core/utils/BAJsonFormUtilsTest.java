package org.smartregister.chw.core.utils;

import android.content.Context;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.shadows.UtilsShadowUtil;
import org.smartregister.commonregistry.CommonPersonObjectClient;

import java.util.HashMap;

@Config(shadows = {UtilsShadowUtil.class})
public class BAJsonFormUtilsTest extends BaseUnitTest {

    @Before
    public void setUp() {
        // Init
    }

    @Test
    public void getAutoJsonEditMemberFormStringReturnsCorrectJSONObject() {
        Context context = RuntimeEnvironment.application;
        HashMap<String, String> detailsMap = new HashMap<>();
        HashMap<String, String> columnMaps = new HashMap<>();

        CommonPersonObjectClient client = new CommonPersonObjectClient("testId", detailsMap, "tester");
        client.setColumnmaps(columnMaps);

        BAJsonFormUtils baJsonFormUtils = new BAJsonFormUtils((CoreChwApplication) context);
        JSONObject resultObject = baJsonFormUtils.getAutoJsonEditMemberFormString("test form", "family_register",
                context, client, Utils.metadata().familyMemberRegister.updateEventType, "familyName", false);
        Assert.assertNotNull(resultObject);
        // Assert object contains expected values
    }
}
