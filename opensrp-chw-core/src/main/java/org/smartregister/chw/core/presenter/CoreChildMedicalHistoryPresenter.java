package org.smartregister.chw.core.presenter;

import org.smartregister.chw.anc.presenter.BaseAncMedicalHistoryPresenter;
import org.smartregister.chw.core.contract.CoreChildMedicalHistoryContract;
import org.smartregister.immunization.domain.ServiceRecord;
import org.smartregister.immunization.domain.ServiceType;
import org.smartregister.immunization.domain.Vaccine;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class CoreChildMedicalHistoryPresenter extends BaseAncMedicalHistoryPresenter implements CoreChildMedicalHistoryContract.InteractorCallBack {

    private CoreChildMedicalHistoryContract.Interactor interactor;
    private WeakReference<CoreChildMedicalHistoryContract.View> view;
    private String memberID;


    public CoreChildMedicalHistoryPresenter(CoreChildMedicalHistoryContract.Interactor interactor, CoreChildMedicalHistoryContract.View view, String memberID) {
        super(interactor, view, memberID);
        this.interactor = interactor;
        this.view = new WeakReference<>(view);
        this.memberID = memberID;

        initializeData();
    }

    private void initializeData() {
        interactor.getRecurringServicesReceived(memberID, getView().getViewContext(), this);
        interactor.getVaccinesReceived(memberID, getView().getViewContext(), this);
    }

    @Override
    public CoreChildMedicalHistoryContract.View getView() {
        if (view.get() != null) {
            return view.get();
        } else {
            return null;
        }
    }

    @Override
    public void onVaccineDataFetched(Map<String, List<Vaccine>> vaccines) {
        if (getView() != null)
            getView().onVaccineDataReceived(vaccines);
    }

    @Override
    public void onServicesDataFetched(List<ServiceRecord> serviceRecords, Map<String, ServiceType> serviceTypeMap) {
        if (getView() != null)
            getView().onServicesDataReceived(serviceRecords, serviceTypeMap);
    }
}
