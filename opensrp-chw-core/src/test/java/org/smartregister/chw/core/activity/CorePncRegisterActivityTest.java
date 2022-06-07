package org.smartregister.chw.core.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.adapter.NavigationAdapter;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.view.activity.BaseRegisterActivity;


public class CorePncRegisterActivityTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    public CorePncRegisterActivity activity;

    @Mock
    private BaseRegisterActivity presenter;

    @Test
    public void testSwitchToBaseFragment() {
        CorePncRegisterActivity spyActivity = spy(activity);
        doNothing().when(spyActivity).startActivity(any(Intent.class));
        doNothing().when(spyActivity).finish();
        spyActivity.switchToBaseFragment();
        verify(spyActivity).finish();
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(spyActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(CorePncRegisterActivity.class.getName(), intentArgumentCaptor.getValue().getComponent().getClassName());
    }


    @Test
    public void testOnResumption() {
        NavigationMenu menu = Mockito.mock(NavigationMenu.class);
        NavigationAdapter adapter = Mockito.mock(NavigationAdapter.class);

        Mockito.doReturn(adapter).when(menu).getNavigationAdapter();
        ReflectionHelpers.setStaticField(NavigationMenu.class, "instance", menu);

        ReflectionHelpers.setField(activity, "presenter", presenter);
        activity.onResumption();

        Mockito.verify(adapter).setSelectedView(Mockito.anyString());
    }

    @Test
    public void testRegisterBottomNavigationIsInitialized() {
        activity.setContentView(R.layout.activity_base_register);
        activity.registerBottomNavigation();
        assertNotNull(ReflectionHelpers.getField(activity, "bottomNavigationHelper"));
        BottomNavigationView bottomNavigationView = ReflectionHelpers.getField(activity, "bottomNavigationView");
        assertNotNull(bottomNavigationView);


    }
}
