package org.smartregister.chw.core.shadows;

import android.content.Context;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.util.AssetHandler;

@Implements(AssetHandler.class)
public class ShadowAssetHandler {

    @Implementation
    public static String readFileFromAssetsFolder(String fileName, Context context) {
       /* if ("special_vaccines.json".equals(fileName)) {
            return VaccineDataUtils.SPECIAL_VACCINES_JSON;
        } else if ("vaccines.json".equals(fileName)) {
            return VaccineDataUtils.VACCINES_JSON;
        }*/

        return "";
    }
}
