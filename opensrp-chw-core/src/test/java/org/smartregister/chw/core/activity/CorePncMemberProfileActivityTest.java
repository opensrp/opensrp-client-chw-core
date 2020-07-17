package org.smartregister.chw.core.activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.interactor.CorePncMemberProfileInteractor;

public class CorePncMemberProfileActivityTest extends BaseUnitTest {

    private CorePncMemberProfileActivity activity;

    @Mock
    private MemberObject memberObject;

    @Mock
    private CorePncMemberProfileInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        activity = Mockito.mock(CorePncMemberProfileActivity.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(activity, "memberObject", memberObject);
        ReflectionHelpers.setField(activity, "pncMemberProfileInteractor", interactor);
    }

    @Test
    public void presenterIsInitialisedCorrectly() {
        activity.registerPresenter();
        Assert.assertNotNull(activity.getPncMemberProfilePresenter());
    }

    @Test
    public void getChildrenInvokesInteractorGetChildrenUnder29() {
        activity.getChildren(memberObject);
        Mockito.verify(interactor).pncChildrenUnder29Days(memberObject.getBaseEntityId());
    }
}
