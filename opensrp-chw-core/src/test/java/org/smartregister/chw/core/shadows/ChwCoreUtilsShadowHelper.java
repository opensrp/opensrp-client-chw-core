package org.smartregister.chw.core.shadows;

import android.content.res.AssetManager;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.chw.core.utils.Utils;

import java.util.Locale;

@Implements(Utils.class)
public class ChwCoreUtilsShadowHelper {

    @Implementation
    public static String getLocalForm(String form_name, Locale locale, AssetManager assetManager) {
        return form_name;
    }
}
