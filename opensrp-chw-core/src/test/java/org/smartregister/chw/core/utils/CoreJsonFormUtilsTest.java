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
        String encounterType = "Remove Family Member", baseEntityId = "16847ab9-5cce-4b88-a666-bb9f8291a4bb";
        CoreLibrary.init(org.smartregister.Context.getInstance());
        JSONObject removeMemberJsonObject = new JSONObject(getRemoveMemberJsonString(encounterType, baseEntityId));
        Triple<Pair<Date, String>, String, List<Event>> eventTriple = CoreJsonFormUtils.processRemoveMemberEvent(null, Utils.getAllSharedPreferences(), removeMemberJsonObject, null);
        Assert.assertNotNull(eventTriple);
        Assert.assertEquals(encounterType, eventTriple.getLeft().second);
        Assert.assertEquals(baseEntityId, eventTriple.getMiddle());
        Assert.assertEquals(1, eventTriple.getRight().size());
    }

    private String getRemoveMemberJsonString(String encounterType, String baseEntityId) {
        return "{\n" +
                "    \"count\": \"1\",\n" +
                "    \"encounter_type\": \"" + encounterType + "\",\n" +
                "    \"entity_id\": \"" + baseEntityId + "\",\n" +
                "    \"relational_id\": \"\",\n" +
                "    \"metadata\": {},\n" +
                "    \"step1\": {\n" +
                "        \"title\": \"Remove Family Member\",\n" +
                "        \"fields\": [\n" +
                "            {\n" +
                "                \"key\": \"details\",\n" +
                "                \"openmrs_entity_parent\": \"\",\n" +
                "                \"openmrs_entity\": \"concept\",\n" +
                "                \"openmrs_entity_id\": \"\",\n" +
                "                \"type\": \"label\",\n" +
                "                \"text\": \"Melissa Yo Jiwanji, 25 Female\",\n" +
                "                \"text_size\": \"25px\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"remove_reason\",\n" +
                "                \"openmrs_entity_parent\": \"\",\n" +
                "                \"openmrs_entity\": \"concept\",\n" +
                "                \"openmrs_entity_id\": \"160417AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\n" +
                "                \"openmrs_data_type\": \"select one\",\n" +
                "                \"type\": \"spinner\",\n" +
                "                \"hint\": \"Reason for removal\",\n" +
                "                \"v_required\": {\n" +
                "                    \"value\": \"true\",\n" +
                "                    \"err\": \"Select the reason for removing the family member's record\"\n" +
                "                },\n" +
                "                \"values\": [\n" +
                "                    \"Death\",\n" +
                "                    \"Moved away\",\n" +
                "                    \"Other\"\n" +
                "                ],\n" +
                "                \"keys\": [\n" +
                "                    \"Death\",\n" +
                "                    \"Moved away\",\n" +
                "                    \"Other\"\n" +
                "                ],\n" +
                "                \"openmrs_choice_ids\": {\n" +
                "                    \"Died\": \"160034AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\n" +
                "                    \"Moved away\": \"160415AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\n" +
                "                    \"Other\": \"5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\"\n" +
                "                },\n" +
                "                \"is-rule-check\": true,\n" +
                "                \"step\": \"step1\",\n" +
                "                \"value\": \"Other\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"dob\",\n" +
                "                \"openmrs_entity_parent\": \"\",\n" +
                "                \"openmrs_entity\": \"concept\",\n" +
                "                \"openmrs_entity_id\": \"\",\n" +
                "                \"type\": \"spacer\",\n" +
                "                \"expanded\": false,\n" +
                "                \"read_only\": \"true\",\n" +
                "                \"hidden\": \"false\",\n" +
                "                \"value\": \"01-04-1994\",\n" +
                "                \"step\": \"step1\",\n" +
                "                \"is-rule-check\": false\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"date_moved\",\n" +
                "                \"openmrs_entity_parent\": \"\",\n" +
                "                \"openmrs_entity\": \"concept\",\n" +
                "                \"openmrs_entity_id\": \"164133AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\n" +
                "                \"openmrs_data_type\": \"text\",\n" +
                "                \"type\": \"date_picker\",\n" +
                "                \"label\": \"Date moved away\",\n" +
                "                \"hint\": \"Date moved away\",\n" +
                "                \"expanded\": false,\n" +
                "                \"min_date\": \"today-9475d\",\n" +
                "                \"max_date\": \"today\",\n" +
                "                \"v_required\": {\n" +
                "                    \"value\": \"true\",\n" +
                "                    \"err\": \"Enter the date that the member moved away\"\n" +
                "                },\n" +
                "                \"is_visible\": false,\n" +
                "                \"is-rule-check\": false\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"date_died\",\n" +
                "                \"openmrs_entity_parent\": \"\",\n" +
                "                \"openmrs_entity\": \"concept\",\n" +
                "                \"openmrs_entity_id\": \"1543AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\n" +
                "                \"openmrs_data_type\": \"text\",\n" +
                "                \"type\": \"date_picker\",\n" +
                "                \"label\": \"Date of death\",\n" +
                "                \"hint\": \"Date of death\",\n" +
                "                \"expanded\": false,\n" +
                "                \"min_date\": \"today-9475d\",\n" +
                "                \"max_date\": \"today\",\n" +
                "                \"v_required\": {\n" +
                "                    \"value\": \"true\",\n" +
                "                    \"err\": \"Enter the date of death\"\n" +
                "                },\n" +
                "                \"step\": \"step1\",\n" +
                "                \"is-rule-check\": false,\n" +
                "                \"is_visible\": false\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"age_at_death\",\n" +
                "                \"openmrs_entity_parent\": \"\",\n" +
                "                \"openmrs_entity\": \"concept\",\n" +
                "                \"openmrs_entity_id\": \"\",\n" +
                "                \"label\": \"Age at death\",\n" +
                "                \"hint\": \"Age at death\",\n" +
                "                \"type\": \"edit_text\",\n" +
                "                \"read_only\": \"true\",\n" +
                "                \"is_visible\": false\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"invisible_required_fields\": \"[date_died, date_moved]\",\n" +
                "    \"details\": {\n" +
                "        \"appVersionName\": \"1.7.23-SNAPSHOT\",\n" +
                "        \"formVersion\": \"\"\n" +
                "    }\n" +
                "}";
    }

}
