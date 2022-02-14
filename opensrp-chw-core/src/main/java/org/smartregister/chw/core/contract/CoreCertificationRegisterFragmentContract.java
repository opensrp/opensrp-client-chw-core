package org.smartregister.chw.core.contract;

public interface CoreCertificationRegisterFragmentContract extends CoreChildRegisterFragmentContract {

    interface Presenter extends CoreChildRegisterFragmentContract.Presenter {

        String getOutOfCatchmentFilterString(String filters);

        String getCountSelectString(String condition);

        String getMainSelectString(String condition);

        String getOutOfCatchmentSelectString(String condition);

        String getOutOfCatchmentMainCondition();

        String getOutOfCatchmentSortQueries();

    }
}
