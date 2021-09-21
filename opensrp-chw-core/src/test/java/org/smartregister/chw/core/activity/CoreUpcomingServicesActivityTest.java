package org.smartregister.chw.core.activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.presenter.BaseAncUpcomingServicesPresenter;
import org.smartregister.chw.anc.util.AppExecutors;
import org.smartregister.chw.core.interactor.CoreChildUpcomingServiceInteractor;

import java.util.concurrent.Executor;


public class CoreUpcomingServicesActivityTest {
    private CoreUpcomingServicesActivity coreUpcomingServicesActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        coreUpcomingServicesActivity = Mockito.mock(CoreUpcomingServicesActivity.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void testStartMeInitiatesActivity() {
        MemberObject memberObject = Mockito.mock(MemberObject.class);
        CoreUpcomingServicesActivity.startMe(coreUpcomingServicesActivity, memberObject);
        Mockito.verify(coreUpcomingServicesActivity).startActivity(Mockito.any());
    }

    @Test
    public void testInitializePresenter() {
        MemberObject memberObject = Mockito.mock(MemberObject.class);
        ReflectionHelpers.setField(coreUpcomingServicesActivity, "memberObject", memberObject);

        CoreChildUpcomingServiceInteractor coreChildUpcomingServiceInteractor = Mockito.mock(CoreChildUpcomingServiceInteractor.class);

        Mockito.doNothing().when(coreUpcomingServicesActivity).displayLoadingState(Mockito.anyBoolean());
        AppExecutors appExecutors = Mockito.mock(AppExecutors.class);
        Executor executor = Mockito.mock(Executor.class);
        ReflectionHelpers.setField(coreChildUpcomingServiceInteractor, "appExecutors", appExecutors);
        Mockito.doReturn(executor).when(appExecutors).diskIO();

        coreUpcomingServicesActivity.initializePresenter();
        Assert.assertTrue(ReflectionHelpers.getField(coreUpcomingServicesActivity, "presenter") instanceof BaseAncUpcomingServicesPresenter);
    }
}