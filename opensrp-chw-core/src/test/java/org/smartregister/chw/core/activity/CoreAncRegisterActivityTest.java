package org.smartregister.chw.core.activity;

import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.presenter.BaseAncRegisterPresenter;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.adapter.NavigationAdapter;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.utils.CoreConstants;

import timber.log.Timber;

public class CoreAncRegisterActivityTest extends BaseUnitTest {

    private MemberObject memberObject;
    @Mock
    private Menu menu;
    private CoreAncRegisterActivity activity;

    private ActivityController<CoreAncRegisterActivity> controller;

    @Mock
    private BaseAncRegisterPresenter presenter;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        MockitoAnnotations.initMocks(this);

        Context context = Context.getInstance();
        CoreLibrary.init(context);

        //Auto login by default
        context.session().start(context.session().lengthInMilliseconds());

        Intent intent = new Intent();
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.PHONE_NUMBER, "phone_number");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.FORM_NAME, "form_name");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.UNIQUE_ID, "unique_id");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.FAMILY_BASE_ENTITY_ID, "familyBaseEntityId");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.FAMILY_NAME, "familyName");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.LAST_LMP, "lastMenstrualPeriod");

        memberObject = Mockito.mock(MemberObject.class);
        memberObject.setBaseEntityId("some-base-entity-id");
        memberObject.setFamilyName("Some Family Name");

        controller = Robolectric.buildActivity(CoreAncRegisterActivity.class, intent).create().start().resume();
        activity = controller.get();
        activity.onCreation();
    }

    @Test
    public void testPresenterIsSetUp() {
        BaseAncRegisterPresenter presenter = ReflectionHelpers.getField(activity, "presenter");
        assertNotNull(presenter);
    }

    @After
    public void tearDown() {
        try {
            activity.finish();
            controller.pause().stop().destroy(); //destroy controller if we can
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Test
    public void testGetFamilyBaseEntityId() {
        Assert.assertEquals(activity.getFamilyBaseEntityId(), "familyBaseEntityId");
        Assert.assertEquals(activity.getFormName(), "form_name");
        Assert.assertEquals(activity.getUniqueId(), "unique_id");
        Assert.assertEquals(activity.getFamilyName(), "familyName");
        Assert.assertEquals(activity.getPhoneNumber(), "phone_number");
    }

    @Test
    public void testOnResumptionAdapterMenu() {
        NavigationMenu menu = Mockito.mock(NavigationMenu.class);
        NavigationAdapter adapter = Mockito.mock(NavigationAdapter.class);

        Mockito.doReturn(adapter).when(menu).getNavigationAdapter();
        ReflectionHelpers.setStaticField(NavigationMenu.class, "instance", menu);

        ReflectionHelpers.setField(activity, "presenter", presenter);
        activity.onResumption();

        Mockito.verify(adapter).setSelectedView(Mockito.anyString());
    }

    @Test
    public void testOnBackPressedShouldCallOnBackPressed() {
        CoreAncRegisterActivity spyActivity = Mockito.spy(activity);
        spyActivity.onBackPressed();
        Mockito.verify(spyActivity).onBackPressed();
    }

    @Test
    public void testOnCreateOptionsMenu() {
        Assert.assertTrue(activity.onCreateOptionsMenu(menu));
    }

    @Test
    public void testStartMeShouldLaunchCoreChildProfileActivity() {
        Activity activity = Mockito.mock(Activity.class);
        CoreChildProfileActivity.startMe(activity, memberObject, activity.getClass());
        Mockito.verify(activity).startActivity(Mockito.any());
    }

    @Test
    public void testOnCreate() {
        assertNotNull(Whitebox.getInternalState(activity, "mPagerAdapter"));
    }
}
