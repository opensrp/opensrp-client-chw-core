package org.smartregister.chw.core.utils;

import org.junit.Assert;
import org.junit.Test;


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
        Assert.assertEquals(CoreConstants.REGISTER_TYPE.OTHER, "OTHER");
    }

}