package org.smartregister.chw.core.activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.util.JsonFormUtils;

public class CoreFamilyProfileActivityTest extends BaseUnitTest {

    private CoreFamilyProfileActivity controller;
    private String baseID = JsonFormUtils.generateRandomUUIDString();

    private CommonPersonObject commonPersonObject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = Mockito.mock(CoreFamilyProfileActivity.class, Mockito.CALLS_REAL_METHODS);
        commonPersonObject = Mockito.mock(CommonPersonObject.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void updateHasPhoneAnswered() {
        Mockito.doNothing().when(controller).updateHasPhone(true);
        controller.updateHasPhone(true);
        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(boolean.class);
        Mockito.verify(controller, Mockito.times(1)).updateHasPhone(captor.capture());
        Assert.assertEquals(captor.getValue(), true);
    }

    @Test
    public void whenGetFamilyBaseEntityIdAnswered() {
        Mockito.when(controller.getFamilyBaseEntityId())
                .thenReturn(baseID);
        Assert.assertEquals(baseID, controller.getFamilyBaseEntityId());
    }

    @Test
    public void whenGetAncCommonPersonObjectAnswered() {
        Mockito.when(controller.getAncCommonPersonObject(baseID))
                .thenReturn(commonPersonObject);
        Assert.assertEquals(commonPersonObject, controller.getAncCommonPersonObject(baseID));
    }


}
