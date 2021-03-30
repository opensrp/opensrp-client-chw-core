package org.smartregister.chw.core.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.enums.ImmunizationState;

public class CoreChildUtilsTest extends BaseUnitTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDobStringToYear() {
        Integer expectedYear = 5;
        Assert.assertEquals(expectedYear, CoreChildUtils.dobStringToYear("5y"));
    }

    @Test
    public void testGetImmunizationExpired() {
        String expectedStatus = "false";
        Assert.assertEquals(expectedStatus, CoreChildUtils.getImmunizationExpired("2019-09-10T19:00:00.000-05:00", "bcg"));
    }

    @Test
    public void testGetDueStatus() {
        ImmunizationState immunizationState = ImmunizationState.OVERDUE;
        Assert.assertEquals(immunizationState, CoreChildUtils.getDueStatus("2019-09-10"));
    }
}
