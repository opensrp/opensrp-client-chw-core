package org.smartregister.chw.core.rule;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.smartregister.chw.core.utils.CoreConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HivFollowupRule implements ICommonRule {
    public static final String RULE_KEY = "hivFollowupRule";
    private String visitID;
    private DateTime hivDate;
    private DateTime dueDate;
    private DateTime overDueDate;
    private DateTime lastVisitDate;
    private DateTime expiryDate;
    private int daysDifference;

    public HivFollowupRule(Date hivDate, Date lastVisitDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        this.hivDate = hivDate != null ? new DateTime(sdf.format(hivDate)) : null;
        this.lastVisitDate = lastVisitDate == null ? null : new DateTime(lastVisitDate);
    }

    public String getVisitID() {
        return visitID;
    }

    public void setVisitID(String visitID) {
        this.visitID = visitID;
    }

    public int getDaysDifference() {
        return daysDifference;
    }

    public boolean updateDueDate(int scheduledPeriodInDays, int daysFromDueToOverdue, int daysFromOverdueTillExpiry) {
        if (lastVisitDate != null) {
            this.dueDate = lastVisitDate.plusDays(scheduledPeriodInDays);
        } else {
            this.dueDate = hivDate.plusDays(scheduledPeriodInDays);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dueDate.toDate());
        calendar.add(Calendar.DATE, daysFromDueToOverdue);
        this.overDueDate = new DateTime(calendar.getTime());

        calendar.setTime(overDueDate.toDate());
        calendar.add(Calendar.DATE, daysFromOverdueTillExpiry);
        this.expiryDate = new DateTime(calendar.getTime());

        daysDifference = Days.daysBetween(new DateTime(), new DateTime(dueDate)).getDays();
        return true;
    }

    public Date getDueDate() {
        return dueDate != null ? dueDate.toDate() : null;
    }

    public Date getOverDueDate() {
        return overDueDate != null ? overDueDate.toDate() : null;
    }

    public Date getExpiryDate() {
        return expiryDate != null ? expiryDate.toDate() : null;
    }

    public Date getCompletionDate() {
        if (lastVisitDate != null && ((lastVisitDate.isAfter(dueDate) || lastVisitDate.isEqual(dueDate))))
            return lastVisitDate.toDate();

        return null;
    }

    @Override
    public String getRuleKey() {
        return "hivFollowupRule";
    }

    @Override
    public String getButtonStatus() {
        DateTime currentDate = new DateTime(new LocalDate().toDate());
        DateTime lastVisit = lastVisitDate;

        if (lastVisitDate != null) {
            if ((lastVisit.isAfter(dueDate) || lastVisit.isEqual(dueDate)) && lastVisit.isBefore(expiryDate))
                return CoreConstants.VISIT_STATE.VISIT_DONE;
            if (lastVisit.isBefore(dueDate)) {
                if (currentDate.isBefore(expiryDate) && (currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate)))
                    return CoreConstants.VISIT_STATE.OVERDUE;
                if (currentDate.isBefore(overDueDate) && (currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate)))
                    return CoreConstants.VISIT_STATE.DUE;
            }
        } else {
            if (currentDate.isBefore(expiryDate) && (currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate)))
                return CoreConstants.VISIT_STATE.OVERDUE;

            if (currentDate.isBefore(overDueDate) && (currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate)))
                return CoreConstants.VISIT_STATE.DUE;
        }
        return CoreConstants.VISIT_STATE.EXPIRED;
    }
}