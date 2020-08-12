package org.smartregister.chw.core.form_data;

import android.content.Context;
import android.os.Build;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.FormUtilsShadowHelper;
import org.smartregister.chw.core.shadows.LocationHelperShadowHelper;
import org.smartregister.chw.core.shadows.LocationPickerViewShadowHelper;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, sdk = Build.VERSION_CODES.P, shadows = {ContextShadow.class,
        FormUtilsShadowHelper.class,  LocationHelperShadowHelper.class, LocationPickerViewShadowHelper.class})
public class NativeFormsDataBinderTest {

    @Test
    public void testGetPrePopulatedFormReturnsCorrectJSON() {
        Context context = RuntimeEnvironment.application;
        String testBaseEntityId = "test-entity-id";

        NativeFormsDataBinder dataBinder = new NativeFormsDataBinder(context, testBaseEntityId);

        JSONObject prepoulatedForm = dataBinder.getPrePopulatedForm("child_enrollment");
        Assert.assertNotNull(prepoulatedForm);

    }

}
