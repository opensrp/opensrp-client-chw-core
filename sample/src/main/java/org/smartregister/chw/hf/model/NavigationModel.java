package org.smartregister.chw.hf.model;

import org.smartregister.chw.core.model.NavigationOption;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.hf.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationModel implements org.smartregister.chw.core.model.NavigationModel.Flavor {
    private List<NavigationOption> navigationOptions = new ArrayList<>();

    @Override
    public List<NavigationOption> getNavigationItems() {
        if (navigationOptions.size() == 0) {
            navigationOptions.add(new NavigationOption(R.mipmap.sidemenu_families, R.mipmap.sidemenu_families_active, R.string.menu_all_clients, CoreConstants.DrawerMenu.ALL_CLIENTS, 0));
            navigationOptions.add(new NavigationOption(R.mipmap.sidemenu_families, R.mipmap.sidemenu_families_active, R.string.menu_all_families, CoreConstants.DrawerMenu.ALL_FAMILIES, 0));
        }

        return navigationOptions;
    }
}
