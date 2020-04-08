package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.BaseReferralNotificationFragmentContract;
import org.smartregister.chw.core.interactor.CoreReferralNotificationInteractor;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;

import java.util.HashSet;

public abstract class BaseReferralNotificationFragmentPresenter implements
        BaseReferralNotificationFragmentContract.Presenter, BaseReferralNotificationFragmentContract.InteractorCallBack {

    private String baseEntityId;
    protected BaseReferralNotificationFragmentContract.View view;
    protected BaseReferralNotificationFragmentContract.Model model;
    protected BaseReferralNotificationFragmentContract.Interactor interactor;


    public BaseReferralNotificationFragmentPresenter(BaseReferralNotificationFragmentContract.View view,
                                                     BaseReferralNotificationFragmentContract.Model model) {
        this.view = view;
        this.model = model;
        interactor = new CoreReferralNotificationInteractor();
    }

    @Override
    public void processViewConfigurations() {
        if (view != null) {
            view.updateSearchBarHint(view.getContext().getString(R.string.search_name_or_id));
        }
    }

    @Override
    public void initializeQueries(String mainCondition) {
        String countSelect = model.countQueryStatement(CoreConstants.TABLE_NAME.TASK, mainCondition);
        String mainSelect = model.mainQueryStatement(CoreConstants.TABLE_NAME.TASK, CoreConstants.TABLE_NAME.FAMILY_MEMBER, mainCondition);
        view.initializeQueryParams(CoreConstants.TABLE_NAME.FAMILY_MEMBER, countSelect, mainSelect);
        view.initializeAdapter(new HashSet<>(), CoreConstants.TABLE_NAME.TASK);
        view.countExecute();
        view.filterandSortInInitializeQueries();
    }

    @Override
    public void startSync() {
        //Overridden not required
    }

    @Override
    public void searchGlobally(String s) {
        //Overridden not required
    }

    public String getBaseEntityId() {
        return baseEntityId;
    }

    public void setBaseEntityId(String baseEntityId) {
        this.baseEntityId = baseEntityId;
    }

    @Override
    public void clientDetails(CommonPersonObjectClient client) {
        view.setClient(client);
    }
}
