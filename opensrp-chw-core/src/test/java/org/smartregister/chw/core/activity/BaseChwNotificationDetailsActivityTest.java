package org.smartregister.chw.core.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.contract.NotificationDetailsContract;
import org.smartregister.chw.core.domain.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class BaseNotificationDetailsActivityTest extends BaseUnitTest {

    private NotificationDetailsContract.View view;
    private TextView referralNotificationTitle = new TextView(RuntimeEnvironment.systemContext);
    private TextView markAsDoneTextView =  new TextView(RuntimeEnvironment.systemContext);
    private LinearLayout referralNotificationDetails = new LinearLayout(RuntimeEnvironment.systemContext);

    @Before
    public void setUp() {
        view = Robolectric.buildActivity(TestableNotificationDetailsActivity.class).get();
        ReflectionHelpers.setField(view, "referralNotificationTitle", referralNotificationTitle);
        ReflectionHelpers.setField(view, "markAsDoneTextView", markAsDoneTextView);
        ReflectionHelpers.setField(view, "referralNotificationDetails", referralNotificationDetails);
    }

    @Test
    public void testInitPresenter() {
        view.initPresenter();
        Assert.assertNotNull(((BaseNotificationDetailsActivity) view).getPresenter());
    }

    @Test
    public void testOnReferralDetailsFetched() {
        List<String> details = new ArrayList<>();
        details.add("Village: Mumbai");
        details.add("Referral Successful");
        NotificationItem notificationItem = new NotificationItem("Mathew Lucas visited the facility on 03 Mar 2020.", details);
        view.setNotificationDetails(notificationItem);
        Assert.assertNotNull(referralNotificationTitle.getText());
        Assert.assertEquals(2, referralNotificationDetails.getChildCount());
    }

    @Test
    public void testDisableMarkAsDoneAction(){
        view.disableMarkAsDoneAction(true);
        Assert.assertFalse(markAsDoneTextView.isEnabled());
    }

    public static class TestableNotificationDetailsActivity extends BaseNotificationDetailsActivity {

    }
}