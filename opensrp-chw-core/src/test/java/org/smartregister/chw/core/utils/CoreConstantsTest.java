package org.smartregister.chw.core.utils;

import android.content.res.AssetManager;
import android.os.Build;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.application.TestApplication;

import java.util.Locale;

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
    public void getRegisterType() {
        Assert.assertEquals(CoreConstants.REGISTER_TYPE.ANC, "ANC");
        Assert.assertEquals(CoreConstants.REGISTER_TYPE.PNC, "PNC");
        Assert.assertEquals(CoreConstants.REGISTER_TYPE.CHILD, "Child");
        Assert.assertEquals(CoreConstants.REGISTER_TYPE.FAMILY_PLANNING, "Family Planning");
        Assert.assertEquals(CoreConstants.REGISTER_TYPE.MALARIA, "Malaria");
        Assert.assertEquals(CoreConstants.REGISTER_TYPE.OTHER, "Other");
    }

    @Test
    public void getMenuType() {
        Assert.assertEquals(CoreConstants.MenuType.ChangeHead, "ChangeHead");
        Assert.assertEquals(CoreConstants.MenuType.ChangePrimaryCare, "ChangePrimaryCare");
    }

    @Test
    public void TestGetFamilyKit() {
        Locale locale = CoreChwApplication.getCurrentLocale();
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        Assert.assertEquals("family_kit", Utils.getLocalForm("family_kit", locale, assetManager));
    }
}