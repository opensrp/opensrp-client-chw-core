package org.smartregister.chw.core.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;

public class DailyTallyTest {
    private DailyTally dailyTally = new DailyTally();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAndSetDay() {
        dailyTally.setDay(new Date());
        Assert.assertEquals(dailyTally.getDay(), new Date());
    }
}
