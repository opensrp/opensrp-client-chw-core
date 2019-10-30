package org.smartregister.chw.core.contract;

import android.content.Context;

import org.smartregister.chw.anc.contract.BaseAncMedicalHistoryContract;
import org.smartregister.immunization.domain.ServiceRecord;
import org.smartregister.immunization.domain.ServiceType;
import org.smartregister.immunization.domain.Vaccine;

import java.util.List;
import java.util.Map;

public interface CoreChildMedicalHistoryContract extends BaseAncMedicalHistoryContract {

    interface Interactor extends BaseAncMedicalHistoryContract.Interactor {

        void getRecurringServicesReceived(String memberID, Context context, InteractorCallBack callBack);

        void getVaccinesReceived(String memberID, Context context, InteractorCallBack callBack);
    }

    interface InteractorCallBack extends BaseAncMedicalHistoryContract.InteractorCallBack {

        void onVaccineDataFetched(Map<String, List<Vaccine>> vaccines);

        void onServicesDataFetched(List<ServiceRecord> serviceRecords, Map<String, ServiceType> serviceTypeMap);

    }

    interface View extends BaseAncMedicalHistoryContract.View {

        void onVaccineDataReceived(Map<String, List<Vaccine>> vaccines);

        void onServicesDataReceived(List<ServiceRecord> serviceRecords, Map<String, ServiceType> serviceTypeMap);

    }
}
