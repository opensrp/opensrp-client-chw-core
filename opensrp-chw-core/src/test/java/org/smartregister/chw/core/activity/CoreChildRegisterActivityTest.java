package org.smartregister.chw.core.activity;

import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.fragment.CoreChildRegisterFragment;
import org.smartregister.chw.core.presenter.CoreChildRegisterPresenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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

        //Auto login by default
        String password = "pwd";
        context.session().start(context.session().lengthInMilliseconds());
        context.configuration().getDrishtiApplication().setPassword(password);
        context.session().setPassword(password);

        controller = Robolectric.buildActivity(CoreChildRegisterActivity.class, new Intent());
        activity = controller.get();
        controller
                .create()
                .start();
    }

    @Test
    public void testNavigationMenuInstanceNotNull() {
        assertNotNull(ReflectionHelpers.getStaticField(NavigationMenu.class, "instance"));
    }

    @Test
    public void testPresenterIsInitialized() {
        assertTrue(activity.presenter() instanceof CoreChildRegisterPresenter);
    }

    @Test
    public void testRegisterFragmentIsInitialized() {
        assertTrue(Whitebox.getInternalState(activity, "mBaseFragment") instanceof CoreChildRegisterFragment);
    }

    @Test
    public void switchToBaseFragment() {
        CoreChildRegisterActivity spyActivity = spy(activity);
        doNothing().when(spyActivity).startActivity(any(Intent.class));
        doNothing().when(spyActivity).finish();
        spyActivity.switchToBaseFragment();
        verify(spyActivity).finish();
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(spyActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(CoreFamilyRegisterActivity.class.getName(), intentArgumentCaptor.getValue().getComponent().getClassName());
    }

    @Test
    public void openFamilyListView() {
        activity.openFamilyListView();
        BottomNavigationView navigationView = Whitebox.getInternalState(activity, "bottomNavigationView");
        assertEquals(R.id.action_family, navigationView.getSelectedItemId());
    }

}