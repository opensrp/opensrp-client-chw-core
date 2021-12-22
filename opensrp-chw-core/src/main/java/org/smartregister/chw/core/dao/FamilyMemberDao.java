package org.smartregister.chw.core.dao;

import static org.smartregister.chw.core.utils.CoreConstants.COLUMN_NAME.BASE_ENTITY_ID;
import static org.smartregister.chw.core.utils.CoreConstants.COLUMN_NAME.ENTITY_TYPE;
import static org.smartregister.chw.core.utils.CoreConstants.COLUMN_NAME.FIRST_NAME;
import static org.smartregister.chw.core.utils.CoreConstants.COLUMN_NAME.LAST_NAME;
import static org.smartregister.chw.core.utils.CoreConstants.COLUMN_NAME.RELATIONAL_ID;

import org.smartregister.chw.core.model.CoreFamilyMemberModel;
import org.smartregister.dao.AbstractDao;

import java.util.List;

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
}
