package org.smartregister.chw.core.dao;

import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.BASE_ENTITY_ID;
import static org.smartregister.chw.core.utils.CoreConstants.FORM_CONSTANTS.REMOVE_MEMBER_FORM.DEATH_CERTIFICATE_ISSUE_DATE;
import static org.smartregister.chw.core.utils.CoreConstants.FORM_CONSTANTS.REMOVE_MEMBER_FORM.DEATH_CERTIFICATE_NUMBER;
import static org.smartregister.chw.core.utils.CoreConstants.FORM_CONSTANTS.REMOVE_MEMBER_FORM.DEATH_NOTIFICATION_DONE;
import static org.smartregister.chw.core.utils.CoreConstants.FORM_CONSTANTS.REMOVE_MEMBER_FORM.RECEIVED_DEATH_CERTIFICATE;
import static org.smartregister.chw.core.utils.CoreConstants.INFORMANT_ADDRESS;
import static org.smartregister.chw.core.utils.CoreConstants.INFORMANT_NAME;
import static org.smartregister.chw.core.utils.CoreConstants.INFORMANT_PHONE;
import static org.smartregister.chw.core.utils.CoreConstants.INFORMANT_RELATIONSHIP;
import static org.smartregister.chw.core.utils.CoreConstants.OFFICIAL_ADDRESS;
import static org.smartregister.chw.core.utils.CoreConstants.OFFICIAL_ID;
import static org.smartregister.chw.core.utils.CoreConstants.OFFICIAL_NAME;
import static org.smartregister.chw.core.utils.CoreConstants.OFFICIAL_NUMBER;
import static org.smartregister.chw.core.utils.CoreConstants.OFFICIAL_POSITION;

import android.content.ContentValues;

import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.dao.AbstractDao;

import java.util.Map;

public class DeathCertificationDao extends AbstractDao {

    public static void updateDeathCertification(Map<String, String> obsMap, String entityType, String baseEntityId) {
        if (obsMap == null || baseEntityId == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(RECEIVED_DEATH_CERTIFICATE, obsMap.get(RECEIVED_DEATH_CERTIFICATE));
        values.put(DEATH_CERTIFICATE_ISSUE_DATE, obsMap.get(DEATH_CERTIFICATE_ISSUE_DATE));
        values.put(DEATH_NOTIFICATION_DONE, obsMap.get(DEATH_NOTIFICATION_DONE));
        values.put(DEATH_CERTIFICATE_NUMBER, obsMap.get(DEATH_CERTIFICATE_NUMBER));
        values.put(INFORMANT_NAME, obsMap.get(INFORMANT_NAME));
        values.put(INFORMANT_RELATIONSHIP, obsMap.get(INFORMANT_RELATIONSHIP));
        values.put(INFORMANT_ADDRESS, obsMap.get(INFORMANT_ADDRESS));
        values.put(INFORMANT_PHONE, obsMap.get(INFORMANT_PHONE));
        values.put(OFFICIAL_NAME, obsMap.get(OFFICIAL_NAME));
        values.put(OFFICIAL_ID, obsMap.get(OFFICIAL_ID));
        values.put(OFFICIAL_POSITION, obsMap.get(OFFICIAL_POSITION));
        values.put(OFFICIAL_ADDRESS, obsMap.get(OFFICIAL_ADDRESS));
        values.put(OFFICIAL_NUMBER, obsMap.get(OFFICIAL_NUMBER));

        SQLiteDatabase database = getRepository().getReadableDatabase();
        if (database != null) {
            String where = "" + BASE_ENTITY_ID + "=?";
            String[] whereArgs = new String[]{baseEntityId};

            database.update(entityType, values, where, whereArgs);
        }
    }
}
