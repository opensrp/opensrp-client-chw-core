package org.smartregister.chw.hf.presenter;

import org.smartregister.chw.core.contract.CoreChildRegisterFragmentContract;
import org.smartregister.chw.core.presenter.CoreChildRegisterFragmentPresenter;
import org.smartregister.chw.core.utils.CoreConstants;

public class ChildRegisterFragmentPresenter extends CoreChildRegisterFragmentPresenter {

    public ChildRegisterFragmentPresenter(CoreChildRegisterFragmentContract.View view, CoreChildRegisterFragmentContract.Model model, String viewConfigurationIdentifier) {
        super(view, model, viewConfigurationIdentifier);
    }

    @Override
    public String getDueCondition() {
        return " AND " + CoreConstants.TABLE_NAME.CHILD + ".base_entity_id in (SELECT distinct (task.for)\n" +
                "FROM task\n" +
                "         INNER JOIN ec_child ON ec_child.base_entity_id = task.for\n" +
                "WHERE task.business_status = 'Referred'\n" +
                "  AND task.status = 'READY'\n" +
                "  AND task.focus = 'Sick Child'\n" +
                "  AND ec_child.date_removed is  null)";
    }
}
