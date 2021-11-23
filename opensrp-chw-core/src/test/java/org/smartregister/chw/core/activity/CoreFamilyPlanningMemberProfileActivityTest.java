package org.smartregister.chw.core.activity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
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

}
