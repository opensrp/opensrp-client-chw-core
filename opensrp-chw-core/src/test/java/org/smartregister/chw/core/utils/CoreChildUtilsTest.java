package org.smartregister.chw.core.utils;

import android.content.Context;
import android.os.Build;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.TestApplication;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, sdk = Build.VERSION_CODES.P)

public class CoreChildUtilsTest  {

    private Context context;

    @Before
    public void setUp(){
        context= RuntimeEnvironment.application;
    }

    @Test
    public void testGetFirstSecondAsNumber() {
        Assert.assertEquals("1st",CoreChildUtils.getFirstSecondAsNumber("1",context));
        Assert.assertEquals("2nd",CoreChildUtils.getFirstSecondAsNumber("2", context));
        Assert.assertEquals("3rd",CoreChildUtils.getFirstSecondAsNumber("3", context));
        Assert.assertEquals("4th",CoreChildUtils.getFirstSecondAsNumber("4", context));
        Assert.assertEquals("5th",CoreChildUtils.getFirstSecondAsNumber("5", context));
        Assert.assertEquals("6th",CoreChildUtils.getFirstSecondAsNumber("6", context));
        Assert.assertEquals("7th",CoreChildUtils.getFirstSecondAsNumber("7", context));
        Assert.assertEquals("8th",CoreChildUtils.getFirstSecondAsNumber("8", context));
        Assert.assertEquals("9th",CoreChildUtils.getFirstSecondAsNumber("9", context));
        Assert.assertEquals("10th",CoreChildUtils.getFirstSecondAsNumber("10", context));
        Assert.assertEquals("11th",CoreChildUtils.getFirstSecondAsNumber("11", context));
        Assert.assertEquals("12th",CoreChildUtils.getFirstSecondAsNumber("12", context));
        Assert.assertEquals("",CoreChildUtils.getFirstSecondAsNumber("",context));

    }
}