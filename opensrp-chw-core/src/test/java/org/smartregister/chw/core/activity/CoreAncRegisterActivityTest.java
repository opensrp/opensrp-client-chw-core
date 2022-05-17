package org.smartregister.chw.core.activity;

import static org.mockito.Mockito.doReturn;

import android.content.Intent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.chw.anc.AncLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.utils.CoreConstants;

import timber.log.Timber;

public class CoreAncRegisterActivityTest extends BaseUnitTest {

    private CoreAncRegisterActivity activity;

    private ActivityController<CoreAncRegisterActivity> controller;

    @Mock
    private AncLibrary ancLibrary;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        MockitoAnnotations.initMocks(this);

        Context context = Context.getInstance();

        //Auto login by default
        String password = "pwd";
        context.session().start(context.session().lengthInMilliseconds());
        context.configuration().getDrishtiApplication().setPassword(password.getBytes());
        context.session().setPassword(password.getBytes());

        Intent intent = new Intent();
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.PHONE_NUMBER, "phone_number");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.FORM_NAME, "form_name");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.UNIQUE_ID, "unique_id");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.FAMILY_BASE_ENTITY_ID, "familyBaseEntityId");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.FAMILY_NAME, "familyName");
        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.LAST_LMP, "lastMenstrualPeriod");

        ReflectionHelpers.setStaticField(AncLibrary.class, "instance", ancLibrary);
        doReturn(context).when(ancLibrary).context();

        controller = Robolectric.buildActivity(CoreAncRegisterActivity.class, intent).create().start().visible();
        activity = controller.get();
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
}
