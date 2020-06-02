package org.smartregister.chw.hf.presenter;

import org.smartregister.chw.core.contract.BaseChwNotificationFragmentContract;
import org.smartregister.chw.core.presenter.BaseChwNotificationFragmentPresenter;
import org.smartregister.chw.hf.model.UpdatesRegisterModel;
import org.smartregister.commonregistry.CommonPersonObjectClient;

public class UpdatesFragmentPresenter extends BaseChwNotificationFragmentPresenter {

    public UpdatesFragmentPresenter(BaseChwNotificationFragmentContract.View view) {
        super(view, new UpdatesRegisterModel());
    }

    @Override
    public void displayDetailsActivity(CommonPersonObjectClient client, String notificationId, String notificationType) {
        //Implementation to navigation details page not needed
    }
}
