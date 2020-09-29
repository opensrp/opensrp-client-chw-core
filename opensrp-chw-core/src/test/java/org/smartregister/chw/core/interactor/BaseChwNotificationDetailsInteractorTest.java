package org.smartregister.chw.core.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.domain.NotificationRecord;
import org.smartregister.chw.core.shadows.ChwNotificationDaoShadowHelper;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.VisitDaoShadowHelper;

@Config(shadows = {ChwNotificationDaoShadowHelper.class})
public class BaseChwNotificationDetailsInteractorTest extends BaseUnitTest {

    private BaseChwNotificationDetailsInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = Mockito.mock(BaseChwNotificationDetailsInteractor.class);
    }

    @Test
    public void canGetSickChildFollowUpDetailsNotificationItem() {

    }


    private NotificationRecord getTestNotificationRecord(String baseEntityId) {


        return new NotificationRecord(baseEntityId);
    }

}
