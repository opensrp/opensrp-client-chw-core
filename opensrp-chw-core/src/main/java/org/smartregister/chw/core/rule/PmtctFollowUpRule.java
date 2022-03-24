package org.smartregister.chw.core.rule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.smartregister.chw.core.dao.CorePmtctDao;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.pmtct.dao.PmtctDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PmtctFollowUpRule implements ICommonRule {

    public static final String RULE_KEY = "pmtctFollowUpRule";

    @NonNull
    private DateTime pmtctDate;
    @Nullable
    private DateTime latestFollowUpDate;
    private DateTime dueDate;
    private DateTime overDueDate;
    private DateTime expiryDate;
    private String baseEntityId;

    public PmtctFollowUpRule(Date pmtctDate, @Nullable Date latestFollowUpDate, String baseEntityId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        this.pmtctDate = pmtctDate != null ? new DateTime(sdf.format(pmtctDate)) : null;
        this.baseEntityId = baseEntityId;
        this.latestFollowUpDate = latestFollowUpDate == null ? null : new DateTime(sdf.format(latestFollowUpDate));
    }

    public int getDatesDiff() {
        return Days.daysBetween(new DateTime(pmtctDate), new DateTime()).getDays();
    }

    public boolean isValid(int dueDay, int overDueDay, int expiry) {
        if (latestFollowUpDate != null) {
            this.dueDate = latestFollowUpDate.plusDays(dueDay);
            this.overDueDate = latestFollowUpDate.plusDays(overDueDay);
            this.expiryDate = latestFollowUpDate.plusDays(expiry);
        } else {
            this.dueDate = pmtctDate.plusDays(dueDay);
            this.overDueDate = pmtctDate.plusDays(overDueDay);
            this.expiryDate = pmtctDate.plusDays(expiry);
        }
        return false;
    }

    public String getBaseEntityId() {
        return baseEntityId;
    }

    public boolean isFirstVisit() {
        return latestFollowUpDate == null;
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

    @Override
    public String getRuleKey() {
        return "pmtctFollowUpRule";
    }

    @Override
    public String getButtonStatus() {
        DateTime currentDate = new DateTime(new LocalDate().toDate());
        DateTime lastVisit = latestFollowUpDate;

        if (currentDate.isBefore(expiryDate)) {
            if ((currentDate.isAfter(overDueDate) || currentDate.isEqual(overDueDate)))
                return CoreConstants.VISIT_STATE.OVERDUE;
            if ((currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate)) && currentDate.isBefore(overDueDate))
                return CoreConstants.VISIT_STATE.DUE;
            if (lastVisit != null && currentDate.isEqual(lastVisit))
                return CoreConstants.VISIT_STATE.VISIT_DONE;
            return CoreConstants.VISIT_STATE.NOT_DUE_YET;

        }
        return CoreConstants.VISIT_STATE.EXPIRED;
    }

}
