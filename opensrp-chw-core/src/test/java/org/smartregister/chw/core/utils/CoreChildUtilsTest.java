package org.smartregister.chw.core.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;

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
}
