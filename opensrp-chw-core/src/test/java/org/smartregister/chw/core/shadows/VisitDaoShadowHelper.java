package org.smartregister.chw.core.shadows;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.domain.VisitDetail;
import org.smartregister.chw.core.dao.VisitDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Implements(VisitDao.class)
public class VisitDaoShadowHelper {

    @Implementation
    public static Map<String, List<VisitDetail>> getMedicalHistory(String baseEntityID) {
        HashMap<String, List<VisitDetail>> detailsMap = new LinkedHashMap<>();
        detailsMap.put("test-visit-id", getTestVisitDetailsMap());
        return detailsMap;
    }


    public static List<VisitDetail> getTestVisitDetailsMap() {
        List<VisitDetail> details = new ArrayList<>();
        VisitDetail detail = new VisitDetail();
        detail.setVisitId("test-visit-id");
        detail.setBaseEntityId("test_base_entity_id");
        detail.setVisitKey("test_visit_key");
        detail.setParentCode("test_parent_code");
        detail.setPreProcessedType("test_preprocessed_type");
        detail.setDetails("test_details");
        detail.setHumanReadable("test_human_readable_details");
        details.add(detail);
        return details;
    }
}
