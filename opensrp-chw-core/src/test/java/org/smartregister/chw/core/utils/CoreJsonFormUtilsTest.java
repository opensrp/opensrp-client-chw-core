package org.smartregister.chw.core.utils;


import android.content.Intent;
import android.util.Pair;

import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.FamilyLibraryShadowUtil;
import org.smartregister.chw.core.shadows.UtilsShadowUtil;
import org.smartregister.clientandeventmodel.Event;

import java.util.Date;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, sdk = 22, shadows = {ContextShadow.class, FamilyLibraryShadowUtil.class, UtilsShadowUtil.class})
public class CoreJsonFormUtilsTest extends BaseUnitTest {

    private JSONObject jsonForm;

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
    public void getAncPncStartFormIntentReturnsIntent() {
        Intent ancPncIntent = CoreJsonFormUtils.getAncPncStartFormIntent(jsonForm, RuntimeEnvironment.application);
        Assert.assertNotNull(ancPncIntent);
    }

    @Test
    public void getFormStepsReturnsListOfJsonObjectSteps() throws JSONException {
        List<JSONObject> steps = CoreJsonFormUtils.getFormSteps(jsonForm);
        Assert.assertEquals(steps.get(1).optString("title"), "Family Registration Page two");
    }

    @Test
    public void processRemoveMemberEventReturnsCorrectTriple() throws JSONException {
        String encounterType = "Remove Family Member";
        String baseEntityId = "16847ab9-5cce-4b88-a666-bb9f8291a4bb";
        CoreLibrary.init(org.smartregister.Context.getInstance());
        JSONObject removeMemberJsonObject = new JSONObject(getRemoveMemberJsonString(encounterType, baseEntityId));
        Triple<Pair<Date, String>, String, List<Event>> eventTriple = CoreJsonFormUtils.processRemoveMemberEvent(null, Utils.getAllSharedPreferences(), removeMemberJsonObject, null);
        Assert.assertNotNull(eventTriple);
        Assert.assertEquals(encounterType, eventTriple.getLeft().second);
        Assert.assertEquals(baseEntityId, eventTriple.getMiddle());
        Assert.assertEquals(1, eventTriple.getRight().size());
    }

    private String getRemoveMemberJsonString(String encounterType, String baseEntityId) {
        return "{\"count\":\"1\",\"encounter_type\":\"" + encounterType + "\",\"entity_id\":\"" + baseEntityId + "\",\"relational_id\":\"\",\"metadata\":{},\"step1\":{\"title\":\"RemoveFamilyMember\",\"fields\":[{\"key\":\"details\",\"openmrs_entity_parent\":\"\",\"openmrs_entity\":\"concept\",\"openmrs_entity_id\":\"\",\"type\":" +
                "\"label\",\"text\":\"MelissaYoJiwanji,25Female\",\"text_size\":\"25px\"},{\"key\":\"remove_reason\",\"openmrs_entity_parent\":\"\",\"openmrs_entity\":\"concept\",\"openmrs_entity_id\":\"160417AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\"openmrs_data_type\":\"selectone\",\"type\":\"spinner\",\"hint\":\"Reasonforremoval\",\"v_required\"" +
                ":{\"value\":\"true\",\"err\":\"Selectthereasonforremovingthefamilymember'srecord\"},\"values\":[\"Death\",\"Movedaway\",\"Other\"],\"keys\":[\"Death\",\"Movedaway\",\"Other\"],\"openmrs_choice_ids\":{\"Died\":\"160034AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\"Movedaway\":\"160415AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\"Other\"" +
                ":\"5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"is-rule-check\":true,\"step\":\"step1\",\"value\":\"Other\"},{\"key\":\"dob\",\"openmrs_entity_parent\":\"\",\"openmrs_entity\":\"concept\",\"openmrs_entity_id\":\"\",\"type\":\"spacer\",\"expanded\":false,\"read_only\":\"true\",\"hidden\":\"false\",\"value\":\"01-04-1994\",\"step\"" +
                ":\"step1\",\"is-rule-check\":false},{\"key\":\"date_moved\",\"openmrs_entity_parent\":\"\",\"openmrs_entity\":\"concept\",\"openmrs_entity_id\":\"164133AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\"openmrs_data_type\":\"text\",\"type\":\"date_picker\",\"label\":\"Datemovedaway\",\"hint\":\"Datemovedaway\",\"expanded\":false,\"min_date\"" +
                ":\"today-9475d\",\"max_date\":\"today\",\"v_required\":{\"value\":\"true\",\"err\":\"Enterthedatethatthemembermovedaway\"},\"is_visible\":false,\"is-rule-check\":false},{\"key\":\"date_died\",\"openmrs_entity_parent\":\"\",\"openmrs_entity\":\"concept\",\"openmrs_entity_id\":\"1543AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\"openmrs_data_type\"" +
                ":\"text\",\"type\":\"date_picker\",\"label\":\"Dateofdeath\",\"hint\":\"Dateofdeath\",\"expanded\":false,\"min_date\":\"today-9475d\",\"max_date\":\"today\",\"v_required\":{\"value\":\"true\",\"err\":\"Enterthedateofdeath\"},\"step\":\"step1\",\"is-rule-check\":false,\"is_visible\":false},{\"key\":\"age_at_death\",\"openmrs_entity_parent\"" +
                ":\"\",\"openmrs_entity\":\"concept\",\"openmrs_entity_id\":\"\",\"label\":\"Ageatdeath\",\"hint\":\"Ageatdeath\",\"type\":\"edit_text\",\"read_only\":\"true\",\"is_visible\":false}]},\"invisible_required_fields\":\"[date_died,date_moved]\",\"details\":{\"appVersionName\":\"1.7.23-SNAPSHOT\",\"formVersion\":\"\"}}";
    }

}
