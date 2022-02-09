package org.smartregister.chw.core.activity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.ChwNotificationDetailsContract;
import org.smartregister.chw.core.domain.NotificationItem;
import org.smartregister.chw.core.presenter.BaseChwNotificationDetailsPresenter;
import org.smartregister.commonregistry.CommonPersonObjectClient;

import java.util.ArrayList;
import java.util.List;

public class BaseChwNotificationDetailsActivityTest extends BaseUnitTest {

    private ChwNotificationDetailsContract.View view;
    private TextView notificationTitle = new TextView(RuntimeEnvironment.systemContext);
    private TextView markAsDoneTextView = new TextView(RuntimeEnvironment.systemContext);
    private LinearLayout notificationDetails = new LinearLayout(RuntimeEnvironment.systemContext);
    private TestableChwNotificationDetailsActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(TestableChwNotificationDetailsActivity.class).get();
        view = activity;
        ReflectionHelpers.setField(view, "notificationTitle", notificationTitle);
        ReflectionHelpers.setField(view, "markAsDoneTextView", markAsDoneTextView);
        ReflectionHelpers.setField(view, "notificationDetails", notificationDetails);
    }

    @Test
    public void setUpViewsInitializesViews() {
        activity.setContentView(R.layout.activity_chw_notification_details);
        activity.setupViews();

        TextView markAsDoneTV = ReflectionHelpers.getField(activity, "markAsDoneTextView");
        TextView viewProfileTextView = ReflectionHelpers.getField(activity, "viewProfileTextView");
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "notificationTitle"));
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "notificationDetails"));
        Assert.assertNotNull(markAsDoneTV);
        Assert.assertTrue(markAsDoneTV.hasOnClickListeners());
        Assert.assertNotNull(viewProfileTextView);
        Assert.assertTrue(viewProfileTextView.hasOnClickListeners());
    }

    @Test
    public void disableMarkAsDoneActionDisablesMarkAsDoneTv() {
        TextView markAsDoneTV = Mockito.mock(TextView.class);
        ReflectionHelpers.setField(activity, "markAsDoneTextView", markAsDoneTV);
        activity.disableMarkAsDoneAction(true);
        Mockito.verify(markAsDoneTV).setEnabled(Mockito.eq(false));
        Mockito.verify(markAsDoneTV).setBackground(Mockito.any(Drawable.class));
        Mockito.verify(markAsDoneTV).setTextColor(Mockito.eq(ContextCompat.getColor(activity,
                R.color.text_black)));
    }

    @Test
    public void getCommonPersonObjectClientReturnsCorrectClient() {
        CommonPersonObjectClient client = Mockito.mock(CommonPersonObjectClient.class);
        ReflectionHelpers.setField(activity, "commonPersonObjectClient", client);
        Assert.assertEquals(client, activity.getCommonPersonObjectClient());
    }


    @Test
    public void setCommonPersonsObjectClientAssignsClient() {
        CommonPersonObjectClient client = Mockito.mock(CommonPersonObjectClient.class);
        activity.setCommonPersonsObjectClient(client);
        Assert.assertEquals(client, ReflectionHelpers.getField(activity, "commonPersonObjectClient"));
    }


    @Test
    public void clickingViewProfileNavigatesToMemberProfile() {
        View viewProfileView = new View(activity);
        viewProfileView.setId(R.id.view_profile);
        activity = Mockito.spy(activity);
        activity.onClick(viewProfileView);
        Mockito.verify(activity).goToMemberProfile();
    }

    @Test
    public void clickingMarkAsDoneDismissesNotification() {
        View markAsDoneView = new View(activity);
        markAsDoneView.setId(R.id.mark_as_done);
        ChwNotificationDetailsContract.Presenter presenter = Mockito.mock(BaseChwNotificationDetailsPresenter.class);
        presenter = Mockito.spy(presenter);
        ReflectionHelpers.setField(activity, "presenter", presenter);
        ReflectionHelpers.setField(activity, "notificationId", "notificationId");
        ReflectionHelpers.setField(activity, "notificationType", "notificationType");
        activity.onClick(markAsDoneView);
        Mockito.verify(presenter).dismissNotification(Mockito.eq("notificationId"), Mockito.eq("notificationType"));
    }

    @Test
    public void testInitPresenter() {
        view.initPresenter();
        Assert.assertNotNull(((BaseChwNotificationDetailsActivity) view).getPresenter());
    }

    @Test
    public void testOnReferralDetailsFetched() {
        List<String> details = new ArrayList<>();
        details.add("Village: Mumbai");
        details.add("Referral Successful");
        NotificationItem notificationItem = new NotificationItem("Mathew Lucas visited the facility on 03 Mar 2020.", details);
        view.setNotificationDetails(notificationItem);
        Assert.assertNotNull(notificationTitle.getText());
        Assert.assertEquals(2, notificationDetails.getChildCount());
    }

    @Test
    public void testDisableMarkAsDoneAction() {
        view.disableMarkAsDoneAction(true);
        Assert.assertFalse(markAsDoneTextView.isEnabled());
    }

    @After
    public void tearDown() {
        try {
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class TestableChwNotificationDetailsActivity extends BaseChwNotificationDetailsActivity {

        @Override
        public void goToMemberProfile() {
            // Implementation not required at the moment
        }
    }
}