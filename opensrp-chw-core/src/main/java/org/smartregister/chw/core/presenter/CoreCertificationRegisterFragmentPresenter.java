package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.contract.CoreCertificationRegisterFragmentContract;

public class CoreCertificationRegisterFragmentPresenter extends CoreChildRegisterFragmentPresenter implements CoreCertificationRegisterFragmentContract.Presenter {

    public CoreCertificationRegisterFragmentPresenter(CoreCertificationRegisterFragmentContract.View view,
                                                      CoreCertificationRegisterFragmentContract.Model model, String viewConfigurationIdentifier) {
        super(view, model, viewConfigurationIdentifier);
    }

    @Override
    public  String getOutOfCatchmentFilterString(String filters) {
        return "";
    }

    @Override
    public String getCountSelectString(String condition) {
        return "";
    }

    @Override
    public String getMainSelectString(String condition) {
        return "";
    }

    @Override
    public String getOutOfCatchmentSelectString(String condition) {
        return "";
    }

    @Override
    public String getOutOfCatchmentMainCondition() {
        return "";
    }

    @Override
    public String getOutOfCatchmentSortQueries() {
        return "";
    }
}
