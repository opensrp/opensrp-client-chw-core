package org.smartregister.chw.core.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.contract.CoreFamilyPlanningMemberProfileContract;
import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.FpMemberObject;

public class CoreFamilyPlanningProfilePresenterTest {
    @Mock
    private CoreFamilyPlanningMemberProfileContract.View view;


    private CoreFamilyPlanningProfilePresenter profilePresenter;

    @Mock
    private BaseFpProfileContract.Interactor interactor;


    @Mock
    private FpMemberObject fpMemberObject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        profilePresenter = new CoreFamilyPlanningProfilePresenter(view, interactor, fpMemberObject);
    }

    @Test
    public void testGetView() {
        CoreFamilyPlanningMemberProfileContract.View myView = profilePresenter.getView();
        Assert.assertEquals(view, myView);
    }


}
