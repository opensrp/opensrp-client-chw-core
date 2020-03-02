package org.smartregister.chw.core.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;

public class StockUsageTest {
    private StockUsage stockUsage = new StockUsage();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAndSetCreatedAt() {
        stockUsage.setCreatedAt(new Date());
        Assert.assertEquals(stockUsage.getCreatedAt(), new Date());
    }

    @Test
    public void testGetAndSetUpdatedAt() {
        stockUsage.setUpdatedAt(new Date());
        Assert.assertEquals(stockUsage.getUpdatedAt(), new Date());
    }

    @Test
    public void testSetAndGetId() {
        stockUsage.setId("123");
        Assert.assertEquals(stockUsage.getId(), "123");
    }

    @Test
    public void testGetAndSetStockName() {
        stockUsage.setStockName("zinc 10");
        Assert.assertEquals(stockUsage.getStockName(), "zinc 10");
    }

    @Test
    public void testGetAndSetStockUsage() {
        stockUsage.setStockUsage("30");
        Assert.assertEquals(stockUsage.getStockUsage(), "30");
    }

    @Test
    public void testGetAndSetYear() {
        stockUsage.setYear("2020");
        Assert.assertEquals(stockUsage.getYear(), "2020");
    }

    @Test
    public void testSetAndGetMonth() {
        stockUsage.setMonth("January");
        Assert.assertEquals(stockUsage.getMonth(), "January");
    }
}