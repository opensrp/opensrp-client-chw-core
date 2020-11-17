package org.smartregister.chw.core.rule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseRobolectricTest;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FpAlertRuleTest extends BaseRobolectricTest {

    private FpAlertRule fpAlertRule;

    @Before
    public void setUp() throws Exception {
        Date fpDate = new SimpleDateFormat("dd-MM-yyyy").parse("17-11-2020");
        Date lastVisitDate = new SimpleDateFormat("dd-MM-yyyy").parse("17-10-2020");
        fpAlertRule = new FpAlertRule(fpDate, lastVisitDate, 10, FamilyPlanningConstants.DBConstants.FP_COC);
        ReflectionHelpers.setField(fpAlertRule, "visitID", "test-visit-id");
    }


    @Test
    public void canGetVisitId() {
        Assert.assertEquals("test-visit-id", fpAlertRule.getVisitID());
    }


    @Test
    public void canGetIfFemaleSterilizationFollowUpOneValid() {
        ReflectionHelpers.setField(fpAlertRule, "fpDifference", 5);
        Assert.assertFalse(fpAlertRule.isFemaleSterilizationFollowUpOneValid(10, 15, 20));
        Assert.assertTrue(fpAlertRule.isFemaleSterilizationFollowUpOneValid(2, 6, 10));
    }

}
