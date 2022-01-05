package org.smartregister.chw.core.interactor;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.VisitDaoShadowHelper;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, shadows = {ContextShadow.class, VisitDaoShadowHelper.class})
public class CoreChildRegisterInteractorTest extends BaseUnitTest {

    private CoreChildRegisterInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = Mockito.mock(CoreChildRegisterInteractor.class, Mockito.CALLS_REAL_METHODS);
    }

   /* @Test
    public void testSaveChildRegistrationShouldSaveChildRegistration() {
        Pair<Client, Event> pair = Pair.create(new Client("baseID"), new Event());
        String jsonString = "jsonString";
        CoreChildRegisterContract.InteractorCallBack callBack = Mockito.mock(CoreChildRegisterContract.InteractorCallBack.class);
        interactor.saveRegistration(pair, jsonString, false, callBack);
        Mockito.verify(interactor).saveRegistration(pair, jsonString, false, callBack);
    }*/

}
