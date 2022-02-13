package org.smartregister.chw.core.interactor;

import org.smartregister.chw.anc.util.JsonFormUtils;
import org.smartregister.chw.anc.util.NCUtils;
import org.smartregister.chw.core.contract.CoreCertificationRegisterContract;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.family.util.AppExecutors;
import org.smartregister.family.util.Utils;
import org.smartregister.repository.AllSharedPreferences;

import timber.log.Timber;

public class CoreCertificationRegisterInteractor implements CoreCertificationRegisterContract.Interactor {

    private final AppExecutors appExecutors;

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
    public void saveRegistration(final String jsonString, String table, CoreCertificationRegisterContract.InteractorCallBack callback) {
        Runnable runnable = () -> {
            try {
                AllSharedPreferences allSharedPreferences = Utils.context().allSharedPreferences();
                Event baseEvent = JsonFormUtils.processJsonForm(allSharedPreferences, jsonString, table);

                NCUtils.addEvent(allSharedPreferences, baseEvent);
                NCUtils.startClientProcessing();

            } catch (Exception ex) {
                Timber.e(ex);
            }

            appExecutors.mainThread().execute(callback::onRegistrationSaved);
        };
        appExecutors.diskIO().execute(runnable);

    }
}
