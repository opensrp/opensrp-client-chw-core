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
    public void getUnifiedReferralForms() {
        Assert.assertEquals(CoreConstants.JSON_FORM.getAncUnifiedReferralForm(), "referrals/anc_referral_form");
        Assert.assertEquals(CoreConstants.JSON_FORM.getChildUnifiedReferralForm(), "referrals/child_referral_form");
        Assert.assertEquals(CoreConstants.JSON_FORM.getPncUnifiedReferralForm(), "referrals/pnc_referral_form");
    }

    @Test
    public void getReferralForms() {
        Assert.assertEquals(CoreConstants.JSON_FORM.getChildReferralForm(), "child_referral_form");
        Assert.assertEquals(CoreConstants.JSON_FORM.getAncReferralForm(), "anc_referral_form");
        Assert.assertEquals(CoreConstants.JSON_FORM.getPncReferralForm(), "pnc_referral_form");
    }

    @Test
    public void getMenuType() {
        Assert.assertEquals(CoreConstants.MenuType.ChangeHead, "ChangeHead");
        Assert.assertEquals(CoreConstants.MenuType.ChangePrimaryCare, "ChangePrimaryCare");
    }

    @Test
    public void testGetFamilyKit() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("family_kit", locale, assetManager), CoreConstants.JSON_FORM.getFamilyKit());
    }

    @Test
    public void testGetWashCheck() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("wash_check", locale, assetManager), CoreConstants.JSON_FORM.getWashCheck());
    }

    @Test
    public void testGetFamilyRegister() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("family_register", locale, assetManager), CoreConstants.JSON_FORM.getFamilyRegister());
    }

    @Test
    public void testGetBirthCertification() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("birth_certification", locale, assetManager), CoreConstants.JSON_FORM.getBirthCertification());
    }

    @Test
    public void testGetDisability() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_disability", locale, assetManager), CoreConstants.JSON_FORM.getDisability());
    }

    @Test
    public void testGetObsIllness() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("observation_illness", locale, assetManager), CoreConstants.JSON_FORM.getObsIllness());
    }

    @Test
    public void testGetChildSickForm() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_sick_form", locale, assetManager), CoreConstants.JSON_FORM.getChildSickForm());
    }

    @Test
    public void testGetFamilyDetailsRegister() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("family_details_register", locale, assetManager), CoreConstants.JSON_FORM.getFamilyDetailsRegister());
    }

    @Test
    public void testGetFamilyDetailsRemoveMember() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("family_details_remove_member", locale, assetManager), CoreConstants.JSON_FORM.getFamilyDetailsRemoveMember());
    }

    @Test
    public void testGetFamilyDetailsRemoveChild() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("family_details_remove_child", locale, assetManager), CoreConstants.JSON_FORM.getFamilyDetailsRemoveChild());
    }

    @Test
    public void testGetFamilyDetailsRemoveFamily() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("family_details_remove_family", locale, assetManager), CoreConstants.JSON_FORM.getFamilyDetailsRemoveFamily());
    }

    @Test
    public void testGetHomeVisitCounselling() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("routine_home_visit", locale, assetManager), CoreConstants.JSON_FORM.getHomeVisitCounselling());
    }

    @Test
    public void testGetPregnancyOutcome() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("anc_pregnancy_outcome", locale, assetManager), CoreConstants.JSON_FORM.getPregnancyOutcome());
    }

    @Test
    public void testGetMalariaConfirmation() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("malaria_confirmation", locale, assetManager), CoreConstants.JSON_FORM.getMalariaConfirmation());
    }

    @Test
    public void testGetMalariaFollowUpVisitForm() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("malaria_follow_up_visit", locale, assetManager), CoreConstants.JSON_FORM.getMalariaFollowUpVisitForm());
    }

    @Test
    public void testGetRoutineHouseholdVisit() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("routine_household_visit", locale, assetManager), CoreConstants.JSON_FORM.getRoutineHouseholdVisit());
    }

    @Test
    public void testGetReferralFollowupForm() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("referrals/referral_followup_neat_form", locale, assetManager), CoreConstants.JSON_FORM.getReferralFollowupForm());
    }

    @Test
    public void testGetMalariaFollowUpHfForm() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("malaria_follow_up_hf", locale, assetManager), CoreConstants.JSON_FORM.getMalariaFollowUpHfForm());
    }

    @Test
    public void testGetVaccineCard() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_hv_vaccine_card_received", locale, assetManager), CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getVaccineCard());
    }

    @Test
    public void testGetVitaminA() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_hv_vitamin_a", locale, assetManager), CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getVitaminA());
    }

    @Test
    public void testGetDeworming() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_hv_deworming", locale, assetManager), CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getDEWORMING());
    }

    @Test
    public void testGetMUAC() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_hv_muac", locale, assetManager), CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getMUAC());
    }

    @Test
    public void testGetDIETARY() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_hv_dietary_diversity", locale, assetManager), CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getDIETARY());
    }

    @Test
    public void testGetMNP() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_hv_mnp", locale, assetManager), CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getMNP());
    }

    @Test
    public void testGetMalariaPrevention() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_hv_malaria_prevention", locale, assetManager), CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getMalariaPrevention());
    }

    @Test
    public void testGetSleepingUnderLlitn() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_hv_sleeping_under_llitn", locale, assetManager), CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getSleepingUnderLlitn());
    }

    @Test
    public void testGetNutritionStatus() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_hv_nutrition_status", locale, assetManager), CoreConstants.JSON_FORM.CHILD_HOME_VISIT.getNutritionStatus());
    }


    @Test
    public void testGetFamilyMemberRegister() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("family_member_register", locale, assetManager), CoreConstants.JSON_FORM.getFamilyMemberRegister());
    }

    @Test
    public void testGetChildRegister() {
        Locale locale = CoreChwApplication.getInstance().getResources().getConfiguration().locale;
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        ReflectionHelpers.setField(jsonForm, "assetManager", assetManager);
        ReflectionHelpers.setField(jsonForm, "locale", locale);
        Assert.assertEquals(Utils.getLocalForm("child_enrollment", locale, assetManager), CoreConstants.JSON_FORM.getChildRegister());
    }

    @Test
    public void testGetStockUsageForm() {
        Assert.assertEquals("stock_usage_report", CoreConstants.JSON_FORM.getStockUsageForm());
    }

    @Test
    public void testGetMalariaReferralForm() {
        Assert.assertEquals("referrals/malaria_referral_form", CoreConstants.JSON_FORM.getMalariaReferralForm());
    }

    @Test
    public void testGetHivReferralForm() {
        Assert.assertEquals("referrals/hiv_referral_form", CoreConstants.JSON_FORM.getHivReferralForm());
    }

    @Test
    public void testTbReferralForm() {
        Assert.assertEquals("referrals/tb_referral_form", CoreConstants.JSON_FORM.getTbReferralForm());
    }

    @Test
    public void testGbvReferralForm() {
        Assert.assertEquals("referrals/gbv_referral_form", CoreConstants.JSON_FORM.getGbvReferralForm());
    }

    @Test
    public void testChildGbvReferralForm() {
        Assert.assertEquals("referrals/child_gbv_referral_form", CoreConstants.JSON_FORM.getChildGbvReferralForm());
    }

    @Test
    public void testPncDangerSignsOutcomeForm() {
        Assert.assertEquals("pnc_danger_signs_outcome", CoreConstants.JSON_FORM.getPncDangerSignsOutcomeForm());
    }
}