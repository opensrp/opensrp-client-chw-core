package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.fp.contract.BaseFpRegisterFragmentContract;
import org.smartregister.chw.fp.presenter.BaseFpRegisterFragmentPresenter;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.chw.malaria.util.DBConstants;

public abstract class CoreFpRegisterFragmentPresenter extends BaseFpRegisterFragmentPresenter {

    public CoreFpRegisterFragmentPresenter(BaseFpRegisterFragmentContract.View view,
                                           BaseFpRegisterFragmentContract.Model model, String viewConfigurationIdentifier) {
        super(view, model, viewConfigurationIdentifier);
    }

    @Override
    public String getMainCondition() {
        return " " + CoreConstants.TABLE_NAME.FAMILY_MEMBER + "." + DBConstants.KEY.DATE_REMOVED + " is null " + " AND "
                + FamilyPlanningConstants.DBConstants.FAMILY_PLANNING_TABLE + "." + DBConstants.KEY.IS_CLOSED + " = 0";
    }

    @Override
    public void processViewConfigurations() {
        super.processViewConfigurations();
        if (config.getSearchBarText() != null && getView() != null) {
            getView().updateSearchBarHint(getView().getContext().getString(R.string.search_name_or_id));
        }
    }

    @Override
    public String getMainTable() {
        return CoreConstants.TABLE_NAME.MALARIA_CONFIRMATION;
    }
}
