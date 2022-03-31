package org.smartregister.chw.core.dao;

import org.smartregister.dao.AbstractDao;

public class SbccDao extends AbstractDao {
    public static void updateData(String baseEntityID, String sbccDate, String locationType, String participantsNumber) {
        String sql = String.format("INSERT INTO ec_sbcc (id, sbcc_date, location_type, participants_number) VALUES ('%s', '%s', '%s', '%s') ON CONFLICT (id) DO UPDATE SET sbcc_date = '%s', location_type = '%s', participants_number = '%s'", baseEntityID, sbccDate, locationType, participantsNumber, sbccDate, locationType, participantsNumber);
        updateDB(sql);
    }
}
