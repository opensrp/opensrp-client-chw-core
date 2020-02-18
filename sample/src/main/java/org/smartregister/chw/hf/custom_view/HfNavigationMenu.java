package org.smartregister.chw.hf.custom_view;

import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.utils.CoreConstants;

import java.util.HashMap;

public class HfNavigationMenu implements NavigationMenu.Flavour {
    @Override
    public String[] getSupportedLanguages() {
        return new String[]{"English", "Kiswahili"};
    }

    @Override
    public HashMap<String, String> getTableMapValues() {
        HashMap<String, String> tableMap = new HashMap<>();
        tableMap.put(CoreConstants.DrawerMenu.REFERRALS,  CoreConstants.TABLE_NAME.TASK);
        return tableMap;
    }
}
