package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.contract.CorePmtctProfileContract;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.pmtct.domain.MemberObject;
import org.smartregister.chw.pmtct.presenter.BasePmtctProfilePresenter;
import org.smartregister.family.util.Utils;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.util.FormUtils;

import timber.log.Timber;

public class CorePmtctMemberProfilePresenter extends BasePmtctProfilePresenter implements CorePmtctProfileContract.Presenter {
    private FormUtils formUtils;
    private CorePmtctProfileContract.Interactor interactor;

    public CorePmtctMemberProfilePresenter(CorePmtctProfileContract.View view, CorePmtctProfileContract.Interactor interactor, MemberObject memberObject) {
        super(view, interactor, memberObject);
        this.interactor = interactor;
    }

    @Override
    public CorePmtctProfileContract.View getView() {
        if (view != null) {
            return (CorePmtctProfileContract.View) view.get();
        }
        return null;
    }

    @Override
    public void startHfPmtctFollowupForm() {
        try {
            getView().startFormActivity(getFormUtils().getFormJson(CoreConstants.JSON_FORM.getPmtctFollowupForm()));
        } catch (Exception ex) {
            Timber.e(ex);
        }
    }

    @Override
    public void createHfPmtctFollowupEvent(AllSharedPreferences allSharedPreferences, String jsonString, String entityID) throws Exception {
        interactor.createHfPmtctFollowupEvent(allSharedPreferences, jsonString, entityID);
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
