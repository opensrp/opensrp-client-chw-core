package org.smartregister.chw.core.presenter;

import androidx.annotation.Nullable;

import com.vijay.jsonwizard.utils.FormUtils;

import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.CoreHivProfileActivity;
import org.smartregister.chw.core.contract.CoreHivProfileContract;
import org.smartregister.chw.core.contract.CoreIndexContactProfileContract;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.hiv.contract.BaseIndexContactProfileContract;
import org.smartregister.chw.hiv.domain.HivIndexContactObject;
import org.smartregister.chw.hiv.presenter.BaseIndexContactProfilePresenter;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.family.contract.FamilyProfileContract;
import org.smartregister.family.domain.FamilyEventClient;
import org.smartregister.repository.AllSharedPreferences;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public class CoreHivIndexContactProfilePresenter extends BaseIndexContactProfilePresenter implements CoreIndexContactProfileContract.Presenter, FamilyProfileContract.InteractorCallBack {
    private BaseIndexContactProfileContract.Interactor interactor;
    private WeakReference<BaseIndexContactProfileContract.View> view;
    private HivIndexContactObject hivIndexContactObject;

    public CoreHivIndexContactProfilePresenter(BaseIndexContactProfileContract.View view, BaseIndexContactProfileContract.Interactor interactor, HivIndexContactObject hivIndexContactObject) {
        super(view, interactor, hivIndexContactObject);
        this.interactor = interactor;
        this.view = new WeakReference<>(view);
        this.hivIndexContactObject = hivIndexContactObject;
    }

    @Override
    public void createReferralEvent(AllSharedPreferences allSharedPreferences, String jsonString) throws Exception {
        ((CoreHivProfileContract.Interactor) interactor).createReferralEvent(allSharedPreferences, jsonString, hivIndexContactObject.getBaseEntityId());
    }

    @Override
    public void startHivReferral() {
        try {
            getView().startFormActivity((new FormUtils()).getFormJsonFromRepositoryOrAssets(((CoreHivProfileActivity) getView()), CoreConstants.JSON_FORM.getHivReferralForm()), hivIndexContactObject, ((CoreHivProfileActivity) getView()).getString(R.string.hiv_referral));
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    @Nullable
    public CoreIndexContactProfileContract.View getView() {
        if (view != null) {
            return (CoreIndexContactProfileContract.View) view.get();
        } else {
            return null;
        }
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
