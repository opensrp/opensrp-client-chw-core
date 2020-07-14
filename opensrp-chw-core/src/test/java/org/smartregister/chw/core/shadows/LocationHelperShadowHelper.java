package org.smartregister.chw.core.shadows;

import android.content.Context;
import android.util.AttributeSet;

import org.mockito.Mockito;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.location.helper.LocationHelper;

import java.util.ArrayList;

@Implements(LocationHelper.class)
public class LocationHelperShadowHelper {

    @Implementation
    public static LocationHelper getInstance() {
       return Mockito.mock(LocationHelper.class);
    }


    @Implementation
    public String getOpenMrsLocationId(String locationName) {
        return "test_location_id";
    }
}
