package org.smartregister.chw.core.adapter;


import androidx.drawerlayout.widget.DrawerLayout;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.smartregister.Context;
import org.smartregister.chw.core.BaseRobolectricTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.CoreChildRegisterActivity;
import org.smartregister.chw.core.model.NavigationOption;
import org.smartregister.chw.core.utils.CoreConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NavigationAdapterTest extends BaseRobolectricTest {

    private CoreChildRegisterActivity activity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Context.bindtypes = new ArrayList<>();

        ActivityController<CoreChildRegisterActivity> controller = Robolectric.buildActivity(CoreChildRegisterActivity.class).create().start();
        activity = controller.get();
    }

    @Test
    public void getItemCount() {
        NavigationOption model1 = new NavigationOption(R.mipmap.sidemenu_families, R.mipmap.sidemenu_families_active, R.string.menu_all_families, CoreConstants.DrawerMenu.ALL_FAMILIES, 0);
        NavigationOption model2 = new NavigationOption(R.mipmap.sidemenu_families, R.mipmap.sidemenu_families_active, R.string.menu_all_families, CoreConstants.DrawerMenu.ALL_FAMILIES, 0);

        Map<String, Class> registeredActivities = new HashMap<>();
        registeredActivities.put(CoreConstants.REGISTERED_ACTIVITIES.CHILD_REGISTER_ACTIVITY, CoreChildRegisterActivity.class);
        NavigationAdapter adapter = new NavigationAdapter(Arrays.asList(model1, model2), activity, registeredActivities, Mockito.mock(NavigationAdapterHost.class), Mockito.mock(DrawerLayout.class));

        Assert.assertEquals(adapter.getItemCount(), 2);
    }

}