package org.smartregister.chw.core.rule;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.smartregister.chw.core.utils.CoreConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HivAlertRule implements ICommonRule {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String visitID;
    private DateTime hivDate;
    private DateTime dueDate;
    private DateTime overDueDate;
    private DateTime lastVisitDate;
    private int tbDifference;

    public HivAlertRule(Date hivDate, Date lastVisitDate) {
        this.hivDate = hivDate != null ? new DateTime(sdf.format(hivDate)) : null;
        this.lastVisitDate = lastVisitDate == null ? null : new DateTime(lastVisitDate);
        tbDifference = Days.daysBetween(new DateTime(hivDate), new DateTime()).getDays();
    }

    public String getVisitID() {
        return visitID;
    }

    public void setVisitID(String visitID) {
        this.visitID = visitID;
    }

    public boolean updateDueDate(int dueDay, int overdueDate) {
        if (lastVisitDate != null) {
            int monthOfYear = new DateTime(lastVisitDate).getMonthOfYear();
            int year = new DateTime(lastVisitDate).getYear();
            if ((monthOfYear == DateTime.now().getMonthOfYear()) && (year == DateTime.now().getYear())) {
                this.dueDate = new DateTime().plusMonths(1).withDayOfMonth(dueDay);
                this.overDueDate = new DateTime().plusMonths(1).withDayOfMonth(overdueDate);
            } else {
                if ((year == DateTime.now().getYear()) && ((DateTime.now().getMonthOfYear()) - (monthOfYear) == 1)) {
                    this.dueDate = new DateTime().withDayOfMonth(dueDay);
                    this.overDueDate = new DateTime().withDayOfMonth(overdueDate);
                } else {
                    this.dueDate = lastVisitDate.withDayOfMonth(dueDay).plusMonths(1);
                    this.overDueDate = lastVisitDate.withDayOfMonth(overdueDate).plusMonths(1);
                }
            }
        } else {
            this.dueDate = hivDate.plusMonths(1).withDayOfMonth(dueDay);
            this.overDueDate = hivDate.plusMonths(1).withDayOfMonth(overdueDate);
        }
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
        return "hivAlertRule";
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