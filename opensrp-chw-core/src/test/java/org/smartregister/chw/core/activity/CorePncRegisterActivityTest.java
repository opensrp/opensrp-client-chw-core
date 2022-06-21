package org.smartregister.chw.core.activity;

import static org.junit.Assert.assertNotNull;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
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
import org.smartregister.view.activity.BaseRegisterActivity;


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
}
