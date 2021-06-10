package org.smartregister.chw.core.activity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;

import timber.log.Timber;

public class CoreAncRegisterActivityTest extends BaseUnitTest {

    private CoreAncRegisterActivity activity;

    private ActivityController<CoreAncRegisterActivity> controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        MockitoAnnotations.initMocks(this);

        Context context = Context.getInstance();
        CoreLibrary.init(context);

        //Auto login by default
        context.session().start(context.session().lengthInMilliseconds());

        controller = Robolectric.buildActivity(CoreAncRegisterActivity.class).create().start();
        activity = controller.get();

        ReflectionHelpers.setField(activity, "phone_number", "phone_number");
        ReflectionHelpers.setField(activity, "form_name", "form_name");
        ReflectionHelpers.setField(activity, "unique_id", "unique_id");
        ReflectionHelpers.setField(activity, "familyBaseEntityId", "familyBaseEntityId");
        ReflectionHelpers.setField(activity, "familyName", "familyName");
        ReflectionHelpers.setField(activity, "lastMenstrualPeriod", "lastMenstrualPeriod");
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
        Assert.assertEquals(CoreAncRegisterActivity.getFamilyBaseEntityId(), "familyBaseEntityId");
    }

    @Test
    public void testGetFormName() {
        Assert.assertEquals(CoreAncRegisterActivity.getFormName(), "form_name");
    }

    @Test
    public void testGetUniqueId() {
        Assert.assertEquals(CoreAncRegisterActivity.getUniqueId(), "unique_id");
    }

    @Test
    public void testGetFamilyName() {
        Assert.assertEquals(CoreAncRegisterActivity.getFamilyName(), "familyName");
    }
}
