package org.smartregister.chw.core.dao;

import org.smartregister.chw.pmtct.dao.PmtctDao;

import java.util.List;

public class CorePmtctDao extends PmtctDao {
    public static boolean isClientKnownOnArt(String baseEntityId){
        String sql = "SELECT known_on_art FROM ec_pmtct_registration p " +
                "WHERE p.base_entity_id = '" + baseEntityId + "'";

        DataMap<String> dataMap = cursor -> getCursorValue(cursor, "known_on_art");

        List<String> res = readData(sql, dataMap);

        if (res.size() > 0 && res.get(0) != null) {
            return res.get(0).equalsIgnoreCase("yes");
        }
        return false;
    }
}
