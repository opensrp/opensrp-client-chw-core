package org.smartregister.chw.hf.activity;

import org.smartregister.chw.core.activity.BaseReferralRegister;
import org.smartregister.chw.core.presenter.BaseReferralPresenter;
import org.smartregister.chw.hf.fragment.ReferralRegisterFragment;
import org.smartregister.helper.BottomNavigationHelper;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.Map;

public class ReferralRegisterActivity extends BaseReferralRegister {

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initializePresenter() {
        presenter = new BaseReferralPresenter();
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new ReferralRegisterFragment();
    }

    @Override
    public void startFormActivity(String formName, String entityId, Map<String, String> metaData) {
        // Abstract method implementation
    }

    @Override
    protected void registerBottomNavigation() {
        bottomNavigationHelper = new BottomNavigationHelper();
        bottomNavigationView = findViewById(org.smartregister.R.id.bottom_navigation);
        FamilyRegisterActivity.registerBottomNavigation(bottomNavigationHelper, bottomNavigationView, this);
    }
}
