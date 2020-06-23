package org.smartregister.chw.core.shadows;

import android.os.Bundle;

import org.smartregister.chw.core.activity.BaseReferralTaskViewActivity;

public class ShadowReferralTaskViewActivity extends BaseReferralTaskViewActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            extraClientTask();
            extraDetails();
            inflateToolbar();
        }
    }

    @Override
    protected void onCreation() {

    }

    @Override
    protected void onResumption() {

    }
}
