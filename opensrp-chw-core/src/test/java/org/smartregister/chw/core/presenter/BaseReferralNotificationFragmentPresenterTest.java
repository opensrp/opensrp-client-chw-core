package org.smartregister.chw.core.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;

import java.lang.ref.WeakReference;

public class BaseReferralNotificationFragmentPresenterTest extends BaseUnitTest {
    @Mock
    private BaseReferralNotificationDetailsContract.View view;

    @Mock
    private BaseReferralNotificationDetailsContract.Interactor interactor;

    private BaseReferralNotificationDetailsPresenter baseReferralNotificationDetailsPresenter;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        baseReferralNotificationDetailsPresenter = Mockito.mock(BaseReferralNotificationDetailsPresenter.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(baseReferralNotificationDetailsPresenter, "view", new WeakReference<>(view));
        ReflectionHelpers.setField(baseReferralNotificationDetailsPresenter, "interactor", interactor);
    }

    @Test
    public void getReferralDetails() {
        String referralTaskId = "45678";
        String notificationType = "popup";
        baseReferralNotificationDetailsPresenter.getReferralDetails(referralTaskId, notificationType);
        Mockito.verify(interactor).fetchReferralDetails(referralTaskId, notificationType);
    }
    @Test
    public void baseReferralNotificationPresenterView() {
        Assert.assertEquals(view, baseReferralNotificationDetailsPresenter.getView());
    }
}
