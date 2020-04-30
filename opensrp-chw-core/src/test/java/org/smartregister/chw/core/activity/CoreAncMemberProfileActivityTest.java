package org.smartregister.chw.core.activity;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.anc.domain.MemberObject;

public class CoreAncMemberProfileActivityTest {

    private CoreAncMemberProfileActivity activity;

    private View viewFamilyRow = new View(RuntimeEnvironment.systemContext);

    @Mock
    private MemberObject memberObject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        activity = Mockito.mock(CoreAncMemberProfileActivity.class, Mockito.CALLS_REAL_METHODS);
        String baseEntityId = "base-entity-id";
        memberObject.setBaseEntityId(baseEntityId);
        ReflectionHelpers.setField(activity, "memberObject", memberObject);
        ReflectionHelpers.setField(activity, "view_family_row", viewFamilyRow);
    }

    @Test
    public void registerPresenter() {
        activity.registerPresenter();
        Assert.assertNotNull(activity.ancMemberProfilePresenter());
    }
}