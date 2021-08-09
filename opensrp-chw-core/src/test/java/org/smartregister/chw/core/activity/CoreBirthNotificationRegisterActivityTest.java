package org.smartregister.chw.core.activity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.chw.core.presenter.CoreChildRegisterPresenter;

public class CoreBirthNotificationRegisterActivityTest {

    private CoreBirthNotificationRegisterActivity coreBirthNotificationRegisterActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        coreBirthNotificationRegisterActivity = Mockito.mock(CoreBirthNotificationRegisterActivity.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void testStartMeInitiatesActivity() {
        CoreBirthNotificationRegisterActivity.startMe(coreBirthNotificationRegisterActivity);
        Mockito.verify(coreBirthNotificationRegisterActivity).startActivity(Mockito.any());
    }

    @Test
    public void testActivityLoaded() {
        Assert.assertNotNull(coreBirthNotificationRegisterActivity);
    }

    @Test
    public void testInitializePresenter() {
        coreBirthNotificationRegisterActivity.initializePresenter();
        Assert.assertTrue(ReflectionHelpers.getField(coreBirthNotificationRegisterActivity, "presenter") instanceof CoreChildRegisterPresenter);
    }

    @After
    public void tearDown() {
        try {
            coreBirthNotificationRegisterActivity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //logout
        Context.getInstance().session().expire();
        System.gc();
    }

}
