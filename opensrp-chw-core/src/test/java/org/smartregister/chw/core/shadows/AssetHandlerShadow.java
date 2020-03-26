package org.smartregister.chw.core.shadows;

import android.content.Context;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.util.AssetHandler;

@Implements(AssetHandler.class)
public class AssetHandlerShadow {

    @Implementation
    public static String readFileFromAssetsFolder(String fileName, Context context) {
        return "";
    }
}
