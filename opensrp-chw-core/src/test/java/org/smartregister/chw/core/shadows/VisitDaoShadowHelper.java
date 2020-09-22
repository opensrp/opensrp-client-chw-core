package org.smartregister.chw.core.shadows;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.core.dao.VisitDao;

import java.util.ArrayList;
import java.util.List;

@Implements(VisitDao.class)
public class VisitDaoShadowHelper {

    @Implementation
    public static List<Visit> getVisitsByMemberID(String baseEntityID) {
        List<Visit> visits = new ArrayList<>();
        visits.add(getTestVisit());
        return visits;
    }


    public static Visit getTestVisit() {
        Visit visit = new Visit();
        visit.setBaseEntityId();
        visit.setVisitId();
        visit.setDate();
        visit.setBaseEntityId();
        visit.setVisitType();
        visit.setParentVisitID();
        visit.setPreProcessedJson();
        visit.setBaseEntityId();
        visit.setDate();
        visit.setJson();
        visit.setFormSubmissionId();
        visit.setProcessed();
        visit.setCreatedAt();
        visit.setUpdatedAt();
        return visit;
    }
}
