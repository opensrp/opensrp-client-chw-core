package org.smartregister.chw.core.interactor;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.domain.NotificationItem;
import org.smartregister.chw.core.presenter.BaseChwNotificationDetailsPresenter;
import org.smartregister.chw.core.shadows.ChwNotificationDaoShadowHelper;

@Config(shadows = {ChwNotificationDaoShadowHelper.class})
public class BaseChwNotificationDetailsInteractorTest extends BaseUnitTest {

    private BaseChwNotificationDetailsInteractor interactor;

    @Mock
    private BaseChwNotificationDetailsPresenter presenter;

    private Context context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = Mockito.mock(BaseChwNotificationDetailsInteractor.class, Mockito.CALLS_REAL_METHODS);
        context = RuntimeEnvironment.application;
        ReflectionHelpers.setField(interactor, "presenter", presenter);
        ReflectionHelpers.setField(interactor, "context", context);
    }

    @Test
    public void canGetSickChildFollowUpDetails() {
        ArgumentCaptor<NotificationItem> notificationItemCaptor = ArgumentCaptor.forClass(NotificationItem.class);
        interactor.fetchNotificationDetails("testNotificationId", context.getString(R.string.notification_type_sick_child_follow_up));
        Mockito.verify(presenter, Mockito.times(1)).onNotificationDetailsFetched(notificationItemCaptor.capture());
        Assert.assertEquals("Test Client has attended the facility on 2020-05-21 17:16:31", notificationItemCaptor.getValue().getTitle());
        Assert.assertEquals("CG: Mama Yake", notificationItemCaptor.getValue().getDetails().get(0));
    }
}
