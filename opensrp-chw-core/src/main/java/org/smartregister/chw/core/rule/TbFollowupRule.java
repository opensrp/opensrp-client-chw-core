package org.smartregister.chw.core.rule;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.smartregister.chw.core.utils.CoreConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class TbFollowupRule implements ICommonRule {
    public static final String RULE_KEY = "tbFollowupRule";
    private String visitID;
    private DateTime tbDate;
    private DateTime dueDate;
    private DateTime overDueDate;
    private DateTime lastVisitDate;
    private int daysDifference;

    public TbFollowupRule(Date tbDate, Date lastVisitDate) {
        this.tbDate = tbDate != null ? new DateTime(tbDate.getTime()) : null;
        this.lastVisitDate = lastVisitDate == null ? null : new DateTime(lastVisitDate);
    }

    public String getVisitID() {
        return visitID;
    }

    public int getDaysDifference() {
        return daysDifference;
    }

    public void setVisitID(String visitID) {
        this.visitID = visitID;
    }

    public boolean updateDueDate(int scheduledPeriodInDays, int overdueDays) {
        if (lastVisitDate != null) {
            this.dueDate = lastVisitDate.plusDays(scheduledPeriodInDays);
        } else {
            this.dueDate = tbDate.plusDays(scheduledPeriodInDays);
        }
        this.overDueDate = dueDate.plus(overdueDays);
        daysDifference = Days.daysBetween(new DateTime(),new DateTime(dueDate)).getDays();
        return true;
    }

    public Date getDueDate() {
        return dueDate != null ? dueDate.toDate() : null;
    }

    public Date getOverDueDate() {
        return overDueDate != null ? overDueDate.toDate() : null;
    }

    public Date getCompletionDate() {
        if (lastVisitDate != null && ((lastVisitDate.isAfter(dueDate) || lastVisitDate.isEqual(dueDate))))
            return lastVisitDate.toDate();

        return null;
    }

    @Override
    public String getRuleKey() {
        return "tbFollowupRule";
    }

    @Override
    public String getButtonStatus() {
        DateTime currentDate = new DateTime(new LocalDate().toDate());
        int monthOfYear = new DateTime(lastVisitDate).getMonthOfYear();
        int year = new DateTime(lastVisitDate).getYear();

        if (lastVisitDate != null) {
            if ((monthOfYear == DateTime.now().getMonthOfYear()) && (year == DateTime.now().getYear())) {
                return CoreConstants.VISIT_STATE.VISIT_DONE;
            }
            if (currentDate.isBefore(overDueDate))
                return CoreConstants.VISIT_STATE.DUE;
            if (currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate))
                return CoreConstants.VISIT_STATE.OVERDUE;

        } else {
            if (currentDate.isBefore(dueDate)) {
                return CoreConstants.VISIT_STATE.NOT_DUE_YET;
            }
            if (currentDate.isBefore(overDueDate) && (currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate)))
                return CoreConstants.VISIT_STATE.DUE;

            if ((currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate)))
                return CoreConstants.VISIT_STATE.OVERDUE;
        }

        return CoreConstants.VISIT_STATE.DUE;
    }
}