package org.smartregister.chw.core.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.activity.BaseReferralNotificationDetailsActivity;
import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class BaseReferralNotificationDetailsPresenterTest {

    private BaseReferralNotificationDetailsPresenter notificationDetailsPresenter;

    @Mock
    private BaseReferralNotificationDetailsContract.Interactor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        BaseReferralNotificationDetailsActivity view = Mockito.mock(BaseReferralNotificationDetailsActivity.class, Mockito.CALLS_REAL_METHODS);
        notificationDetailsPresenter = new BaseReferralNotificationDetailsPresenter(view);
        notificationDetailsPresenter.setInteractor(interactor);
    }

    @Test
    public void testGetReferralDetails() {
        String referralTaskId = "referralTaskId";
        String notificationType = "Referral Successful";
        notificationDetailsPresenter.getReferralDetails(referralTaskId, notificationType);
        verify(interactor, atLeastOnce()).fetchReferralDetails(referralTaskId, notificationType);
    }

    @Test
    public void testGetView() {
        Assert.assertNotNull(notificationDetailsPresenter.getView());
    }
}