package org.smartregister.chw.core.activity;

import android.os.Bundle;
import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.util.JsonFormUtils;

public class CoreFamilyProfileActivityTest extends BaseUnitTest {

    private CoreFamilyProfileActivity controller;
    private String baseID = JsonFormUtils.generateRandomUUIDString();
    private CommonPersonObject commonPersonObject;
    private CommonPersonObjectClient commonPersonObjectClient;

    @Mock
    private View view;
    @Mock
    private Bundle fragmentArguments;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = Mockito.mock(CoreFamilyProfileActivity.class, Mockito.CALLS_REAL_METHODS);
        commonPersonObject = Mockito.mock(CommonPersonObject.class, Mockito.CALLS_REAL_METHODS);
        commonPersonObjectClient = Mockito.mock(CommonPersonObjectClient.class, Mockito.CALLS_REAL_METHODS);
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
    public void goToProfileActivityAnswered() {
        Mockito.doNothing().when(controller).goToProfileActivity(view, fragmentArguments);
        controller.goToProfileActivity(view, fragmentArguments);
        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        ArgumentCaptor<Bundle> bundleArgumentCaptor = ArgumentCaptor.forClass(Bundle.class);
        Mockito.verify(controller, Mockito.times(1)).goToProfileActivity(captor.capture(), bundleArgumentCaptor.capture());
        Assert.assertEquals(captor.getValue(), view);
        Assert.assertEquals(bundleArgumentCaptor.getValue(), fragmentArguments);
    }

    @Test
    public void goToOtherMemberProfileActivityAnswered() {
        Mockito.doNothing().when(controller).goToOtherMemberProfileActivity(commonPersonObjectClient, fragmentArguments);
        controller.goToOtherMemberProfileActivity(commonPersonObjectClient, fragmentArguments);
        ArgumentCaptor<CommonPersonObjectClient> captor = ArgumentCaptor.forClass(CommonPersonObjectClient.class);
        ArgumentCaptor<Bundle> bundleArgumentCaptor = ArgumentCaptor.forClass(Bundle.class);
        Mockito.verify(controller, Mockito.times(1)).goToOtherMemberProfileActivity(captor.capture(), bundleArgumentCaptor.capture());
        Assert.assertEquals(captor.getValue(), commonPersonObjectClient);
        Assert.assertEquals(bundleArgumentCaptor.getValue(), fragmentArguments);
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

    @Test
    public void whenGetPncCommonPersonObjectAnswered() {
        Mockito.when(controller.getPncCommonPersonObject(baseID))
                .thenReturn(commonPersonObject);
        Assert.assertEquals(commonPersonObject, controller.getPncCommonPersonObject(baseID));
    }


}
