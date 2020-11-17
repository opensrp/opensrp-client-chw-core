package org.smartregister.chw.core.rule;

import org.junit.Before;
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
    }





}
