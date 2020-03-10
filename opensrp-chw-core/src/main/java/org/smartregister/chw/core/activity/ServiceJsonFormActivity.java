package org.smartregister.chw.core.activity;

import com.vijay.jsonwizard.activities.JsonWizardFormActivity;
import com.vijay.jsonwizard.constants.JsonFormConstants;

import org.smartregister.chw.core.fragment.ServiceJsonFormFragment;

public class ServiceJsonFormActivity extends JsonWizardFormActivity {
    ServiceJsonFormFragment serviceJsonFormFragment;

    @Override
    public void initializeFormFragment() {
        serviceJsonFormFragment = ServiceJsonFormFragment.getFormFragment(JsonFormConstants.FIRST_STEP_NAME);
        getSupportFragmentManager().beginTransaction()
                .add(com.vijay.jsonwizard.R.id.container, serviceJsonFormFragment).commit();
    }
}
