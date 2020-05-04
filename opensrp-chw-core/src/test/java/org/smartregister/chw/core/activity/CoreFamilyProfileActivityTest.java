package org.smartregister.chw.core.activity;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.custom_views.FamilyFloatingMenu;
import org.smartregister.chw.core.utils.ChildDBConstants;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;

import java.util.HashMap;
import java.util.Map;

public class CoreFamilyProfileActivityTest extends BaseUnitTest {

    private CoreFamilyProfileActivity activity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        activity = Mockito.mock(CoreFamilyProfileActivity.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void updateHasPhoneAnswered() {
        FamilyFloatingMenu familyFloatingMenu = Mockito.mock(FamilyFloatingMenu.class);
        ReflectionHelpers.setField(activity, "familyFloatingMenu", familyFloatingMenu);

        activity.updateHasPhone(true);
        Mockito.verify(familyFloatingMenu).reDraw(true);
    }

    @Test
    public void goToProfileActivityAnswered() {
        View view = Mockito.mock(View.class);
        activity = Mockito.spy(activity);

        Map<String, String> columnMap = new HashMap<>();
        columnMap.put(ChildDBConstants.KEY.ENTITY_TYPE, CoreConstants.TABLE_NAME.CHILD);
        CommonPersonObjectClient client = Mockito.mock(CommonPersonObjectClient.class);
        Mockito.doReturn(columnMap).when(client).getColumnmaps();

        Mockito.doReturn(client).when(view).getTag();
        Mockito.doNothing().when(activity).goToChildProfileActivity(Mockito.any(), Mockito.any());

        activity.goToProfileActivity(view, null);

        Mockito.verify(activity).goToChildProfileActivity(client, null);
    }

    @Test
    public void whenGetFamilyBaseEntityIdAnswered() {
        ReflectionHelpers.setField(activity, "familyBaseEntityId", "12345");
        Assert.assertEquals("12345", activity.getFamilyBaseEntityId());
    }


}
