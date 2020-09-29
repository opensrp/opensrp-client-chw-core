package org.smartregister.chw.core.shadows;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.chw.core.dao.ChwNotificationDao;
import org.smartregister.chw.core.domain.NotificationRecord;

@Implements(ChwNotificationDao.class)
public class ChwNotificationDaoShadowHelper {

    @Implementation
    public static NotificationRecord getSickChildFollowUpRecord(String notificationId) {
        NotificationRecord record = initNotificationRecord();
        record.setCareGiverName();
        record.setVillage();
        record.setDiagnosis();
        return record;
    }

    private static NotificationRecord initNotificationRecord() {
        NotificationRecord record = new NotificationRecord("test-base-entity-id");
        record.setClientName();
        record.setVisitDate();
        return record;
    }

}
