package org.smartregister.chw.core.presenter;

import org.json.JSONObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.CoreCertificationRegisterContract;
import org.smartregister.chw.core.interactor.CoreCertificationRegisterInteractor;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class CoreCertificationRegisterPresenter implements CoreCertificationRegisterContract.Presenter,
        CoreCertificationRegisterContract.InteractorCallBack {

    private CoreCertificationRegisterContract.Interactor interactor;
    private WeakReference<CoreCertificationRegisterContract.View> viewWeakReference;
    private CoreCertificationRegisterContract.Model model;

    public CoreCertificationRegisterPresenter(CoreCertificationRegisterContract.View view, CoreCertificationRegisterContract.Model model) {
        this.viewWeakReference = new WeakReference<>(view);
        this.model = model;
        interactor = new CoreCertificationRegisterInteractor();
    }

    @Override
    public CoreCertificationRegisterContract.View getView() {
        if (viewWeakReference != null) {
            return viewWeakReference.get();
        } else {
            return null;
        }
    }

    @Override
    public void saveForm(String jsonString, String table) {
        try {
            if (getView() != null)
                getView().showProgressDialog(R.string.saving_dialog_title);

            interactor.saveRegistration(jsonString, table, this);
        } catch (Exception ex) {
            Timber.e(ex);
        }

    }

    @Override
    public void registerViewConfigurations(List<String> viewIdentifiers) {
        if (viewIdentifiers != null)
            ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().registerViewConfigurations(viewIdentifiers);
    }

    @Override
    public void unregisterViewConfiguration(List<String> viewIdentifiers) {
        if (viewIdentifiers != null)
            ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().unregisterViewConfiguration(viewIdentifiers);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        viewWeakReference = null;
        interactor.onDestroy(isChangingConfiguration);
        // Activity destroyed set interactor to null
        if (!isChangingConfiguration) {
            interactor = null;
            model = null;
        }
    }

    @Override
    public void startEditCertForm(String formName, String updateEventType, String entityId, Map<String, String> valueMap) throws Exception {
        JSONObject formJsonObject = model.getEditFormAsJson(getView().getContext(), formName, updateEventType, entityId, valueMap);
        getView().startFormActivity(formJsonObject);
    }

    public void startCertificationForm(String formName, String entityId) throws Exception {
        JSONObject formJsonObject = model.getFormAsJson(getView().getContext(), formName, entityId);
        getView().startFormActivity(formJsonObject);
    }



    @Override
    public void updateInitials() {
        // Empty block
    }

    @Override
    public void onRegistrationSaved() {
        if (getView() != null) {
            getView().hideProgressDialog();
            getView().onRegistrationSaved();
        }
    }
}
