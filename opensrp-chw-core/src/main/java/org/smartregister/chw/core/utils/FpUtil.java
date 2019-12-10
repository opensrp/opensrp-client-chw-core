package org.smartregister.chw.core.utils;

import org.jeasy.rules.api.Rules;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class FpUtil extends org.smartregister.chw.fp.util.FpUtil {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    public static List<Rules> getFpRules(String fpMethod) {
        List<Rules> fpRules = new ArrayList<>();

        switch (fpMethod) {
            case FamilyPlanningConstants.DBConstants.FP_POP:
            case FamilyPlanningConstants.DBConstants.FP_COC:
                fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_COC_POP_REFILL));
                break;
            case FamilyPlanningConstants.DBConstants.FP_IUCD:
                fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_IUCD));
                break;
            case FamilyPlanningConstants.DBConstants.FP_FEMALE_CONDOM:
            case FamilyPlanningConstants.DBConstants.FP_MALE_CONDOM:
                fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_CONDOM_REFILL));
                break;
            case FamilyPlanningConstants.DBConstants.FP_INJECTABLE:
                fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_INJECTION_DUE));
            case FamilyPlanningConstants.DBConstants.FP_FEMALE_STERLIZATION:
                fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_FEMALE_STERILIZATION));
                break;
            default:
                break;
        }
        return fpRules;
    }

    public static Date parseFpStartDate(String startDate) {
        try {
            return sdf.parse(startDate);
        } catch (ParseException e) {
            Timber.e(e);
        }

        return null;
    }

}
