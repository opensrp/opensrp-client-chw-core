package org.smartregister.chw.core.form_data;

import android.content.Context;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

public class NativeFormsDataBinderTest {

    @Test
    public void testGetPrePopulatedFormReturnsCorrectJSON() {
        Context context = RuntimeEnvironment.application;
        String testBaseEntityId = "test-entity-id";
        NativeFormsDataBinder dataBinder = new NativeFormsDataBinder(context, testBaseEntityId);

        JSONObject prepoulatedForm = dataBinder.getPrePopulatedForm("test_form");
        Assert.assertNotNull(prepoulatedForm);

    }

}
