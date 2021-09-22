package org.smartregister.chw.core.shadows;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.fp.dao.FpDao;

import java.util.Date;

@Implements(FpDao.class)
public class FPDaoShadowHelper {

    @Implementation
    public static Visit getLatestFpVisit(String baseEntityId, String entityType, String fpMethod) {
        Visit latestFpVisit = new Visit();
        latestFpVisit.setVisitId("7820ab67-b034-444d-b858-b2ae0dab3e68");
        latestFpVisit.setParentVisitID(null);
        latestFpVisit.setVisitType("Fp Follow Up Visit");
        latestFpVisit.setDate(new Date(Long.parseLong("1587037164000")));
        return latestFpVisit;
    }

}
