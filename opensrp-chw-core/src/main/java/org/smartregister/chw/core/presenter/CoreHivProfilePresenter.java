package org.smartregister.chw.core.presenter;

import androidx.annotation.Nullable;

import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.chw.core.contract.CoreFamilyPlanningMemberProfileContract;
import org.smartregister.chw.core.contract.CoreHivProfileContract;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.hiv.contract.BaseHivProfileContract;
import org.smartregister.chw.hiv.domain.HivMemberObject;
import org.smartregister.chw.hiv.presenter.BaseHivProfilePresenter;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.family.contract.FamilyProfileContract;
import org.smartregister.family.domain.FamilyEventClient;
import org.smartregister.family.util.Utils;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.util.FormUtils;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public class CoreHivProfilePresenter extends BaseHivProfilePresenter implements CoreHivProfileContract.Presenter, FamilyProfileContract.InteractorCallBack {
    private BaseHivProfileContract.Interactor interactor;
    private WeakReference<BaseHivProfileContract.View> view;
    private FormUtils formUtils;
    private HivMemberObject hivMemberObject;

    public CoreHivProfilePresenter(BaseHivProfileContract.View view, BaseHivProfileContract.Interactor interactor, HivMemberObject hivMemberObject) {
        super(view, interactor, hivMemberObject);
        this.interactor = interactor;
        this.view = new WeakReference<>(view);
        this.hivMemberObject = hivMemberObject;
    }

    @Override
    public void createReferralEvent(AllSharedPreferences allSharedPreferences, String jsonString) throws Exception {
        ((CoreHivProfileContract.Interactor) interactor).createReferralEvent(allSharedPreferences, jsonString, hivMemberObject.getBaseEntityId());
    }

    @Override
    public void startHivReferral() {
        try {
            getView().startFormActivity(getFormUtils().getFormJson(CoreConstants.JSON_FORM.getHivReferralForm()), hivMemberObject);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    @Nullable
    public CoreHivProfileContract.View getView() {
        if (view != null) {
            return (CoreHivProfileContract.View) view.get();
        } else {
            return null;
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

    @Override
    public void startFormForEdit(CommonPersonObjectClient client) {
        // TODO
    }

    @Override
    public void refreshProfileTopSection(CommonPersonObjectClient client) {
        // TODO
    }

    @Override
    public void onUniqueIdFetched(Triple<String, String, String> triple, String entityId) {
        // TODO
    }

    @Override
    public void onNoUniqueId() {
        // TODO
    }

    @Override
    public void onRegistrationSaved(boolean editMode, boolean isSaved, FamilyEventClient familyEventClient) {
        if (isSaved) {
            refreshProfileData();
            Timber.d("On member profile registration saved");
        }
    }
}
