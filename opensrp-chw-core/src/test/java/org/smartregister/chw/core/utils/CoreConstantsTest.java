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

}