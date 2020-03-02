package org.smartregister.chw.core.fragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.contract.FamilyCallDialogContract;

import static org.mockito.ArgumentMatchers.any;

public class FamilyCallDialogFragmentTest extends BaseUnitTest {
    @Mock
    private FamilyCallDialogFragment familyCallDialogFragment;

    @Mock
    private FamilyCallDialogContract.Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenRefreshHeadOfFamilyViewAnswered() {
        Mockito.when(model.getPhoneNumber()).thenReturn("0777777777");
        Mockito.when(model.getName()).thenReturn("Family head Name");
        Mockito.when(model.getRole()).thenReturn("Family Head");
        Assert.assertNotNull(model);
        Mockito.doAnswer(invocation -> {
            FamilyCallDialogContract.Model answeredModel = invocation.getArgument(0);
            Assert.assertNotNull(answeredModel);
            Assert.assertEquals("0777777777", answeredModel.getPhoneNumber());
            Assert.assertEquals("Family head Name", answeredModel.getName());
            Assert.assertEquals("Family Head", answeredModel.getRole());
            return null;
        }).when(familyCallDialogFragment).refreshHeadOfFamilyView(any(FamilyCallDialogContract.Model.class));
        familyCallDialogFragment.refreshHeadOfFamilyView(model);
    }

    @Test
    public void whenRefreshCareGiverViewAnswered() {
        Mockito.when(model.getPhoneNumber()).thenReturn("0777777777");
        Mockito.when(model.getName()).thenReturn("Care giver Name");
        Mockito.when(model.getRole()).thenReturn("Care Giver");
        Assert.assertNotNull(model);
        Mockito.doAnswer(invocation -> {
            FamilyCallDialogContract.Model answeredModel = invocation.getArgument(0);
            Assert.assertNotNull(answeredModel);
            Assert.assertEquals("0777777777", answeredModel.getPhoneNumber());
            Assert.assertEquals("Care giver Name", answeredModel.getName());
            Assert.assertEquals("Care Giver", answeredModel.getRole());
            return null;
        }).when(familyCallDialogFragment).refreshCareGiverView(any(FamilyCallDialogContract.Model.class));
        familyCallDialogFragment.refreshCareGiverView(model);
    }


}
