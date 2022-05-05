package org.smartregister.chw.core.activity;

import android.os.Bundle;

import org.json.JSONObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.FormUtils;
import org.smartregister.chw.ld.activity.BaseLDRegisterActivity;
import org.smartregister.family.util.JsonFormUtils;

/**
 * Created by cozej4 on 05/05/2022.
 */
public class CoreLDRegisterActivity extends BaseLDRegisterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationMenu.getInstance(this, null, null);
    }

    @Override
    protected void onResumption() {
        super.onResumption();
        NavigationMenu menu = NavigationMenu.getInstance(this, null, null);
        if (menu != null) {
            menu.getNavigationAdapter().setSelectedView(CoreConstants.DrawerMenu.LD);
        }
    }

    @Override
    public void startFormActivity(JSONObject jsonForm) {
        startActivityForResult(FormUtils.getStartFormActivity(jsonForm, this.getString(R.string.ld), this), JsonFormUtils.REQUEST_CODE_GET_JSON);
    }
}
