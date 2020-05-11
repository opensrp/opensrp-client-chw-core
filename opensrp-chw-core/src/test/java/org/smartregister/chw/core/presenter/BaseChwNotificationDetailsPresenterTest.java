package org.smartregister.chw.core.presenter;

import android.util.Pair;
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
import org.smartregister.chw.core.activity.BaseChwNotificationDetailsActivity;
import org.smartregister.chw.core.contract.ChwNotificationDetailsContract;
import org.smartregister.chw.core.domain.NotificationItem;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class BaseChwNotificationDetailsPresenterTest extends BaseUnitTest {

    @Mock
    private ChwNotificationDetailsContract.Interactor interactor;

    private BaseChwNotificationDetailsPresenter notificationDetailsPresenter;
    private TextView referralNotificationTitle = new TextView(RuntimeEnvironment.systemContext);
    private LinearLayout referralNotificationDetails = new LinearLayout(RuntimeEnvironment.systemContext);
    private String baseEntityId = "some-base-entity-id";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        BaseChwNotificationDetailsActivity view = Robolectric.buildActivity(TestableChwNotificationDetailsActivity.class).get();
        ReflectionHelpers.setField(view, "referralNotificationTitle", referralNotificationTitle);
        ReflectionHelpers.setField(view, "referralNotificationDetails", referralNotificationDetails);
        notificationDetailsPresenter = new BaseChwNotificationDetailsPresenter(view);
        notificationDetailsPresenter.setInteractor(interactor);
        notificationDetailsPresenter.setClientBaseEntityId(baseEntityId);
        notificationDetailsPresenter.setNotificationDates(Pair.create("2020-04-28", "2020-05-01"));
    }

    @Test
    public void testGetReferralDetails() {
        String referralTaskId = "referralTaskId";
        String notificationType = "Referral Successful";
        notificationDetailsPresenter.getNotificationDetails(referralTaskId, notificationType);
        verify(interactor, atLeastOnce()).fetchNotificationDetails(referralTaskId, notificationType);
    }

    @Test
    public void testOnReferralDetailsFetched() {
        List<String> details = new ArrayList<>();
        details.add("Referral Successful");
        NotificationItem referralDetails = new NotificationItem("some-title", details);
        notificationDetailsPresenter.onNotificationDetailsFetched(referralDetails);
        Assert.assertNotNull(referralNotificationTitle.getText());
        Assert.assertEquals(1, referralNotificationDetails.getChildCount());
    }

    @Test
    public void testGetView() {
        Assert.assertNotNull(notificationDetailsPresenter.getView());
    }

    @Test
    public void testGetBaseEntityId (){
        Assert.assertNotNull(notificationDetailsPresenter.getClientBaseEntityId());
        Assert.assertEquals(baseEntityId, notificationDetailsPresenter.getClientBaseEntityId());
    }

    @Test
    public void testGetNotificationDates() {
       Assert.assertNotNull(notificationDetailsPresenter.getNotificationDates());
    }

    public static class TestableChwNotificationDetailsActivity extends BaseChwNotificationDetailsActivity {

    }
}