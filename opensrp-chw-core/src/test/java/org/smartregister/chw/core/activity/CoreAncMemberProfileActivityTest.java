package org.smartregister.chw.core.activity;

import android.view.MenuItem;
import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.impl.CoreAncMemberProfileActivityImpl;
import org.smartregister.chw.core.presenter.CoreAncMemberProfilePresenter;
import org.smartregister.chw.core.utils.CoreConstants;

public class CoreAncMemberProfileActivityTest extends BaseUnitTest {

    private CoreAncMemberProfileActivity activity;

    private View viewFamilyRow = new View(RuntimeEnvironment.systemContext);
    private ActivityController<CoreAncMemberProfileActivityImpl> controller;

    @Mock
    private MemberObject memberObject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        MockitoAnnotations.initMocks(this);

        Context context = Context.getInstance();
        CoreLibrary.init(context);

        //Auto login by default
        context.session().start(context.session().lengthInMilliseconds());

        controller = Robolectric.buildActivity(CoreAncMemberProfileActivityImpl.class).create().start();
        activity = controller.get();
        memberObject = Mockito.mock(MemberObject.class);
        memberObject.setBaseEntityId("some-base-entity-id");
        memberObject.setFamilyName("Some Family Name");

        ReflectionHelpers.setField(activity, "memberObject", memberObject);
        ReflectionHelpers.setField(activity, "view_family_row", viewFamilyRow);
    }

    @Test
    public void registerPresenter() {
        activity.registerPresenter();
        Assert.assertNotNull(activity.ancMemberProfilePresenter());
    }

    @Test
    public void testOnOptionsItemSelected() {
        activity = Mockito.spy(activity);

        MenuItem menuItem = Mockito.mock(MenuItem.class);
        CoreConstants.JSON_FORM.setLocaleAndAssetManager(activity.getApplicationContext().getResources().getConfiguration().locale, activity.getApplicationContext().getAssets());

        // back pressed
        Mockito.doReturn(android.R.id.home).when(menuItem).getItemId();
        Mockito.doNothing().when(activity).onBackPressed();

        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).onBackPressed();

        // start form
        Mockito.doReturn(R.id.action_anc_member_registration).when(menuItem).getItemId();
        Mockito.doNothing().when(activity).startFormForEdit(Mockito.any(), Mockito.any());

        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).startFormForEdit(Mockito.eq(R.string.edit_member_form_title), Mockito.any());


        // start form
        Mockito.doReturn(R.id.action_anc_registration).when(menuItem).getItemId();
        Mockito.doNothing().when(activity).startFormForEdit(Mockito.any(), Mockito.any());

        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).startFormForEdit(Mockito.eq(R.string.edit_anc_registration_form_title), Mockito.any());

        // start form
        Mockito.doReturn(R.id.anc_danger_signs_outcome).when(menuItem).getItemId();
        CoreAncMemberProfilePresenter presenter = Mockito.mock(CoreAncMemberProfilePresenter.class);
        ReflectionHelpers.setField(activity, "presenter", presenter);

        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(presenter).startAncDangerSignsOutcomeForm(memberObject);
    }

    @Test
    public void openFamilyLocationStartsAncMemberMapActivity() {
        activity = Mockito.spy(activity);
        activity.openFamilyLocation();
        Mockito.verify(activity).startActivity(Mockito.any());
    }
}