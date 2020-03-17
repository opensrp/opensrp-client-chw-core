package org.smartregister.chw.core.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.json.JSONObject;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.fragment.CoreAllClientsRegisterFragment;
import org.smartregister.chw.core.presenter.AllClientsRegisterPresenter;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.opd.activity.BaseOpdRegisterActivity;
import org.smartregister.opd.contract.OpdRegisterActivityContract;
import org.smartregister.opd.presenter.BaseOpdRegisterActivityPresenter;
import org.smartregister.view.fragment.BaseRegisterFragment;

public class CoreAllClientsRegisterActivity extends BaseOpdRegisterActivity {

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new CoreAllClientsRegisterFragment();
    }

    @Override
    public void startFormActivity(JSONObject jsonObject) {
        //Overridden from the extended abstract class - feature not required for HF app
    }

    @Override
    protected void onActivityResultExtended(int i, int i1, Intent intent) {
        //Overridden from the extended abstract class - feature not required for HF app
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationMenu.getInstance(this, null, null);
    }

    @Override
    protected BaseOpdRegisterActivityPresenter createPresenter(@NonNull OpdRegisterActivityContract.View view, @NonNull OpdRegisterActivityContract.Model model) {
        return new AllClientsRegisterPresenter(view, model);
    }

    @Override
    protected void onResumption() {
        super.onResumption();
        NavigationMenu menu = NavigationMenu.getInstance(this, null, null);
        if (menu != null) {
            menu.getNavigationAdapter()
                    .setSelectedView(CoreConstants.DrawerMenu.ALL_CLIENTS);
        }
    }

    @Override
    public void switchToBaseFragment() {
        Intent intent = new Intent(this, CoreFamilyRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void startRegistration() {
        //Overridden from the abstract class - registration feature not required for HF app
    }
}
