package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.contract.CoreCertificationRegisterContract;
import org.smartregister.chw.core.interactor.CoreCertificationRegisterInteractor;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;

import java.lang.ref.WeakReference;
import java.util.List;

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
    public void saveForm(String jsonString, boolean isEditMode) {
        // Todo
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
    public void updateInitials() {

    }
}
