package org.smartregister.chw.core.dao;

import org.jetbrains.annotations.Nullable;
import org.smartregister.dao.AbstractDao;

import java.util.Date;
import java.util.List;

public class MalariaDao extends AbstractDao {

    @Nullable
    public static Date getMalariaTestDate(String baseEntityID) {
        String sql = "select malaria_test_date from ec_malaria_confirmation where base_entity_id = '" + baseEntityID + "'";

        DataMap<Date> dataMap = cursor -> getCursorValueAsDate(cursor, "malaria_test_date", getNativeFormsDateFormat());

        List<Date> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return null;

        return res.get(0);
    }

    public static boolean isRegisteredForMalaria(String baseEntityID) {
        String sql = String.format(
                "select count(ec_malaria_confirmation.base_entity_id) count\n" +
                        "from ec_malaria_confirmation\n" +
                        "where base_entity_id = '%s'\n" +
                        "  and ec_malaria_confirmation.is_closed = 0\n" +
                        "  and ec_malaria_confirmation.malaria_test_date IS NOT NULL", baseEntityID);

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return false;

        return res.get(0) > 0;
    }
}
