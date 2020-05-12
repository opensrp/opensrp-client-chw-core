package org.smartregister.chw.core.utils;

import android.content.Context;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.smartregister.chw.core.R;

@RunWith(RobolectricTestRunner.class)
public class ChwNotificationUtilTest {

    @Test
    public void getNotificationsTableReturnsCorrectValue() {
        Context context = RuntimeEnvironment.application;
        String sickChildTable = "ec_sick_child_followup";
        String familyPlanningTable = "ec_family_planning_update";
        Assert.assertEquals(sickChildTable, ChwNotificationUtil.getNotificationDetailsTable(context, context.getString(R.string.notification_type_sick_child_follow_up)));
        Assert.assertEquals(familyPlanningTable, ChwNotificationUtil.getNotificationDetailsTable(context, context.getString(R.string.notification_type_family_planning)));
    }

    @Test
    public void canGetStringFromJSONArrayString() {
        String jsonArrayString = "[\"Value Uno\", \"Value dos\"]";
        String expectedString = "Value Uno,Value dos";
        Assert.assertEquals(expectedString, ChwNotificationUtil.getStringFromJSONArrayString(jsonArrayString));
        jsonArrayString = "Value uno";
        expectedString = "Value uno";
        Assert.assertEquals(expectedString, ChwNotificationUtil.getStringFromJSONArrayString(jsonArrayString));
    }
}
