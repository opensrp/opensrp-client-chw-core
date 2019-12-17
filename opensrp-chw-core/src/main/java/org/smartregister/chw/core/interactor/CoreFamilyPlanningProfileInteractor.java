package org.smartregister.chw.core.interactor;

import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.util.VisitUtils;
import org.smartregister.chw.core.dao.AncDao;
import org.smartregister.chw.core.dao.PNCDao;
import org.smartregister.chw.core.dao.VisitDao;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.chw.fp.interactor.BaseFpProfileInteractor;
import org.smartregister.domain.AlertStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoreFamilyPlanningProfileInteractor extends BaseFpProfileInteractor {

    @Override
    public void updateProfileFpStatusInfo(FpMemberObject memberObject, BaseFpProfileContract.InteractorCallback callback) {
        Runnable runnable = new Runnable() {

            Date lastVisitDate = getLastVisitDate(memberObject);

            @Override
            public void run() {
                appExecutors.mainThread().execute(() -> {
                    callback.refreshFamilyStatus(AlertStatus.normal);
                    callback.refreshLastVisit(lastVisitDate);
                    callback.refreshUpComingServicesStatus("Family Planning Followup Visit", AlertStatus.normal, new Date());
                    callback.refreshMedicalHistory(true);
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    private String getMemberVisitType(String baseEntityId) {
        String type = null;

        if (AncDao.isANCMember(baseEntityId)) {
            type = CoreConstants.TABLE_NAME.ANC_MEMBER;
        } else if (PNCDao.isPNCMember(baseEntityId)) {
            type = CoreConstants.TABLE_NAME.PNC_MEMBER;
        }
        return type;
    }

    public Date getLastVisitDate(FpMemberObject memberObject) {
        Date lastVisitDate = null;
        List<Visit> visits = new ArrayList<>();
        String memberType = getMemberVisitType(memberObject.getBaseEntityId());

        if (memberType != null) {
            switch (memberType) {
                case CoreConstants.TABLE_NAME.PNC_MEMBER:
                    visits = VisitDao.getPNCVisitsMedicalHistory(memberObject.getBaseEntityId());
                    break;
                case CoreConstants.TABLE_NAME.ANC_MEMBER:
                    visits = getAncVisitsMedicalHistory(memberObject.getBaseEntityId());
                    break;
                default:
                    break;
            }

            if (visits.size() > 0) {
                lastVisitDate = visits.get(0).getDate();
            }
        }
        return lastVisitDate;
    }

    private List<Visit> getAncVisitsMedicalHistory(String baseEntityId) {
        List<Visit> visits = VisitUtils.getVisits(baseEntityId);
        List<Visit> allVisits = new ArrayList<>(visits);

        for (Visit visit : visits) {
            List<Visit> childVisits = VisitUtils.getChildVisits(visit.getVisitId());
            allVisits.addAll(childVisits);
        }
        return allVisits;
    }

}