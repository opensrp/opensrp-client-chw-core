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
import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;
import org.smartregister.chw.core.domain.ReferralNotificationItem;

import java.util.ArrayList;
import java.util.List;

public class BaseReferralNotificationDetailsActivityTest extends BaseUnitTest {

    private BaseReferralNotificationDetailsContract.View view;
    private TextView referralNotificationTitle = new TextView(RuntimeEnvironment.systemContext);
    private LinearLayout referralNotificationDetails = new LinearLayout(RuntimeEnvironment.systemContext);

    @Before
    public void setUp() {
        view = Robolectric.buildActivity(TestableReferralNotificationDetailsActivity.class).get();
        ReflectionHelpers.setField(view, "referralNotificationTitle", referralNotificationTitle);
        ReflectionHelpers.setField(view, "referralNotificationDetails", referralNotificationDetails);
    }

    @Test
    public void testInitPresenter() {
        view.initPresenter();
        Assert.assertNotNull(((BaseReferralNotificationDetailsActivity) view).getPresenter());
    }

    @Test
    public void testOnReferralDetailsFetched() {
        List<String> details = new ArrayList<>();
        details.add("Village: Mumbai");
        details.add("Referral Successful");
        ReferralNotificationItem referralNotificationItem = new ReferralNotificationItem("Mathew Lucas visited the facility on 03 Mar 2020.", details);
        view.onReferralDetailsFetched(referralNotificationItem);
        Assert.assertNotNull(referralNotificationTitle.getText());
        Assert.assertEquals(2, referralNotificationDetails.getChildCount());
    }

    public static class TestableReferralNotificationDetailsActivity extends BaseReferralNotificationDetailsActivity {

    }
}