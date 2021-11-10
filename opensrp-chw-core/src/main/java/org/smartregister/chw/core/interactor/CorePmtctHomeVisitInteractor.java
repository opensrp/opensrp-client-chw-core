package org.smartregister.chw.core.interactor;

import org.smartregister.chw.anc.util.VisitUtils;
import org.smartregister.chw.pmtct.contract.BasePmtctHomeVisitContract;
import org.smartregister.chw.pmtct.dao.PmtctDao;
import org.smartregister.chw.pmtct.domain.MemberObject;
import org.smartregister.chw.pmtct.interactor.BasePmtctHomeVisitInteractor;
import org.smartregister.chw.pmtct.model.BasePmtctHomeVisitAction;

import java.util.LinkedHashMap;
import java.util.Map;

import timber.log.Timber;

public class CorePmtctHomeVisitInteractor extends BasePmtctHomeVisitInteractor {

    private Flavor flavor;

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    @Override
    public void calculateActions(BasePmtctHomeVisitContract.View view, MemberObject memberObject, BasePmtctHomeVisitContract.InteractorCallBack callBack) {
        try {
            VisitUtils.processVisits(memberObject.getBaseEntityId());
        } catch (Exception e) {
            Timber.e(e);
        }

        final Runnable runnable = () -> {
            final LinkedHashMap<String, BasePmtctHomeVisitAction> actionList = new LinkedHashMap<>();

            try {
                for (Map.Entry<String, BasePmtctHomeVisitAction> entry : flavor.calculateActions(view, memberObject, callBack).entrySet()) {
                    actionList.put(entry.getKey(), entry.getValue());
                }
            } catch (BasePmtctHomeVisitAction.ValidationException e) {
                Timber.e(e);
            }

            appExecutors.mainThread().execute(() -> callBack.preloadActions(actionList));
        };

        appExecutors.diskIO().execute(runnable);
    }


    @Override
    public MemberObject getMemberClient(String memberID) {
        // read all the member details from the database
        return PmtctDao.getMember(memberID);
    }

    public interface Flavor {

        LinkedHashMap<String, BasePmtctHomeVisitAction> calculateActions(final BasePmtctHomeVisitContract.View view, MemberObject memberObject, final BasePmtctHomeVisitContract.InteractorCallBack callBack) throws BasePmtctHomeVisitAction.ValidationException;

    }
}
