package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.contract.CoreCertificationRegisterFragmentContract;

public class CoreCertificationRegisterFragmentPresenter extends CoreChildRegisterFragmentPresenter {

    public CoreCertificationRegisterFragmentPresenter(CoreCertificationRegisterFragmentContract.View view,
                                                      CoreCertificationRegisterFragmentContract.Model model, String viewConfigurationIdentifier) {
        super(view, model, viewConfigurationIdentifier);
    }

}
