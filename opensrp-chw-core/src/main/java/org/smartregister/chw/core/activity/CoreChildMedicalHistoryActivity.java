package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import org.smartregister.chw.anc.activity.BaseAncMedicalHistoryActivity;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.util.Constants;
import org.smartregister.chw.core.contract.CoreChildMedicalHistoryContract;
import org.smartregister.chw.core.interactor.CoreChildMedicalHistoryActivityInteractor;
import org.smartregister.chw.core.presenter.CoreChildMedicalHistoryPresenter;
import org.smartregister.immunization.domain.ServiceRecord;
import org.smartregister.immunization.domain.ServiceType;
import org.smartregister.immunization.domain.Vaccine;

import java.util.List;
import java.util.Map;

public class CoreChildMedicalHistoryActivity extends BaseAncMedicalHistoryActivity implements CoreChildMedicalHistoryContract.View {

    public static void startMe(Activity activity, MemberObject memberObject) {
        Intent intent = new Intent(activity, CoreChildMedicalHistoryActivity.class);
        intent.putExtra(Constants.ANC_MEMBER_OBJECTS.MEMBER_PROFILE_OBJECT, memberObject);
        activity.startActivity(intent);
    }

    @Override
    public void initializePresenter() {
        presenter = new CoreChildMedicalHistoryPresenter(new CoreChildMedicalHistoryActivityInteractor(), this, memberObject.getBaseEntityId());
    }

    @Override
    public void onDataReceived(List<Visit> visits, Map<String, List<Vaccine>> vaccines, Map<ServiceType, List<ServiceRecord>> serviceRecords) {
        View view = renderView(visits);
        linearLayout.addView(view, 0);
    }

    @Override
    public View renderView(List<Visit> visits, Map<String, List<Vaccine>> vaccines, Map<ServiceType, List<ServiceRecord>> serviceRecords) {
        LayoutInflater inflater = getLayoutInflater();
        return inflater.inflate(org.smartregister.chw.opensrp_chw_anc.R.layout.medical_history_details, null);
    }

    public interface Flavor {
        View bindViews(Activity activity);

        void processViewData(
                List<Visit> visits,
                Map<String, List<Vaccine>> vaccines,
                Map<ServiceType, List<ServiceRecord>> serviceRecords,
                Context context
        );
    }

}
