package org.smartregister.chw.core.shadows;

import android.content.res.AssetManager;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.Context;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.family.activity.FamilyWizardFormActivity;
import org.smartregister.family.domain.FamilyMetadata;
import org.smartregister.family.util.Utils;
import org.smartregister.view.activity.BaseProfileActivity;

import java.util.Locale;

@Implements(Utils.class)
public class UtilsShadowUtil {

    private static FamilyMetadata metadata;

    @Implementation
    public static FamilyMetadata metadata() {
        if (metadata == null) {
            metadata = new FamilyMetadata(FamilyWizardFormActivity.class, FamilyWizardFormActivity.class,
                    BaseProfileActivity.class, CoreConstants.IDENTIFIER.UNIQUE_IDENTIFIER_KEY, false);
        }
        return metadata;
    }

    public static void setMetadata(FamilyMetadata metadata) {
        UtilsShadowUtil.metadata = metadata;
    }

    @Implementation
    public static Context context() {
        return Context.getInstance();
    }

    @Implementation
    public static String getFileName(String form_name, Locale current, AssetManager assetManager) {
        return form_name;
    }

}
