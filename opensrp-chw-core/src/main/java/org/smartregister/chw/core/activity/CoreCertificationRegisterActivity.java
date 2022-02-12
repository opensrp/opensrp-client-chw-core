package org.smartregister.chw.core.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.CoreCertificationRegisterContract;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.fragment.CoreCertificationRegisterFragment;
import org.smartregister.chw.core.listener.CoreBottomNavigationListener;
import org.smartregister.chw.core.model.CoreCertificationRegisterModel;
import org.smartregister.chw.core.presenter.CoreCertificationRegisterPresenter;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.family.util.Constants;
import org.smartregister.family.util.JsonFormUtils;
import org.smartregister.family.util.Utils;
import org.smartregister.helper.BottomNavigationHelper;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.Map;

import timber.log.Timber;

public abstract class CoreCertificationRegisterActivity extends BaseRegisterActivity implements CoreCertificationRegisterContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationMenu.getInstance(this, null, null);

        String action = getIntent().getStringExtra(CoreConstants.ACTIVITY_PAYLOAD.ACTION);

        if (StringUtils.isNotBlank(action))
            startUpdateFormActivity();
    }

    @Override
    protected void registerBottomNavigation() {

        bottomNavigationHelper = new BottomNavigationHelper();
        bottomNavigationView = findViewById(org.smartregister.R.id.bottom_navigation);

        if (bottomNavigationView != null) {
            bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            bottomNavigationView.getMenu().removeItem(R.id.action_clients);
            bottomNavigationView.getMenu().removeItem(R.id.action_register);
            bottomNavigationView.getMenu().removeItem(R.id.action_search);
            bottomNavigationView.getMenu().removeItem(R.id.action_library);

            bottomNavigationView.inflateMenu(R.menu.bottom_nav_family_menu);

            bottomNavigationHelper.disableShiftMode(bottomNavigationView);

            CoreBottomNavigationListener childBottomNavigationListener = new CoreBottomNavigationListener(this);
            bottomNavigationView.setOnNavigationItemSelectedListener(childBottomNavigationListener);
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new CoreCertificationRegisterPresenter(this, new CoreCertificationRegisterModel());
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new CoreCertificationRegisterFragment();
    }

    @Override
    protected Fragment[] getOtherFragments() {
        return new Fragment[0];
    }


    public abstract void startUpdateFormActivity();

    @Override
    public void startFormActivity(String s, String s1, Map<String, String> map) {
        // Empty code block
    }

    public abstract String getFormTitle();

    @Override
    public void startFormActivity(JSONObject jsonForm) {
        Intent intent = new Intent(this, Utils.metadata().familyMemberFormActivity);
        intent.putExtra(org.smartregister.family.util.Constants.JSON_FORM_EXTRA.JSON, jsonForm.toString());

        Form form = new Form();
        form.setHideSaveLabel(true);
        form.setName(getFormTitle());
        form.setActionBarBackground(org.smartregister.chw.core.R.color.family_actionbar);
        form.setNavigationBackground(org.smartregister.chw.core.R.color.family_navigation);
        form.setHomeAsUpIndicator(org.smartregister.chw.core.R.mipmap.ic_cross_white);

        intent.putExtra(JsonFormConstants.JSON_FORM_KEY.FORM, form);
        startActivityForResult(intent, JsonFormUtils.REQUEST_CODE_GET_JSON);
    }

    @Override
    protected void onActivityResultExtended(int requestCode, int resultCode, Intent data) {
        if (requestCode == JsonFormUtils.REQUEST_CODE_GET_JSON && resultCode == RESULT_OK) {
            try {
                String jsonString = data.getStringExtra(Constants.JSON_FORM_EXTRA.JSON);
                Timber.d("JSONResult : %s", jsonString);

                assert jsonString != null;
                JSONObject form = new JSONObject(jsonString);
                if (form.getString(JsonFormUtils.ENCOUNTER_TYPE).equals("")
                ) {
                    presenter().saveForm(jsonString, false);
                }
            } catch (Exception e) {
                Timber.e(e);
            }
        }
    }

    @Override
    public CoreCertificationRegisterContract.Presenter presenter() {
        return (CoreCertificationRegisterContract.Presenter) presenter;
    }

    @Override
    public void onRegistrationSaved() {
        // Post processing
    }


    @Override
    public void startRegistration() {
        // Empty code block
    }
}
