package org.smartregister.chw.core.interactor;

import org.smartregister.chw.anc.AncLibrary;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.chw.fp.interactor.BaseFpProfileInteractor;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.domain.AlertStatus;

import java.util.Date;

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

    private Date getLastVisitDate(FpMemberObject memberObject) {
        Date lastVisitDate = null;
        Visit lastVisit = AncLibrary.getInstance().visitRepository().getLatestVisit(memberObject.getBaseEntityId(), FamilyPlanningConstants.EventType.FP_HOME_VISIT);
        if (lastVisit != null) {
            lastVisitDate = lastVisit.getDate();
        }

        return lastVisitDate;
    }

}