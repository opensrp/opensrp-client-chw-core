package org.smartregister.chw.core.interactor;

import android.content.Context;

import org.smartregister.chw.anc.contract.BaseAncMedicalHistoryContract;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.domain.VisitDetail;
import org.smartregister.chw.anc.interactor.BaseAncMedicalHistoryInteractor;
import org.smartregister.chw.anc.util.VisitUtils;
import org.smartregister.chw.core.contract.CoreChildMedicalHistoryContract;
import org.smartregister.chw.core.dao.VisitDao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoreChildMedicalHistoryActivityInteractor extends BaseAncMedicalHistoryInteractor implements CoreChildMedicalHistoryContract.Interactor {

    @Override
    public void getMemberHistory(final String memberID, final Context context, final BaseAncMedicalHistoryContract.InteractorCallBack callBack) {
        final Runnable runnable = () -> {

            List<Visit> visits = new ArrayList<>();
            Map<String, Visit> visitMap = getVisits(memberID);
            Map<String, List<VisitDetail>> detailList = VisitDao.getMedicalHistory(memberID);
            for (Map.Entry<String, List<VisitDetail>> entry : detailList.entrySet()) {

                Visit v = visitMap.get(entry.getKey());
                if (v == null) {
                    v = new Visit();
                    v.setVisitId(entry.getKey());
                }
                v.setVisitDetails(VisitUtils.getVisitGroups(entry.getValue()));
                visits.add(v);
            }

            appExecutors.mainThread().execute(() -> callBack.onDataFetched(visits));
        };

        appExecutors.diskIO().execute(runnable);
    }

    private Map<String, Visit> getVisits(String memberID) {
        List<Visit> visits = VisitDao.getVisitsByMemberID(memberID);
        Map<String, Visit> map = new LinkedHashMap<>();
        if (visits == null) return map;

        for (Visit v : visits) {
            map.put(v.getVisitId(), v);
        }
        return map;
    }

    @Override
    public void getRecurringServicesReceived(String memberID, Context context, CoreChildMedicalHistoryContract.InteractorCallBack callBack) {

    }

    @Override
    public void getVaccinesReceived(String memberID, Context context, CoreChildMedicalHistoryContract.InteractorCallBack callBack) {

    }
}
