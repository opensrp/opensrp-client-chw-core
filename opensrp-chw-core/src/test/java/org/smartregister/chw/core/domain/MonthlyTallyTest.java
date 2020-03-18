package org.smartregister.chw.core.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.*;

public class MonthlyTallyTest {

    private MonthlyTally monthlyTally = new MonthlyTally();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDateAndSetDateSent() {
        monthlyTally.setDateSent(new Date());
        Assert.assertEquals(monthlyTally.getDateSent(), new Date());
    }

    @Test
    public void testGetAndSetMonth() {
        monthlyTally.setMonth(new Date());
        Assert.assertEquals(monthlyTally.getMonth(), new Date());
    }

    @Test
    public void testIsAndSetEdited() {
        monthlyTally.setEdited(true);
        Assert.assertTrue(monthlyTally.isEdited());
    }
    @Test
    public void testGetAndSetProviderId() {
        String providerId = Mockito.anyString();
        monthlyTally.setProviderId(providerId);
        Assert.assertEquals(monthlyTally.getProviderId(), providerId);
    }

    @Test
    public void testGetAndSetUpdatedAt() {
        monthlyTally.setUpdatedAt(new Date());
        Assert.assertEquals(monthlyTally.getUpdatedAt(), new Date());
    }

    @Test
    public void testGetAndSetCreatedAt() {
        monthlyTally.setCreatedAt(new Date());
        Assert.assertEquals(monthlyTally.getCreatedAt(), new Date());
    }

}