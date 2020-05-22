package org.smartregister.chw.core.interactor;

import android.content.Context;

import org.jeasy.rules.api.Rules;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.interactor.BaseAncUpcomingServicesInteractor;
import org.smartregister.chw.anc.model.BaseUpcomingService;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.rule.HivAlertRule;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.FpUtil;
import org.smartregister.chw.core.utils.HomeVisitUtil;
import org.smartregister.chw.hiv.dao.HivDao;
import org.smartregister.chw.hiv.domain.HivAlertObject;
import org.smartregister.chw.tb.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoreHivUpcomingServicesInteractor extends BaseAncUpcomingServicesInteractor {
    protected MemberObject memberObject;
    protected Context context;

    @Override
    public List<BaseUpcomingService> getMemberServices(Context context, MemberObject memberObject) {
        this.memberObject = memberObject;
        this.context = context;
        List<BaseUpcomingService> serviceList = new ArrayList<>();
        evaluateFp(serviceList);
        return serviceList;
    }

    private void evaluateFp(List<BaseUpcomingService> serviceList) {
        String hiv_date = null;
        Rules rule = null;
        Date serviceDueDate = null;
        Date serviceOverDueDate = null;
        String serviceName = null;

        List<HivAlertObject> hivList = HivDao.getHivDetails(memberObject.getBaseEntityId());
        if (hivList.size() > 0) {
            for (HivAlertObject hiv : hivList) {
                hiv_date = hiv.getHivStartDate();
                rule = getHivRules();
            }
        }
        Date lastVisitDate = null;
        Visit lastVisit;
        Date tbDate = FpUtil.parseFpStartDate(hiv_date);
        lastVisit = HivDao.getLatestVisit(memberObject.getBaseEntityId(), Constants.EventType.FOLLOW_UP_VISIT);
        if (lastVisit != null) {
            lastVisitDate = lastVisit.getDate();
        }

        HivAlertRule alertRule = HomeVisitUtil.getHivVisitStatus(rule, lastVisitDate, tbDate);
        serviceDueDate = alertRule.getDueDate();
        serviceOverDueDate = alertRule.getOverDueDate();
        serviceName = context.getString(R.string.hiv_follow_up_visit);

        BaseUpcomingService upcomingService = new BaseUpcomingService();
        if (serviceName != null) {
            upcomingService.setServiceDate(serviceDueDate);
            upcomingService.setOverDueDate(serviceOverDueDate);
            upcomingService.setServiceName(serviceName);
            serviceList.add(upcomingService);
        }

    }

    public static Rules getHivRules() {
        return CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.HIV_FOLLOW_UP_VISIT);
    }

}
