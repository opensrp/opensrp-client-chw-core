package org.smartregister.chw.core.dao;

import static org.smartregister.chw.core.utils.ChildDBConstants.KEY.BIRTH_CERT;
import static org.smartregister.chw.core.utils.ChildDBConstants.KEY.BIRTH_CERT_NUMBER;
import static org.smartregister.chw.core.utils.ChildDBConstants.KEY.BIRTH_REGISTRATION;
import static org.smartregister.chw.core.utils.ChildDBConstants.KEY.BIRTH_REG_TYPE;
import static org.smartregister.chw.core.utils.ChildDBConstants.KEY.INFORMANT_REASON;
import static org.smartregister.chw.core.utils.ChildDBConstants.KEY.SYSTEM_BIRTH_NOTIFICATION;
import static org.smartregister.chw.core.utils.CoreConstants.BIRTH_CERTIFICATE_ISSUE_DATE;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.BASE_ENTITY_ID;
import static org.smartregister.chw.core.utils.CoreConstants.DrawerMenu.BIRTH_NOTIFICATION;

import android.content.ContentValues;

import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.chw.core.utils.ChildDBConstants;
import org.smartregister.dao.AbstractDao;

import java.util.Map;

public class BirthCertificationDao extends AbstractDao {

    public static void updateBirthCertification(Map<String, String> obsMap, String entityType, String baseEntityId) {
        if (obsMap == null || baseEntityId == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(BIRTH_CERT, obsMap.get(BIRTH_CERT));
        values.put(BIRTH_REGISTRATION, obsMap.get(BIRTH_REGISTRATION));
        values.put(ChildDBConstants.KEY.BIRTH_CERT_NOTIFIICATION, obsMap.get(ChildDBConstants.KEY.BIRTH_CERT_NOTIFIICATION));
        values.put(BIRTH_CERTIFICATE_ISSUE_DATE, obsMap.get(BIRTH_CERTIFICATE_ISSUE_DATE));
        values.put(BIRTH_CERT_NUMBER, obsMap.get(BIRTH_CERT_NUMBER));
        values.put(SYSTEM_BIRTH_NOTIFICATION, obsMap.get(SYSTEM_BIRTH_NOTIFICATION));
        values.put(BIRTH_REG_TYPE, obsMap.get(BIRTH_REG_TYPE));
        values.put(INFORMANT_REASON, obsMap.get(INFORMANT_REASON));

        SQLiteDatabase database = getRepository().getReadableDatabase();
        if (database != null) {
            String where = "" + BASE_ENTITY_ID + "=?";
            String[] whereArgs = new String[]{baseEntityId};

            database.update(entityType, values, where, whereArgs);
        }
    }
}
