package org.smartregister.chw.core.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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
import org.smartregister.chw.core.contract.CoreChildRegisterContract;
import org.smartregister.chw.core.fragment.CoreChildRegisterFragment;
import org.smartregister.chw.core.presenter.CoreChildRegisterPresenter;

public class CoreChildRegisterActivityTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private CoreChildRegisterActivity activity;
    private ActivityController<CoreChildRegisterActivity> controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Context context = Context.getInstance();
        CoreLibrary.init(context);

        controller = Robolectric.buildActivity(CoreChildRegisterActivity.class);
        activity = controller.get();
    }

    @Test
    public void canInitializePresenter() {
        activity.initializePresenter();
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "presenter"));
        Assert.assertTrue(ReflectionHelpers.getField(activity, "presenter") instanceof CoreChildRegisterPresenter);
    }

    @Test
    public void getPresenterReturnsCorrectPresenter() {
        activity.initializePresenter();
        Assert.assertTrue(activity.presenter() instanceof CoreChildRegisterContract.Presenter);
    }

    @Test
    public void registerBottomNavIsInitialisedCorrectly() {
        activity.setContentView(R.layout.activity_base_register);
        activity.registerBottomNavigation();
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "bottomNavigationHelper"));
        BottomNavigationView bottomNavigationView = ReflectionHelpers.getField(activity, "bottomNavigationView");
        Assert.assertNotNull(bottomNavigationView);
        Assert.assertEquals(4, bottomNavigationView.getMenu().size());
        Assert.assertEquals(LabelVisibilityMode.LABEL_VISIBILITY_LABELED, bottomNavigationView.getLabelVisibilityMode());
    }

    @Test
    public void canOpenFamilyListView() {
        activity.setContentView(R.layout.activity_base_register);
        activity.registerBottomNavigation();
        BottomNavigationView mockView = Mockito.spy((BottomNavigationView) ReflectionHelpers.getField(activity, "bottomNavigationView"));
        ReflectionHelpers.setField(activity, "bottomNavigationView", mockView);
        activity.openFamilyListView();
        Mockito.verify(mockView).setSelectedItemId(R.id.action_family);
    }

    @Test
    public void getRegisterFragmentReturnsCoreChildRegisterFragment() {
        Assert.assertTrue(activity.getRegisterFragment() instanceof CoreChildRegisterFragment);
    }

    @After
    public void tearDown() {
        try {
            activity.finish();
            controller.pause().stop().destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
