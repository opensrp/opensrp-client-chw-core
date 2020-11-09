package org.smartregister.chw.core.utils;

import android.os.Build;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.TestApplication;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, sdk = Build.VERSION_CODES.P)
public class CoreConstantsTest {

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
}