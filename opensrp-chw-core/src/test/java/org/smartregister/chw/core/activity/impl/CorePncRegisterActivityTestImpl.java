package org.smartregister.chw.core.activity.impl;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.CoreFamilyRegisterActivity;
import org.smartregister.chw.core.activity.CorePncRegisterActivity;
import org.smartregister.chw.core.contract.NavigationContract;

public class CorePncRegisterActivityTestImpl extends CorePncRegisterActivity {


    @Override
    public Class<? extends CoreFamilyRegisterActivity> getFamilyRegisterActivity() {
        return CoreFamilyRegisterActivity.class;
//        return null;
    }

    @Override
    public void registerBottomNavigation() {
        if(this.bottomNavigationView == null){
            this.bottomNavigationView = findViewById(R.layout.activity_base_register);
        }
        super.registerBottomNavigation();
    }

}
