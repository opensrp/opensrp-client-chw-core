package org.smartregister.chw.hf.presenter;

import org.smartregister.chw.core.contract.BaseChwNotificationFragmentContract;
import org.smartregister.chw.core.presenter.BaseChwNotificationFragmentPresenter;
import org.smartregister.chw.hf.model.UpdatesRegisterModel;

public class UpdatesFragmentPresenter extends BaseChwNotificationFragmentPresenter {

    public UpdatesFragmentPresenter(BaseChwNotificationFragmentContract.View view) {
        super(view, new UpdatesRegisterModel());
    }

    @Override
    public void displayDetailsActivity(String referralTaskId, String notificationType) {
        //Implementation to navigation details page not needed
    }
}
