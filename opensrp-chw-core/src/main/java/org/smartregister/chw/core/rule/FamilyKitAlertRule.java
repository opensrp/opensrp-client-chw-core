package org.smartregister.chw.core.rule;

import android.content.Context;

//All date formats ISO 8601 yyyy-mm-dd

/**
 * Created by Qazi Abubakar
 */
public class FamilyKitAlertRule extends MonthlyAlertRule {

    public FamilyKitAlertRule(Context context, long lastVisitDateLong, long dateCreatedLong) {
        super(context, lastVisitDateLong, dateCreatedLong);
    }

    @Override
    public String getRuleKey() {
        return "familyKitAlertRule";
    }

}
