package org.smartregister.chw.core.activity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.impl.CoreFamilyPlanningMemberProfileActivityImpl;
import org.smartregister.chw.core.presenter.CoreFamilyPlanningProfilePresenter;
import org.smartregister.chw.core.shadows.FPDaoShadowHelper;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;


@Config(shadows = FPDaoShadowHelper.class)
public class CoreFamilyPlanningMemberProfileActivityTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private CoreFamilyPlanningMemberProfileActivityImpl activity;
    private ActivityController<CoreFamilyPlanningMemberProfileActivityImpl> controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        FpMemberObject fpMemberObject = new FpMemberObject();
        fpMemberObject.setFamilyName("Tester");
        fpMemberObject.setFpMethod(FamilyPlanningConstants.DBConstants.FP_COC);
        fpMemberObject.setFpStartDate("2021-08-01");
        fpMemberObject.setBaseEntityId("test-member-123");

        Intent intent = new Intent();
        intent.putExtra(FamilyPlanningConstants.FamilyPlanningMemberObject.MEMBER_OBJECT, fpMemberObject);
        intent.putExtra(CoreConstants.INTENT_KEY.TOOLBAR_TITLE, R.string.return_to_family_name);
        controller = Robolectric.buildActivity(CoreFamilyPlanningMemberProfileActivityImpl.class, intent).create().start();
        activity = Mockito.spy(controller.get());
        activity.onCreation();
    }

    @Test
    public void onCreationInitsViews() {
        TextView toolbarTextView = activity.findViewById(R.id.toolbar_title);
        Assert.assertEquals(toolbarTextView.getText(), activity.getString(R.string.return_to_family_name, "Tester"));
        assertNotNull(ReflectionHelpers.getField(activity, "notificationAndReferralLayout"));
        assertNotNull(ReflectionHelpers.getField(activity, "notificationAndReferralRecyclerView"));
    }


    @Test
    public void presenterIsInitializedCorrectly() {
        assertNotNull(ReflectionHelpers.getField(activity, "fpProfilePresenter"));
        assertTrue(ReflectionHelpers.getField(activity, "fpProfilePresenter") instanceof CoreFamilyPlanningProfilePresenter);
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
        Mockito.doReturn(R.id.action_registration).when(menuItem).getItemId();
        Mockito.doNothing().when(activity).startFormForEdit(Mockito.any(), Mockito.any());
        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).startFormForEdit(Mockito.eq(R.string.registration_info), Mockito.any());
        // remove member
        Mockito.doReturn(R.id.action_remove_member).when(menuItem).getItemId();
        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).removeMember();
        // start family planning registration
        Mockito.doReturn(R.id.action_fp_change).when(menuItem).getItemId();
        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).startFamilyPlanningRegistrationActivity();
        // start register
        Mockito.doReturn(R.id.action_malaria_registration).when(menuItem).getItemId();
        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).startMalariaRegister();
        // Malaria diagnosis
        Mockito.doReturn(R.id.action_malaria_followup_visit).when(menuItem).getItemId();
        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).startMalariaFollowUpVisit();
        // Malaria diagnosis
        Mockito.doReturn(R.id.action_malaria_diagnosis).when(menuItem).getItemId();
        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).startHfMalariaFollowupForm();


    }
}
