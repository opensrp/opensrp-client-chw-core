package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.BaseNotificationFragmentContract;
import org.smartregister.chw.core.model.BaseNotificationModel;

import java.lang.ref.WeakReference;

public abstract class BaseNotificationFragmentPresenter implements
        BaseNotificationFragmentContract.Presenter {

    private WeakReference<BaseNotificationFragmentContract.View> viewReference;
    protected BaseNotificationModel model;

    public BaseNotificationFragmentPresenter(BaseNotificationFragmentContract.View view,
                                             BaseNotificationModel model) {
        this.viewReference = new WeakReference<>(view);
        this.model = model;
    }

    @Override
    public void processViewConfigurations() {
        if (getView() != null) {
            getView().updateSearchBarHint(getView().getContext().getString(R.string.search_name_or_id));
        }
    }

    @Override
    public void initializeQueries(String mainCondition) {
        getView().initializeQueryParams("ec_family_member", null, null);
        getView().initializeAdapter();
        getView().countExecute();
        getView().filterandSortInInitializeQueries();
    }

    protected BaseNotificationFragmentContract.View getView() {
        if (viewReference != null) {
            return viewReference.get();
        } else {
            return null;
        }
    }

    @Override
    public void startSync() {
        //Overridden not required
    }

    @Override
    public void searchGlobally(String s) {
        //Overridden not required
    }

    @Override
    abstract public void displayDetailsActivity(String referralTaskId, String notificationType);
}
