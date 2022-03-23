package org.smartregister.chw.core.rule;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.smartregister.chw.core.utils.CoreConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;

public class HeiFollowupRule implements ICommonRule {

    public static final String RULE_KEY = "heiFollowupRule";
    private final DateTime startDate;
    @Nullable
    private final DateTime latestFollowupDate;
    private DateTime dueDate;
    private DateTime overDueDate;

    public HeiFollowupRule(Date startDate, @Nullable Date latestFollowupDate, String baseEntityId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        this.startDate = new DateTime(sdf.format(startDate));
        this.latestFollowupDate = latestFollowupDate != null ? new DateTime(sdf.format(latestFollowupDate)) : null;
        updateDueDates();
    }

    public int getDatesDiff() {
        return Days.daysBetween(new DateTime(startDate), new DateTime()).getDays();
    }

    public void updateDueDates() {
        if (latestFollowupDate != null) {
            this.dueDate = latestFollowupDate.plusDays(42);
            this.overDueDate = latestFollowupDate.plusDays(49);
            return;
        }
        if(isFirstVisit()){
            this.dueDate = startDate.plusDays(0);
            this.overDueDate = startDate.plusDays(7);
        }

    }

    public boolean isFirstVisit() {
        return latestFollowupDate == null;
    }

    public Date getDueDate() {
        return dueDate != null ? dueDate.toDate() : null;
    }

    public Date getOverDueDate() {
        return overDueDate != null ? overDueDate.toDate() : null;
    }


    @Override
    public String getRuleKey() {
        return RULE_KEY;
    }

    @Override
    public String getButtonStatus() {
        DateTime currentDate = new DateTime(new LocalDate().toDate());
        DateTime lastVisit = latestFollowupDate;

        if ((currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate)))
            return CoreConstants.VISIT_STATE.OVERDUE;
        if ((currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate)) && currentDate.isBefore(overDueDate))
            return CoreConstants.VISIT_STATE.DUE;
        if (lastVisit != null && currentDate.isEqual(lastVisit))
            return CoreConstants.VISIT_STATE.VISIT_DONE;
        return CoreConstants.VISIT_STATE.NOT_DUE_YET;


    }
}
