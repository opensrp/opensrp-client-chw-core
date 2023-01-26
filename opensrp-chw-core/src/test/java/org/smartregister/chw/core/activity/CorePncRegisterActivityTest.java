package org.smartregister.chw.core.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.impl.CorePncRegisterActivityTestImpl;



public class CorePncRegisterActivityTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    public CorePncRegisterActivity activity;

    private ActivityController<CorePncRegisterActivityTestImpl> controller;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Context context = Context.getInstance();

        //Auto login by default
        String password = "pwd";
        context.session().start(context.session().lengthInMilliseconds());
        context.configuration().getDrishtiApplication().setPassword(password.getBytes());
        context.session().setPassword(password.getBytes());

        controller = Robolectric.buildActivity(CorePncRegisterActivityTestImpl.class, new Intent());
        activity = controller.get();
        controller
                .create()
                .start();
    }

    @After
    public void tearDown() {
        try {
            activity.finish();
            controller.pause().stop().destroy(); //destroy controller if we can
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testRegisterBottomNavigationIsInitialized() {
        activity.setContentView(R.layout.activity_base_register);
        activity.registerBottomNavigation();
        assertNotNull(ReflectionHelpers.getField(activity, "bottomNavigationHelper"));
    }

    @Test
    public void testSwitchToBaseFragment() {
        CorePncRegisterActivity spyActivity = spy(activity);
        doNothing().when(spyActivity).startActivity(any(Intent.class));
        doNothing().when(spyActivity).finish();
        spyActivity.switchToBaseFragment();
        verify(spyActivity).finish();

        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(spyActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(CoreFamilyRegisterActivity.class.getName(), intentArgumentCaptor.getValue().getComponent().getClassName());
    }
}
