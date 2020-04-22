package org.smartregister.chw.core.presenter;

import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.activity.BaseReferralNotificationDetailsActivity;
import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;
import org.smartregister.chw.core.domain.ReferralNotificationItem;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class BaseReferralNotificationDetailsPresenterTest extends BaseUnitTest {

    @Mock
    private BaseReferralNotificationDetailsContract.Interactor interactor;

    private BaseReferralNotificationDetailsActivity view;
    private BaseReferralNotificationDetailsPresenter notificationDetailsPresenter;
    private TextView referralNotificationTitle = new TextView(RuntimeEnvironment.systemContext);
    private LinearLayout referralNotificationDetails = new LinearLayout(RuntimeEnvironment.systemContext);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        view = Robolectric.buildActivity(TestableReferralNotificationDetailsActivity.class).get();
        ReflectionHelpers.setField(view, "referralNotificationTitle", referralNotificationTitle);
        ReflectionHelpers.setField(view, "referralNotificationDetails", referralNotificationDetails);
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
    public void testOnReferralDetailsFetched() {
        List<String> details = new ArrayList<>();
        details.add("Referral Successful");
        ReferralNotificationItem referralDetails = new ReferralNotificationItem("some-title", details);
        notificationDetailsPresenter.onReferralDetailsFetched(referralDetails);
        Assert.assertNotNull(referralNotificationTitle.getText());
        Assert.assertEquals(1, referralNotificationDetails.getChildCount());
    }

    @Test
    public void testGetView() {
        Assert.assertNotNull(notificationDetailsPresenter.getView());
    }

    public static class TestableReferralNotificationDetailsActivity extends BaseReferralNotificationDetailsActivity {

    }
}