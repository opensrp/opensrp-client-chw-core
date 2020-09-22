package org.smartregister.chw.core.interactor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.ContextShadow;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, shadows = {ContextShadow.class})
public class CoreChildMedicalHistoryActivityInteractorTest extends BaseUnitTest {

    private CoreChildMedicalHistoryActivityInteractor interactor;

    @Before
    public void setUp() {
        interactor = Mockito.mock(CoreChildMedicalHistoryActivityInteractor.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void getVisitsReturnsCorrectList() {
        List<Visit> visits = interactor.getVisits("memberID");
        Assert.assertNotNull(visits);
    }

}
