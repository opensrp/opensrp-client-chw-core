package org.smartregister.chw.core.shadows;

import org.mockito.Mockito;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.family.FamilyLibrary;

/**
 * @author rkodev
 */

@Implements(FamilyLibrary.class)
public class FamilyLibraryShadow {

    private static FamilyLibrary instance;

    @Implementation
    public static FamilyLibrary getInstance() {
        if (instance == null) {
            instance = Mockito.mock(FamilyLibrary.class);
        }

        return instance;
    }

    public static void setInstance(FamilyLibrary instance) {
        FamilyLibraryShadow.instance = instance;
    }
}
