package org.smartregister.chw.core.interactor;

import org.smartregister.chw.core.contract.CoreCertificationRegisterContract;
import org.smartregister.family.util.AppExecutors;

public class CoreCertificationRegisterInteractor implements CoreCertificationRegisterContract.Interactor {

    private AppExecutors appExecutors;

    public CoreCertificationRegisterInteractor() {
        this(new AppExecutors());
    }

    public CoreCertificationRegisterInteractor(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        //TODO -> Set presenter or model to NULL
    }

    @Override
    public void saveRegistration() {

    }
}
