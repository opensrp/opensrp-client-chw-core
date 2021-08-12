package org.smartregister.chw.core.activity;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.activity.impl.CoreFamilyPlanningMemberProfileActivityImpl;

public class CoreFamilyPlanningMemberProfileActivityTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private CoreFamilyPlanningMemberProfileActivityImpl activity;
    private ActivityController<CoreFamilyPlanningMemberProfileActivityImpl> controller;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Intent intent = new Intent();
        controller = Robolectric.buildActivity(CoreFamilyPlanningMemberProfileActivityImpl.class, intent).create().start();
        activity = controller.get();

        /*Context context = Context.getInstance();
        CoreLibrary.init(context);*/
    }

    @Test
    public void onCreationInitsCorrectViews() {

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

}
