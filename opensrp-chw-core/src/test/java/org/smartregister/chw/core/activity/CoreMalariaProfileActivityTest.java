package org.smartregister.chw.core.activity;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.impl.CoreMalariaProfileActivityImpl;
import org.smartregister.chw.core.presenter.CoreMalariaMemberProfilePresenter;
import org.smartregister.chw.malaria.domain.MemberObject;

import timber.log.Timber;

public class CoreMalariaProfileActivityTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private CoreMalariaProfileActivity activity;
    private ActivityController<CoreMalariaProfileActivityImpl> activityController;

    @Mock
    private MemberObject memberObject;

    @Mock
    private ProgressBar progressBar;

    @Mock
    private CoreMalariaMemberProfilePresenter profilePresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Context context = Context.getInstance();
        CoreLibrary.init(context);

        activityController = Robolectric.buildActivity(CoreMalariaProfileActivityImpl.class);
        activity = activityController.get();

        memberObject = Mockito.mock(MemberObject.class);
        memberObject.setFamilyName("Sample");
        progressBar = Mockito.mock(ProgressBar.class);
        profilePresenter = Mockito.mock(CoreMalariaMemberProfilePresenter.class);

        ReflectionHelpers.setField(activity, "memberObject", memberObject);
        ReflectionHelpers.setField(activity, "progressBar", progressBar);
        ReflectionHelpers.setField(activity, "profilePresenter", profilePresenter);

        activityController.create().start();
    }

    @Test
    public void presenterIsInitialized() {
        activity.initializePresenter();
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "profilePresenter"));
    }

    @Test
    public void initNotificationReferralRecyclerViewInitsCorrectLayouts() {
        activity = Mockito.spy(activity);
        Mockito.when(activity.findViewById(R.id.notification_and_referral_row)).thenReturn(Mockito.mock(RelativeLayout.class));
        Mockito.when(activity.findViewById(R.id.notification_and_referral_recycler_view)).thenReturn(Mockito.mock(RecyclerView.class));
        activity.initializeNotificationReferralRecyclerView();
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "notificationAndReferralLayout"));
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "notificationAndReferralRecyclerView"));
    }

    @Test
    public void initialisingPresenterRefreshesProfileBottomSection() {
        activity.initializePresenter();
        profilePresenter = Mockito.spy(profilePresenter);
        Mockito.verify(profilePresenter, Mockito.times(1)).refreshProfileBottom();
    }

    @After
    public void tearDown() {
        try {
            activity.finish();
            activityController.pause().stop().destroy();
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}
