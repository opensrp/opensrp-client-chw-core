package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.malaria.contract.MalariaRegisterFragmentContract;
import org.smartregister.chw.malaria.presenter.BaseMalariaRegisterFragmentPresenter;
import org.smartregister.chw.malaria.util.DBConstants;

public abstract class CoreMalariaRegisterFragmentPresenter extends BaseMalariaRegisterFragmentPresenter {

    public CoreMalariaRegisterFragmentPresenter(MalariaRegisterFragmentContract.View view,
                                                MalariaRegisterFragmentContract.Model model, String viewConfigurationIdentifier) {
        super(view, model, viewConfigurationIdentifier);
    }

    @Override
    public String getMainCondition() {
        return " " + CoreConstants.TABLE_NAME.FAMILY_MEMBER + "." + org.smartregister.chw.malaria.util.DBConstants.KEY.DATE_REMOVED + " is null " +
                "AND " + CoreConstants.TABLE_NAME.MALARIA_CONFIRMATION + "." + DBConstants.KEY.MALARIA + " = 1 AND "
                + CoreConstants.TABLE_NAME.MALARIA_CONFIRMATION + "." + DBConstants.KEY.IS_CLOSED + " = 0";
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
