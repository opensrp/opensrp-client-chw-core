package org.smartregister.chw.core.shadows;

import org.mockito.Mockito;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.chw.pnc.PncLibrary;

@Implements(PncLibrary.class)
public class PncLibraryShadow {

    private static PncLibrary instance = Mockito.mock(PncLibrary.class);

    @Implementation
    public static PncLibrary getInstance() {
        return instance;
    }

    public static void setInstance(PncLibrary instance) {
        PncLibraryShadow.instance = instance;
    }
}
