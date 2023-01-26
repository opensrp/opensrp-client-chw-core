package org.smartregister.chw.core.activity.impl;

import org.smartregister.chw.core.activity.CoreFamilyRegisterActivity;
import org.smartregister.chw.core.activity.CorePncRegisterActivity;

public class CorePncRegisterActivityTestImpl extends CorePncRegisterActivity {


    @Override
    public Class<? extends CoreFamilyRegisterActivity> getFamilyRegisterActivity() {
        return CoreFamilyRegisterActivity.class;
    }



}
