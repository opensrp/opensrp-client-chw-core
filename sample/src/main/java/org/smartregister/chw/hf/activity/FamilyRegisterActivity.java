package org.smartregister.chw.hf.activity;

import android.os.Bundle;

import org.smartregister.chw.core.activity.CoreFamilyRegisterActivity;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.hf.BuildConfig;
import org.smartregister.chw.hf.HealthFacilityApplication;
import org.smartregister.chw.hf.fragment.FamilyRegisterFragment;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.Map;

public class FamilyRegisterActivity extends CoreFamilyRegisterActivity {

    @Override
    protected void registerBottomNavigation() {
        super.registerBottomNavigation();

        if (!BuildConfig.SUPPORT_QR)
            bottomNavigationView.getMenu().removeItem(org.smartregister.family.R.id.action_scan_qr);

        if (!BuildConfig.SUPPORT_REPORT)
            bottomNavigationView.getMenu().removeItem(org.smartregister.family.R.id.action_job_aids);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationMenu.getInstance(this, null, null);
        HealthFacilityApplication.getInstance().notifyAppContextChange(); // initialize the language (bug in translation)

        action = getIntent().getStringExtra(CoreConstants.ACTIVITY_PAYLOAD.ACTION);
        if (action != null && action.equals(CoreConstants.ACTION.START_REGISTRATION)) {
            startRegistration();
        }
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new FamilyRegisterFragment();
    }

    @Override
    public void startFormActivity(String s, String s1, Map<String, String> map) {
        //Overridden
    }
}
