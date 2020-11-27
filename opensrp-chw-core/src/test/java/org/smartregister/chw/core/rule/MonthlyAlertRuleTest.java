package org.smartregister.chw.core.rule;

import android.content.Context;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import java.util.Date;

/**
 * Created by Qazi Abubakar
 */
public class MonthlyAlertRuleTest {
    private final Context context = RuntimeEnvironment.application;
    private final long lastVisitDate = new DateTime().minusDays(7).toDate().getTime();
    private final long dateCreated = new DateTime().minusDays(30).toDate().getTime();
    private final MonthlyAlertRule monthlyAlertRule = new MonthlyAlertRule(context, lastVisitDate, dateCreated) {
        @Override
        public String getRuleKey() {
            return "familyKitAlertRule";
        }
    };

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLastDayOfMonth(){
        DateTime first = new DateTime(new Date()).withDayOfMonth(1);
        Date lastDate = first.plusMonths(1).minusDays(1).toDate();
        Assert.assertNotEquals(first.toDate(), monthlyAlertRule.getLastDayOfMonth(new Date()));
        Assert.assertEquals(lastDate.toString(), monthlyAlertRule.getLastDayOfMonth(new Date()).toString());
    }

    @Test
    public void testGetFirstDayOfMonth(){
        DateTime first = new DateTime(new Date()).withDayOfMonth(1);
        Date lastDate = first.plusMonths(1).minusDays(1).toDate();
        Assert.assertEquals(first.toDate(), monthlyAlertRule.getFirstDayOfMonth(new Date()));
        Assert.assertNotEquals(lastDate, monthlyAlertRule.getFirstDayOfMonth(new Date()));
    }
}
