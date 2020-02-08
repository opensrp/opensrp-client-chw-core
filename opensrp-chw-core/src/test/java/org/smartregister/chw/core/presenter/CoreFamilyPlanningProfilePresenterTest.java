package org.smartregister.chw.core.presenter;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;
import org.smartregister.chw.core.contract.CoreFamilyPlanningMemberProfileContract;
import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.util.FormUtils;

public class CoreFamilyPlanningProfilePresenterTest {
    @Mock
    private CoreFamilyPlanningMemberProfileContract.View view;


    private CoreFamilyPlanningProfilePresenter profilePresenter;

    @Mock
    private BaseFpProfileContract.Interactor interactor;

    @Mock
    private JSONObject jsonForm;

    @Mock
    private FpMemberObject fpMemberObject;

    @Mock
    private FormUtils formUtils;

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

    @Test
    public void startFamilyPlanningReferralStartsReferralForm() {
        Whitebox.setInternalState(profilePresenter, "formUtils", formUtils);
        profilePresenter.startFamilyPlanningReferral();
        Mockito.verify(profilePresenter.getView()).startFormActivity(Mockito.any(), Mockito.any());
    }

}
