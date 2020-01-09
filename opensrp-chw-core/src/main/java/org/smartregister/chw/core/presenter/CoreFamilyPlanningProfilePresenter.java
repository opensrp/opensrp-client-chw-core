package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.contract.CoreFamilyPlanningMemberProfileContract;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.chw.fp.presenter.BaseFpProfilePresenter;
import org.smartregister.family.util.Utils;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.util.FormUtils;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public class CoreFamilyPlanningProfilePresenter extends BaseFpProfilePresenter implements CoreFamilyPlanningMemberProfileContract.Presenter {
    private BaseFpProfileContract.Interactor interactor;
    private WeakReference<BaseFpProfileContract.View> view;
    private FormUtils formUtils;
    private FpMemberObject fpMemberObject;

    public CoreFamilyPlanningProfilePresenter(BaseFpProfileContract.View view, BaseFpProfileContract.Interactor interactor, FpMemberObject fpMemberObject) {
        super(view, interactor, fpMemberObject);
        this.interactor = interactor;
        this.view = new WeakReference<>(view);
        this.fpMemberObject = fpMemberObject;
    }

    @Override
    public void createReferralEvent(AllSharedPreferences allSharedPreferences, String jsonString) throws Exception {
        ((CoreFamilyPlanningMemberProfileContract.Interactor) interactor).createReferralEvent(allSharedPreferences, jsonString, fpMemberObject.getBaseEntityId());
    }

    @Override
    public CoreFamilyPlanningMemberProfileContract.View getView() {
        if (view != null) {
            return (CoreFamilyPlanningMemberProfileContract.View) view.get();
        } else {
            return null;
        }
    }

    @Override
    public void startFamilyPlanningReferral() {
        try {
            getView().startFormActivity(getFormUtils().getFormJson(CoreConstants.JSON_FORM.getFamilyPlanningReferralForm()), fpMemberObject);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private FormUtils getFormUtils() {

        if (formUtils == null) {
            try {
                formUtils = FormUtils.getInstance(Utils.context().applicationContext());
            } catch (Exception e) {
                Timber.e(e);
            }
        }
        return formUtils;
    }
}
