package org.smartregister.chw.core.utils;


import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.FamilyLibraryShadowUtil;
import org.smartregister.chw.core.shadows.UtilsShadowUtil;
import org.smartregister.family.FamilyLibrary;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, sdk = 22, shadows = {ContextShadow.class, FamilyLibraryShadowUtil.class, UtilsShadowUtil.class})
public class CoreJsonFormUtilsTest extends BaseUnitTest {

    private JSONObject jsonForm;

    @Mock
    private FamilyLibrary familyLibraryInstance;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        String jsonString = "{\"count\": \"2\",\"metadata\": {},\"step1\": {\"title\": \"Family Registration Info\",\"next\": \"step2\",\"fields\": [] },\"step2\": {\"title\": \"Family Registration Page two\",\"fields\": [] }}";
        try {
            jsonForm = new JSONObject(jsonString);

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Test
    public void getAncPncStartFormIntentReturnsIntent() throws Exception {
        Context context = Mockito.mock(Context.class);
        FamilyLibraryShadowUtil.setInstance(familyLibraryInstance);

        Intent ancPncIntent = CoreJsonFormUtils.getAncPncStartFormIntent(jsonForm, context);
        Assert.assertNotNull(ancPncIntent);
    }

    @Test
    public void getFormStepsReturnsListOfJsonObjectSteps() throws JSONException {
        List<JSONObject> steps = CoreJsonFormUtils.getFormSteps(jsonForm);
        Assert.assertEquals(steps.get(1).optString("title"), "Family Registration Page two");
    }
}
