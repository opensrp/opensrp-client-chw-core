package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.contract.FamilyPlanningMemberProfileContract;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.chw.fp.presenter.BaseFpProfilePresenter;
import org.smartregister.family.util.Utils;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.util.FormUtils;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public class CoreFamilyPlanningProfilePresenter extends BaseFpProfilePresenter implements FamilyPlanningMemberProfileContract.Presenter {
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
        ((FamilyPlanningMemberProfileContract.Interactor) interactor).createReferralEvent(allSharedPreferences, jsonString, fpMemberObject.getBaseEntityId());
    }

    @Override
    public FamilyPlanningMemberProfileContract.View getView() {
        if (view != null) {
            return (FamilyPlanningMemberProfileContract.View) view.get();
        } else {
            return null;
        }
    }

    @Override
    public void startFamilyPlanningReferral() {
        try {
            getView().startFormActivity(getFormUtils().getFormJson(CoreConstants.JSON_FORM.getAncReferralForm()), fpMemberObject);
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
