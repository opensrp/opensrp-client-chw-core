package org.smartregister.chw.hf.custom_view;

import org.apache.commons.lang3.tuple.Pair;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.utils.CoreConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class HfNavigationMenu implements NavigationMenu.Flavour {
    @Override
    public List<Pair<String, Locale>> getSupportedLanguages() {
        return Arrays.asList(Pair.of("English", Locale.ENGLISH), Pair.of("Kiswahili", new Locale("sw")));
    }

    @Override
    public HashMap<String, String> getTableMapValues() {
        HashMap<String, String> tableMap = new HashMap<>();
        tableMap.put(CoreConstants.DrawerMenu.REFERRALS, CoreConstants.TABLE_NAME.TASK);
        return tableMap;
    }

    public boolean hasServiceReport() {
        return false;
    }

    @Override
    public boolean hasStockReport() {
        return false;
    }
}
