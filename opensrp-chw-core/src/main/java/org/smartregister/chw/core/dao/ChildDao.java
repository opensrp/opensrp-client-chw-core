package org.smartregister.chw.core.dao;

import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.core.domain.Child;
import org.smartregister.dao.AbstractDao;

import java.util.ArrayList;
import java.util.List;

public class ChildDao extends AbstractDao {

    public static List<Child> getFamilyChildren(String familyBaseEntityID) {
        String sql = "select  c.base_entity_id , c.first_name , c.last_name , c.middle_name , c.mother_entity_id , c.relational_id , c.dob , c.date_created ,  lastVisit.last_visit_date , last_visit_not_done_date " +
                "from ec_child c " +
                "inner join ec_family_member m on c.base_entity_id = m.base_entity_id COLLATE NOCASE " +
                "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE  " +
                "left join ( " +
                " select base_entity_id , max(visit_date) last_visit_date " +
                " from visits " +
                " where visit_type in ('Child Home Visit') " +
                " group by base_entity_id " +
                ") lastVisit on lastVisit.base_entity_id = c.base_entity_id " +
                "left join ( " +
                " select base_entity_id , max(visit_date) last_visit_not_done_date " +
                " from visits " +
                " where visit_type in ('Visit not done') " +
                " group by base_entity_id " +
                ") lastVisitNotDone on lastVisitNotDone.base_entity_id = c.base_entity_id " +
                "where f.base_entity_id = '" + familyBaseEntityID + "' " +
                "and  m.date_removed is null and m.is_closed = 0 " +
                "and ((( julianday('now') - julianday(c.dob))/365.25) < 5) and c.is_closed = 0  " +
                "and (( ifnull(entry_point,'') <> 'PNC' ) or (ifnull(entry_point,'') = 'PNC' and date(c.dob, '+28 days') > date()))";

        List<Child> values = AbstractDao.readData(sql, getChildDataMap());
        if (values == null || values.size() == 0)
            return new ArrayList<>();

        return values;
    }

    public static MemberObject getMember(String baseEntityID) {
        String sql = "select m.base_entity_id , m.unique_id , m.relational_id , m.dob , m.first_name , m.middle_name , m.last_name , m.gender , " +
                "m.phone_number , m.other_phone_number , f.first_name family_name , f.primary_caregiver , f.family_head , " +
                "fh.first_name family_head_first_name , fh.middle_name family_head_middle_name, fh.last_name family_head_last_name, " +
                "fh.phone_number family_head_phone_number , f.village_town , c.last_interacted_with " +
                "from ec_family_member m " +
                "inner join ec_family f on m.relational_id = f.base_entity_id " +
                "left join ec_family_member fh on fh.base_entity_id = f.family_head " +
                "left join ec_child c on c.base_entity_id = fh.base_entity_id " +
                "where m.base_entity_id = '" + baseEntityID + "' ";

        DataMap<MemberObject> dataMap = cursor -> {
            MemberObject memberObject = new MemberObject();
            memberObject.setChwMemberId(getCursorValue(cursor, "unique_id", ""));
            memberObject.setBaseEntityId(getCursorValue(cursor, "base_entity_id", ""));
            memberObject.setFamilyBaseEntityId(getCursorValue(cursor, "relational_id", ""));
            memberObject.setFamilyHead(getCursorValue(cursor, "family_head", ""));

            String familyHeadName = getCursorValue(cursor, "family_head_first_name", "") + " "
                    + getCursorValue(cursor, "family_head_middle_name", "");

            familyHeadName = (familyHeadName.trim() + " " + getCursorValue(cursor, "family_head_last_name", "")).trim();

            memberObject.setFamilyHeadName(familyHeadName);
            memberObject.setFamilyHeadPhoneNumber(getCursorValue(cursor, "family_head_phone_number", ""));
            memberObject.setPrimaryCareGiver(getCursorValue(cursor, "primary_caregiver"));
            memberObject.setFamilyName(getCursorValue(cursor, "family_name", ""));
            memberObject.setLastInteractedWith(getCursorValue(cursor, "last_interacted_with"));
            memberObject.setFirstName(getCursorValue(cursor, "first_name", ""));
            memberObject.setMiddleName(getCursorValue(cursor, "middle_name", ""));
            memberObject.setLastName(getCursorValue(cursor, "last_name", ""));
            memberObject.setDob(getCursorValue(cursor, "dob"));
            memberObject.setPhoneNumber(getCursorValue(cursor, "phone_number", ""));
            memberObject.setAddress(getCursorValue(cursor, "village_town"));

            return memberObject;
        };

        List<MemberObject> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return null;

        return res.get(0);
    }

    public static Child getChild(String baseEntityID) {
        String sql = "select  c.base_entity_id , c.first_name , c.last_name , c.middle_name , c.mother_entity_id , c.relational_id , c.dob , c.date_created ,  lastVisit.last_visit_date , last_visit_not_done_date " +
                "from ec_child c " +
                "inner join ec_family_member m on c.base_entity_id = m.base_entity_id COLLATE NOCASE " +
                "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE  " +
                "left join ( " +
                " select base_entity_id , max(visit_date) last_visit_date " +
                " from visits " +
                " where visit_type in ('Child Home Visit') " +
                " group by base_entity_id " +
                ") lastVisit on lastVisit.base_entity_id = c.base_entity_id " +
                "left join ( " +
                " select base_entity_id , max(visit_date) last_visit_not_done_date " +
                " from visits " +
                " where visit_type in ('Visit not done') " +
                " group by base_entity_id " +
                ") lastVisitNotDone on lastVisitNotDone.base_entity_id = c.base_entity_id " +
                "where c.base_entity_id = '" + baseEntityID + "' " +
                "and  m.date_removed is null and m.is_closed = 0 " +
                "and ((( julianday('now') - julianday(c.dob))/365.25) < 5) and c.is_closed = 0  " +
                "and (( ifnull(entry_point,'') <> 'PNC' ) or (ifnull(entry_point,'') = 'PNC' and date(c.dob, '+28 days') > date()))";

        List<Child> values = AbstractDao.readData(sql, getChildDataMap());
        if (values == null || values.size() != 1)
            return null;

        return values.get(0);
    }

    private static DataMap<Child> getChildDataMap() {
        return c -> {
            Child record = new Child();
            record.setBaseEntityID(getCursorValue(c, "base_entity_id"));
            record.setFirstName(getCursorValue(c, "first_name"));
            record.setLastName(getCursorValue(c, "last_name"));
            record.setMiddleName(getCursorValue(c, "middle_name"));
            record.setMotherBaseEntityID(getCursorValue(c, "mother_entity_id"));
            record.setFamilyBaseEntityID(getCursorValue(c, "relational_id"));
            record.setDateOfBirth(getCursorValueAsDate(c, "dob", getDobDateFormat()));
            record.setDateCreated(getCursorValueAsDate(c, "date_created", getDobDateFormat()));
            record.setLastVisitDate(getCursorValueAsDate(c, "last_visit_date"));
            record.setLastVisitNotDoneDate(getCursorValueAsDate(c, "last_visit_not_done_date"));
            return record;
        };
    }

    public static boolean isChild(String baseEntityID) {
        String sql = "select count(c.base_entity_id) count from ec_child c where c.base_entity_id = '" + baseEntityID + "' " +
                "and c.is_closed = 0 and (( ifnull(entry_point,'') <> 'PNC' ) or (ifnull(entry_point,'') = 'PNC' and date(c.dob, '+28 days') > date()))";

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return false;

        return res.get(0) > 0;
    }
}
