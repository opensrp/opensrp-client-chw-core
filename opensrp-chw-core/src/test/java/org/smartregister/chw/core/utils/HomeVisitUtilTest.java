package org.smartregister.chw.core.utils;

import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.smartregister.chw.core.rule.AncVisitAlertRule;

public class HomeVisitUtilTest {

    private Context context;

    @Test
    public void canGetVisitSummaryFromVisitAlert() {
        String visitDate = "12-12-2019";
        String visitNotDate = "01-01-2020";

        DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MMM-dd");
        DateTime dateTime = FORMATTER.parseDateTime("2020-jan-01");
        LocalDate dateCreated = dateTime.toLocalDate();

        AncVisitAlertRule ancVisitAlertRule = new AncVisitAlertRule(context, DateTimeFormat.forPattern("dd-MM-yyyy").print(dateCreated), visitDate, visitNotDate, dateCreated);
        VisitSummary summary = HomeVisitUtil.getAncVisitStatus(ancVisitAlertRule, null);
        Assert.assertNotNull(summary);
    }
}
