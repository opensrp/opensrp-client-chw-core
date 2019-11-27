package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;

import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.dao.MalariaDao;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.malaria.activity.BaseMalariaRegisterActivity;
import org.smartregister.family.util.JsonFormUtils;
import org.smartregister.family.util.Utils;
import org.smartregister.job.SyncServiceJob;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

import static org.smartregister.chw.core.utils.CoreConstants.ENTITY_ID;
import static org.smartregister.chw.core.utils.CoreConstants.JSON_FORM.isMultiPartForm;
import static org.smartregister.chw.malaria.util.JsonFormUtils.validateParameters;
import static org.smartregister.util.JsonFormUtils.VALUE;
import static org.smartregister.util.JsonFormUtils.getFieldJSONObject;

public abstract class CoreFpRegisterActivity extends BaseFpRegisterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationMenu.getInstance(this, null, null);
    }

    @Override
    public void startFormActivity(JSONObject jsonForm) {

        Intent intent = new Intent(this, Utils.metadata().familyMemberFormActivity);
        intent.putExtra(org.smartregister.family.util.Constants.JSON_FORM_EXTRA.JSON, jsonForm.toString());

        Form form = new Form();
        form.setActionBarBackground(R.color.family_actionbar);
        form.setWizard(false);
        form.setHomeAsUpIndicator(R.mipmap.ic_cross_white);
        form.setSaveLabel(getString(R.string.submit));

        if (isMultiPartForm(jsonForm)) {
            form.setWizard(true);
            form.setNavigationBackground(R.color.family_navigation);
            form.setName(this.getString(R.string.malaria_registration));
            form.setNextLabel(this.getResources().getString(R.string.next));
            form.setPreviousLabel(this.getResources().getString(R.string.back));
        }
        intent.putExtra(JsonFormConstants.JSON_FORM_KEY.FORM, form);
        startActivityForResult(intent, JsonFormUtils.REQUEST_CODE_GET_JSON);
    }

    @Override
    public List<String> getViewIdentifiers() {
        return Collections.singletonList(CoreConstants.CONFIGURATION.MALARIA_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == org.smartregister.chw.malaria.util.Constants.REQUEST_CODE_GET_JSON) {
            String jsonString = data.getStringExtra(org.smartregister.chw.malaria.util.Constants.JSON_FORM_EXTRA.JSON);
            try {
                JSONObject form = new JSONObject(jsonString);
                Triple<Boolean, JSONObject, JSONArray> registrationFormParams = validateParameters(form.toString());
                JSONObject jsonForm = registrationFormParams.getMiddle();
                String encounter_type = jsonForm.optString(org.smartregister.chw.malaria.util.Constants.JSON_FORM_EXTRA.ENCOUNTER_TYPE);

                if (org.smartregister.chw.malaria.util.Constants.EVENT_TYPE.MALARIA_FOLLOW_UP_VISIT.equals(encounter_type)) {
                    JSONArray fields = registrationFormParams.getRight();
                    JSONObject fever_still_object = getFieldJSONObject(fields, "fever_still");
                    if (fever_still_object != null && "No".equalsIgnoreCase(fever_still_object.optString(VALUE))) {
                        MalariaDao.closeMemberFromRegister(jsonForm.optString(ENTITY_ID));

                    }
                }
                startRegisterActivity(getMalariaRegisterActivity().getClass());

            } catch (JSONException e) {
                Timber.e(e);
            }

        } else {
            finish();
        }

    }

    protected abstract Activity getMalariaRegisterActivity();

    private void startRegisterActivity(Class registerClass) {
        SyncServiceJob.scheduleJobImmediately(SyncServiceJob.TAG);
        Intent intent = new Intent(this, registerClass);
        this.startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        this.finish();
    }

    @Override
    protected void onResumption() {
        super.onResumption();
        NavigationMenu menu = NavigationMenu.getInstance(this, null, null);
        if (menu != null) {
            menu.getNavigationAdapter().setSelectedView(CoreConstants.DrawerMenu.MALARIA);
        }
    }
}