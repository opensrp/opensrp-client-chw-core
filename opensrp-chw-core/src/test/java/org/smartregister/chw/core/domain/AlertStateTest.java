package org.smartregister.chw.core.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class AlertStateTest {
    private String caseID = "case-1234";
    private String startDate = "2020-09-05";
    private String visitCode = "visit-0001";
    private String dateGiven = "2020-10-01";
    private AlertState alertState = new AlertState(caseID, startDate, visitCode, dateGiven);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAndSetCaseID() {
        alertState.setCaseID(caseID);
        Assert.assertEquals(alertState.getCaseID(), caseID);
    }

    @Test
    public void testGetAndSetStartDate() {
        alertState.setStartDate(startDate);
        Assert.assertEquals(alertState.getStartDate(), startDate);
    }

    @Test
    public void testGetAndSetVisitCode() {
        alertState.setVisitCode(visitCode);
        Assert.assertEquals(alertState.getVisitCode(), visitCode);
    }

    @Test
    public void testGetAndSetDateGiven() {
        alertState.setDateGiven(dateGiven);
        Assert.assertEquals(alertState.getDateGiven(), dateGiven);
    }

}