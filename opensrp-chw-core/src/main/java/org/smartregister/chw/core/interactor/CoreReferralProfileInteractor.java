package org.smartregister.chw.core.interactor;

import org.joda.time.LocalDate;
import org.smartregister.chw.core.dao.AlertDao;
import org.smartregister.chw.core.dao.VisitDao;
import org.smartregister.chw.referral.contract.BaseReferralProfileContract;
import org.smartregister.chw.referral.domain.MemberObject;
import org.smartregister.chw.referral.interactor.BaseReferralProfileInteractor;
import org.smartregister.domain.Alert;
import org.smartregister.domain.AlertStatus;

import java.util.List;

public class CoreReferralProfileInteractor extends BaseReferralProfileInteractor {

    @Override
    public void refreshProfileInfo(MemberObject memberObject, BaseReferralProfileContract.InteractorCallBack callback) {
        Runnable runnable = () -> appExecutors.mainThread().execute(() -> {
            callback.refreshFamilyStatus(AlertStatus.normal);
            Alert alert = getLatestAlert(memberObject.getBaseEntityId());

            callback.refreshMedicalHistory(VisitDao.memberHasVisits(memberObject.getBaseEntityId()));
            if (alert != null)
                callback.refreshUpComingServicesStatus(alert.scheduleName(), alert.status(), new LocalDate(alert.startDate()).toDate());

        });
        appExecutors.diskIO().execute(runnable);
    }



    private Alert getLatestAlert(String baseEntityID) {
        List<Alert> alerts = AlertDao.getActiveAlertsForVaccines(baseEntityID);

        if (alerts.size() > 0)
            return alerts.get(0);

        return null;
    }

}
