package org.smartregister.chw.core.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.ContextShadow;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, shadows = {ContextShadow.class})
public class CoreChildMedicalHistoryActivityInteractorTest {

    @Before
    public void setUp() {

    }

    @Test
    public void canGetServiceRecords() {

    }

    @Test
    public void canGetVisits() {

    }

}
