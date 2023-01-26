package org.smartregister.chw.core.utils;

import android.content.res.AssetManager;
import android.os.Build;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.application.TestApplication;

import java.util.Locale;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, sdk = Build.VERSION_CODES.P)
public class CoreConstantsTest {

    @Mock
    private CoreConstants.JSON_FORM jsonForm;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUnifiedReferralFormsReturnsCorrectConstant() {
        Assert.assertEquals("referrals/anc_referral_form", CoreConstants.JSON_FORM.getAncUnifiedReferralForm());
        Assert.assertEquals("referrals/child_referral_form", CoreConstants.JSON_FORM.getChildUnifiedReferralForm());
        Assert.assertEquals("referrals/pnc_referral_form", CoreConstants.JSON_FORM.getPncUnifiedReferralForm());
    }

    @Test
    public void getReferralFormsReturnsCorrectConstant() {
        Assert.assertEquals("child_referral_form", CoreConstants.JSON_FORM.getChildReferralForm());
        Assert.assertEquals("anc_referral_form", CoreConstants.JSON_FORM.getAncReferralForm());
        Assert.assertEquals("pnc_referral_form", CoreConstants.JSON_FORM.getPncReferralForm());
    }

    @Test
    public void getMenuTypeReturnsCorrectConstant() {
        Assert.assertEquals("ChangeHead", CoreConstants.MenuType.ChangeHead);
        Assert.assertEquals("ChangePrimaryCare", CoreConstants.MenuType.ChangePrimaryCare);
    }

    @Test
    public void testGetFamilyKitReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getFamilyKit(), Utils.getLocalForm("family_kit", locale, assetManager));
    }

    @Test
    public void testGetWashCheckReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getWashCheck(), Utils.getLocalForm("wash_check", locale, assetManager));
    }

    @Test
    public void testGetFamilyRegisterReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getFamilyRegister(), Utils.getLocalForm("family_register", locale, assetManager));
    }

    @Test
    public void testGetBirthCertificationReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getBirthCertification(), Utils.getLocalForm("birth_certification", locale, assetManager));
    }

    @Test
    public void testGetDisabilityReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getDisability(), Utils.getLocalForm("child_disability", locale, assetManager));
    }

    @Test
    public void testGetObsIllnessReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getObsIllness(), Utils.getLocalForm("observation_illness", locale, assetManager));
    }

    @Test
    public void testGetChildSickFormReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getChildSickForm(), Utils.getLocalForm("child_sick_form", locale, assetManager));
    }

    @Test
    public void testGetFamilyDetailsRegisterReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getFamilyDetailsRegister(), Utils.getLocalForm("family_details_register", locale, assetManager));
    }

    @Test
    public void testGetFamilyDetailsRemoveMemberReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getFamilyDetailsRemoveMember(), Utils.getLocalForm("family_details_remove_member", locale, assetManager));
    }

    @Test
    public void testGetFamilyDetailsRemoveChildReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getFamilyDetailsRemoveChild(), Utils.getLocalForm("family_details_remove_child", locale, assetManager));
    }

    @Test
    public void testGetFamilyDetailsRemoveFamilyReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getFamilyDetailsRemoveFamily(), Utils.getLocalForm("family_details_remove_family", locale, assetManager));
    }

    @Test
    public void testGetHomeVisitCounsellingReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getHomeVisitCounselling(), Utils.getLocalForm("routine_home_visit", locale, assetManager));
    }

    @Test
    public void testGetPregnancyOutcomeReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getPregnancyOutcome(), Utils.getLocalForm("anc_pregnancy_outcome", locale, assetManager));
    }

    @Test
    public void testGetMalariaConfirmationReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getMalariaConfirmation(), Utils.getLocalForm("malaria_confirmation", locale, assetManager));
    }

    @Test
    public void testGetMalariaFollowUpVisitFormReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getMalariaFollowUpVisitForm(), Utils.getLocalForm("malaria_follow_up_visit", locale, assetManager));
    }

    @Test
    public void testGetRoutineHouseholdVisitReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getRoutineHouseholdVisit(), Utils.getLocalForm("routine_household_visit", locale, assetManager));
    }

    @Test
    public void testGetReferralFollowupFormReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getReferralFollowupForm(), Utils.getLocalForm("referrals/referral_followup_neat_form", locale, assetManager));
    }

    @Test
    public void testGetMalariaFollowUpHfFormReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getMalariaFollowUpHfForm(), Utils.getLocalForm("malaria_follow_up_hf", locale, assetManager));
    }

    @Test
    public void testGetVaccineCardReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getVaccineCard(), Utils.getLocalForm("child_hv_vaccine_card_received", locale, assetManager));
    }

    @Test
    public void testGetVitaminAReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getVitaminA(), Utils.getLocalForm("child_hv_vitamin_a", locale, assetManager));
    }

    @Test
    public void testGetDewormingReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getDEWORMING(), Utils.getLocalForm("child_hv_deworming", locale, assetManager));
    }

    @Test
    public void testGetMUACReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getMUAC(), Utils.getLocalForm("child_hv_muac", locale, assetManager));
    }

    @Test
    public void testGetDIETARYReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getDIETARY(), Utils.getLocalForm("child_hv_dietary_diversity", locale, assetManager));
    }

    @Test
    public void testGetMNPReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getMNP(), Utils.getLocalForm("child_hv_mnp", locale, assetManager));
    }

    @Test
    public void testGetMalariaPreventionReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getMalariaPrevention(), Utils.getLocalForm("child_hv_malaria_prevention", locale, assetManager));
    }

    @Test
    public void testGetSleepingUnderLlitnReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getSleepingUnderLlitn(), Utils.getLocalForm("child_hv_sleeping_under_llitn", locale, assetManager));
    }

    @Test
    public void testGetNutritionStatusReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getNutritionStatus(), Utils.getLocalForm("child_hv_nutrition_status", locale, assetManager));
    }


    @Test
    public void testGetFamilyMemberRegisterReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getFamilyMemberRegister(), Utils.getLocalForm("family_member_register", locale, assetManager));
    }

    @Test
    public void testGetChildRegisterReturnsCorrectConstant() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(CoreConstants.JSON_FORM.getChildRegister(), Utils.getLocalForm("child_enrollment", locale, assetManager));
    }

    @Test
    public void testGetStockUsageFormReturnsCorrectConstant() {
        Assert.assertEquals("stock_usage_report", CoreConstants.JSON_FORM.getStockUsageForm());
    }

    @Test
    public void testGetMalariaReferralFormReturnsCorrectConstant() {
        Assert.assertEquals("referrals/malaria_referral_form", CoreConstants.JSON_FORM.getMalariaReferralForm());
    }

    @Test
    public void testGetHivReferralFormReturnsCorrectConstant() {
        Assert.assertEquals("referrals/hiv_referral_form", CoreConstants.JSON_FORM.getHivReferralForm());
    }

    @Test
    public void testTbReferralFormReturnsCorrectConstant() {
        Assert.assertEquals("referrals/tb_referral_form", CoreConstants.JSON_FORM.getTbReferralForm());
    }

    @Test
    public void testGbvReferralFormReturnsCorrectConstant() {
        Assert.assertEquals("referrals/gbv_referral_form", CoreConstants.JSON_FORM.getGbvReferralForm());
    }

    @Test
    public void testChildGbvReferralFormReturnsCorrectConstant() {
        Assert.assertEquals("referrals/child_gbv_referral_form", CoreConstants.JSON_FORM.getChildGbvReferralForm());
    }

    @Test
    public void testPncDangerSignsOutcomeFormReturnsCorrectConstant() {
        Assert.assertEquals("pnc_danger_signs_outcome", CoreConstants.JSON_FORM.getPncDangerSignsOutcomeForm());
    }
}