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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.smartregister.family.FamilyLibrary;
import org.smartregister.family.domain.FamilyMetadata;

import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FamilyLibrary.class, Utils.class})
public class CoreJsonFormUtilsTest {

    private JSONObject jsonForm;

    @Mock
    private FamilyMetadata familyMetadata;

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
        PowerMockito.mockStatic(FamilyLibrary.class);
        PowerMockito.mockStatic(Utils.class);
        PowerMockito.when(FamilyLibrary.getInstance()).thenReturn(familyLibraryInstance);
        PowerMockito.when(Utils.metadata()).thenReturn(familyMetadata);
        PowerMockito.when(familyLibraryInstance.metadata()).thenReturn(familyMetadata);
        Intent ancPncIntent = CoreJsonFormUtils.getAncPncStartFormIntent(jsonForm, context);
        Assert.assertNotNull(ancPncIntent);
    }

    @Test
    public void getFormStepsReturnsListOfJsonObjectSteps() throws JSONException {
        List<JSONObject> steps = CoreJsonFormUtils.getFormSteps(jsonForm);
        Assert.assertEquals(steps.size(), 2);
        Assert.assertEquals(steps.get(0).optString("next"), "step2");
        Assert.assertEquals(steps.get(1).optString("title"), "Family Registration Page two");
    }
}
