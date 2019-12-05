package org.smartregister.chw.core.rule;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FPAlertRule implements ICommonRule {
    private String visitID;
    private DateTime fpDate;
    private DateTime dueDate;
    private DateTime overDueDate;
    private DateTime lastVisitDate;
    private DateTime expiryDate;
    private int fpDifference;
    private Integer pillCycles;
    private String fpMethod;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    public FPAlertRule(Date fpDate, Date lastVisitDate, Integer pillCycles, String fpMethod) {
        this.pillCycles = pillCycles == null ? 0 : pillCycles;
        this.fpDate = fpDate == null ? null : new DateTime(sdf.format(fpDate));
        this.lastVisitDate = lastVisitDate == null ? null : new DateTime(lastVisitDate);
        this.fpMethod = fpMethod;
        fpDifference = Days.daysBetween(new DateTime(fpDate), new DateTime()).getDays();
    }

    public String getVisitID() {
        return visitID;
    }

    public void setVisitID(String visitID) {
        this.visitID = visitID;
    }

    public boolean isCocPopValid(int dueDay, int overdueDate) {
        this.dueDate = new DateTime(fpDate).plusDays(pillCycles * 28).minusDays(dueDay);
        this.overDueDate = new DateTime(fpDate).plus(pillCycles * 28).minusDays(overdueDate);
        return (fpMethod.equalsIgnoreCase(FamilyPlanningConstants.DBConstants.FP_COC) || fpMethod.equalsIgnoreCase(FamilyPlanningConstants.DBConstants.FP_POP));
    }

    public boolean isCondomValid(int dueDay, int overdueDate) {
        this.dueDate = new DateTime().withDayOfMonth(dueDay);
        this.overDueDate = new DateTime().withDayOfMonth(overdueDate);
        return (fpMethod.equalsIgnoreCase(FamilyPlanningConstants.DBConstants.FP_FEMALE_CONDOM) || fpMethod.equalsIgnoreCase(FamilyPlanningConstants.DBConstants.FP_MALE_CONDOM));
    }

    public boolean isInjectionValid(int dueDay, int overdueDate) {
        this.dueDate = new DateTime(fpDate).plusDays(dueDay);
        this.overDueDate = new DateTime(fpDate).plusDays(overdueDate);
        return fpMethod.equalsIgnoreCase(FamilyPlanningConstants.DBConstants.FP_INJECTABLE);
    }

    public boolean isFemaleSterilizationFollowUpOneValid(int dueDay, int overdueDate, int expiry) {
        this.dueDate = new DateTime(fpDate).plusDays(dueDay);
        this.overDueDate = new DateTime(fpDate).plusDays(overdueDate);
        this.expiryDate = new DateTime(fpDate).plusDays(expiry);
        return (fpDifference >= dueDay && fpDifference < expiry && fpMethod.equalsIgnoreCase(FamilyPlanningConstants.DBConstants.FP_FEMALE_STERLIZATION));
    }

    public boolean isFemaleSterilizationFollowUpTwoValid(int dueDay, int overdueDate, int expiry) {
        this.dueDate = new DateTime(fpDate).plusDays(dueDay);
        this.overDueDate = new DateTime(fpDate).plusDays(overdueDate);
        this.expiryDate = new DateTime(fpDate).plusMonths(expiry);
        int expiryDiff = Days.daysBetween(new DateTime(fpDate), this.expiryDate).getDays();
        return (fpDifference >= dueDay && fpDifference < expiryDiff && fpMethod.equalsIgnoreCase(FamilyPlanningConstants.DBConstants.FP_FEMALE_STERLIZATION));
    }

    public boolean isFemaleSterilizationFollowUpThreeValid(int dueDay, int overdueDate, int expiry) {
        this.dueDate = new DateTime(fpDate).plusMonths(dueDay);
        this.overDueDate = new DateTime(fpDate).plusMonths(overdueDate).plusDays(2);
        this.expiryDate = new DateTime(fpDate).plusMonths(expiry);
        int dueDiff = Days.daysBetween(new DateTime(fpDate), this.dueDate).getDays();
        int expiryDiff = Days.daysBetween(new DateTime(fpDate), this.expiryDate).getDays();
        return (fpDifference >= dueDiff && fpDifference < expiryDiff && fpMethod.equalsIgnoreCase(FamilyPlanningConstants.DBConstants.FP_FEMALE_STERLIZATION));
    }

    public boolean isIUCDValid(int dueDay, int overdueDate, int expiry) {
        this.dueDate = new DateTime(fpDate).plusMonths(dueDay);
        this.overDueDate = new DateTime(fpDate).plusMonths(overdueDate).plusDays(2);
        this.expiryDate = new DateTime(fpDate).plusMonths(expiry);
        int dueDiff = Days.daysBetween(new DateTime(fpDate), this.dueDate).getDays();
        int expiryDiff = Days.daysBetween(new DateTime(fpDate), this.expiryDate).getDays();
        return (fpDifference >= dueDiff && fpDifference < expiryDiff && fpMethod.equalsIgnoreCase(FamilyPlanningConstants.DBConstants.FP_IUCD));
    }

    public Integer getPillCycles() {
        return pillCycles;
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
        if (lastVisitDate != null && ((lastVisitDate.isAfter(dueDate) || lastVisitDate.isEqual(dueDate)) && lastVisitDate.isBefore(expiryDate)))
            return lastVisitDate.toDate();

        return null;
    }

    @Override
    public String getRuleKey() {
        return "fpAlertRule";
    }

    @Override
    public String getButtonStatus() {
        DateTime lastVisit = lastVisitDate;
        DateTime currentDate = new DateTime(new LocalDate().toDate());

        if (lastVisitDate != null) {
            if (expiryDate != null) {
                if ((lastVisit.isAfter(dueDate) || lastVisit.isEqual(dueDate)) && lastVisit.isBefore(expiryDate))
                    return CoreConstants.VISIT_STATE.VISIT_DONE;
                if (lastVisit.isBefore(dueDate)) {
                    if (currentDate.isBefore(overDueDate) && (currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate)))
                        return CoreConstants.VISIT_STATE.DUE;

                    if (currentDate.isBefore(expiryDate) && (currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate)))
                        return CoreConstants.VISIT_STATE.OVERDUE;
                }
            } else {
                if ((lastVisit.isAfter(dueDate) || lastVisit.isEqual(dueDate)) && lastVisit.isBefore(overDueDate))
                    return CoreConstants.VISIT_STATE.VISIT_DONE;
                if (lastVisit.isBefore(dueDate)) {
                    if (currentDate.isBefore(overDueDate) && (currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate)))
                        return CoreConstants.VISIT_STATE.DUE;

                    if (currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate))
                        return CoreConstants.VISIT_STATE.OVERDUE;
                }
            }

        } else {
            if (expiryDate != null) {
                if (currentDate.isBefore(overDueDate) && (currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate)))
                    return CoreConstants.VISIT_STATE.DUE;

                if (currentDate.isBefore(expiryDate) && (currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate)))
                    return CoreConstants.VISIT_STATE.OVERDUE;
            } else {
                if (currentDate.isBefore(overDueDate) && (currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate)))
                    return CoreConstants.VISIT_STATE.DUE;

                if ((currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate)))
                    return CoreConstants.VISIT_STATE.OVERDUE;
            }
        }

        return CoreConstants.VISIT_STATE.EXPIRED;
    }
}
