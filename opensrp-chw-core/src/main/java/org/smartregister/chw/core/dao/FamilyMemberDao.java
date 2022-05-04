package org.smartregister.chw.core.dao;

import static org.smartregister.chw.core.utils.CoreConstants.ColumnNameConstants.BASE_ENTITY_ID;
import static org.smartregister.chw.core.utils.CoreConstants.ColumnNameConstants.ENTITY_TYPE;
import static org.smartregister.chw.core.utils.CoreConstants.ColumnNameConstants.FIRST_NAME;
import static org.smartregister.chw.core.utils.CoreConstants.ColumnNameConstants.LAST_NAME;
import static org.smartregister.chw.core.utils.CoreConstants.ColumnNameConstants.RELATIONAL_ID;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.DEATH_CAUSE;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.DEATH_MANNER;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.DEATH_PLACE;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.INFORMANT_ADDRESS;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.INFORMANT_NAME;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.INFORMANT_PHONE;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.INFORMANT_RELATIONSHIP;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.KNOW_DEATH_CAUSE;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.OFFICIAL_ADDRESS;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.OFFICIAL_ID;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.OFFICIAL_NAME;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.OFFICIAL_NUMBER;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.OFFICIAL_POSITION;

import android.content.ContentValues;

import net.sqlcipher.database.SQLiteDatabase;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.chw.anc.util.DBConstants;
import org.smartregister.chw.core.model.CoreFamilyMemberModel;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.dao.AbstractDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FamilyMemberDao extends AbstractDao {

    public static List<CoreFamilyMemberModel> familyMembersToUpdateLastName(String baseEntityId) {
        String sql = "SELECT DISTINCT fm.entity_type, fm.last_name, fm.base_entity_id,fm.relational_id FROM ec_family_member fm " +
                "INNER JOIN ec_family f on '" + baseEntityId + "' = fm.relational_id " +
                "WHERE f.first_name = fm.last_name";

        DataMap<CoreFamilyMemberModel> dataMap = cursor ->
                new CoreFamilyMemberModel(getCursorValue(cursor, LAST_NAME),
                        getCursorValue(cursor, BASE_ENTITY_ID),
                        getCursorValue(cursor, RELATIONAL_ID),
                        getCursorValue(cursor, ENTITY_TYPE));

        return readData(sql, dataMap);
    }

    public static List<CoreFamilyMemberModel> getMaleFamilyMembers(String relationalID) {
        String sql = "SELECT * FROM ec_family_member WHERE relational_id = '" + relationalID + "' " +
                "AND gender = 'Male' AND entity_type = 'ec_family_member'";

        DataMap<CoreFamilyMemberModel> dataMap = cursor ->
                new CoreFamilyMemberModel(getCursorValue(cursor, LAST_NAME),
                        getCursorValue(cursor, FIRST_NAME),
                        getCursorValue(cursor, BASE_ENTITY_ID),
                        getCursorValue(cursor, RELATIONAL_ID),
                        getCursorValue(cursor, ENTITY_TYPE));

        return readData(sql, dataMap);
    }

    public static List<CoreFamilyMemberModel> getAliveOrDeadFamilyMembers(String status) {
        String sql = "SELECT * FROM ec_family_member WHERE is_closed = '" + status + "' ";

        DataMap<CoreFamilyMemberModel> dataMap = cursor ->
                new CoreFamilyMemberModel(getCursorValue(cursor, LAST_NAME),
                        getCursorValue(cursor, FIRST_NAME),
                        getCursorValue(cursor, BASE_ENTITY_ID),
                        getCursorValue(cursor, RELATIONAL_ID),
                        getCursorValue(cursor, ENTITY_TYPE));

        return readData(sql, dataMap);
    }
    public static void updateRemovedFamilyMember(Map<String, String> obsMap, String baseEntityId, ContentValues values) throws ParseException {
        if (obsMap == null || baseEntityId == null) {
            return;
        }
        SQLiteDatabase database = getRepository().getReadableDatabase();
        if (database != null) {
            SimpleDateFormat defaultDf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date dod = getDate(obsMap, "date_died");
            values.put(DBConstants.KEY.DOD, defaultDf.format(dod));

            if (obsMap.containsKey(DEATH_MANNER))
                values.put(DEATH_MANNER, obsMap.get(DEATH_MANNER));

            if (obsMap.containsKey(DEATH_PLACE))
                values.put(DEATH_PLACE, obsMap.get(DEATH_PLACE));

            if (obsMap.containsKey(KNOW_DEATH_CAUSE))
                values.put(KNOW_DEATH_CAUSE, obsMap.get(KNOW_DEATH_CAUSE));

            if (obsMap.containsKey(DEATH_CAUSE))
                values.put(DEATH_CAUSE, obsMap.get(DEATH_CAUSE));

            if (obsMap.containsKey(OFFICIAL_NAME))
                values.put(OFFICIAL_NAME, obsMap.get(OFFICIAL_NAME));

            if (obsMap.containsKey(OFFICIAL_ID))
                values.put(OFFICIAL_ID, obsMap.get(OFFICIAL_ID));

            if (obsMap.containsKey(OFFICIAL_POSITION))
                values.put(OFFICIAL_POSITION, obsMap.get(OFFICIAL_POSITION));

            if (obsMap.containsKey(OFFICIAL_ADDRESS))
                values.put(OFFICIAL_ADDRESS, obsMap.get(OFFICIAL_ADDRESS));

            if (obsMap.containsKey(OFFICIAL_NUMBER))
                values.put(OFFICIAL_NUMBER, obsMap.get(OFFICIAL_NUMBER));

            if (obsMap.containsKey(INFORMANT_NAME))
                values.put(INFORMANT_NAME, obsMap.get(INFORMANT_NAME));

            if (obsMap.containsKey(INFORMANT_RELATIONSHIP))
                values.put(INFORMANT_RELATIONSHIP, obsMap.get(INFORMANT_RELATIONSHIP));

            if (obsMap.containsKey(INFORMANT_ADDRESS))
                values.put(INFORMANT_ADDRESS, obsMap.get(INFORMANT_ADDRESS));

            if (obsMap.containsKey(INFORMANT_PHONE))
                values.put(INFORMANT_PHONE, obsMap.get(INFORMANT_PHONE));

            String where = "" + CoreConstants.DB_CONSTANTS.BASE_ENTITY_ID + "=?";
            String[] whereArgs = new String[]{baseEntityId};
            database.update(CoreConstants.TABLE_NAME.FAMILY_MEMBER, values, where, whereArgs);
        }
    }

    private static Date getDate(Map<String, String> obsMap, String key) throws ParseException {
        String strDod = obsMap.get(key);
        if (StringUtils.isBlank(strDod)) return null;

        SimpleDateFormat nfDf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return nfDf.parse(strDod);
    }
}
