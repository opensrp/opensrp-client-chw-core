package org.smartregister.chw.core.dao;

import org.smartregister.chw.core.domain.NotificationRecord;
import org.smartregister.dao.AbstractDao;

import java.util.List;

public class ChwNotificationDao extends AbstractDao {

    /**
     * This method is used to get details of referral notification with the provided task id
     *
     * @param referralId unique identifier for the task
     * @return a notification record with details for the referral
     */
    @Deprecated
    public static NotificationRecord getSuccessfulReferral(String referralId) {
        String sql = String.format(
                "/* Get details for successful referral */\n" +
                        "SELECT ec_family_member.first_name || ' ' || CASE ec_family_member.last_name\n" +
                        "                                                 WHEN NULL THEN ec_family_member.middle_name\n" +
                        "                                                 ELSE ec_family_member.last_name END full_name,\n" +
                        "       ec_family_member.dob          AS                                              dob,\n" +
                        "       ec_family.village_town        AS                                              village,\n" +
                        "       event.dateCreated             AS                                              notification_date,\n" +
                        "       ec_family_member.phone_number AS                                              phone_number,\n" +
                        "       ec_family_member.base_entity_id AS                                            base_entity_id\n" +
                        "\n" +
                        "FROM task\n" +
                        "         inner join ec_family_member on ec_family_member.base_entity_id = task.for\n" +
                        "         inner join ec_close_referral on ec_close_referral.referral_task = task._id\n" +
                        "         inner join event on ec_close_referral.id = event.formSubmissionId\n" +
                        "         inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
                        "\n" +
                        "WHERE ec_family_member.is_closed = '0'\n" +
                        "  AND ec_family_member.date_removed is null\n" +
                        "  AND task.code = 'Referral'\n" +
                        "  AND task._id = '%s' COLLATE NOCASE\n", referralId);

        return AbstractDao.readSingleValue(sql, mapColumnValuesToModel());

    }

    public static NotificationRecord getSickChildFollowUpRecord(String baseEntityId) {
        String sql = String.format(
                "/* Get details for a sick child follow-up */\n" +
                        "SELECT ec_family_member.first_name || ' ' || CASE ec_family_member.last_name\n" +
                        "                                                 WHEN NULL THEN ec_family_member.middle_name\n" +
                        "                                                 ELSE ec_family_member.last_name END full_name,\n" +
                        "cg.first_name || ' ' || CASE cg.last_name \n" +
                        "                                                                         WHEN NULL THEN cg.middle_name \n" +
                        "                                                                         ELSE ec_family_member.last_name \n" +
                        "END cg_full_name, " +
                        "       ec_family.village_town        AS      village,\n" +
                        "       ec_sick_child_followup.diagnosis AS diagnosis,\n" +
                        "       ec_sick_child_followup.results AS results,\n" +
                        "       ec_sick_child_followup.visit_date AS visit_date\n" +
                        "\n" +
                        "FROM ec_sick_child_followup\n" +
                        "         inner join ec_family_member on ec_family_member.base_entity_id = ec_sick_child_followup.base_entity_id\n" +
                        "         inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
                        "         inner join (select fm.base_entity_id, fm.first_name, fm.middle_name, fm.last_name from ec_family_member fm) cg on ec_family.primary_caregiver = cg.base_entity_id\n" +
                        "\n" +
                        "WHERE ec_family_member.is_closed = '0'\n" +
                        "  AND ec_family_member.date_removed is null\n" +
                        "  AND ec_sick_child_followup.base_entity_id = '%s'\n", baseEntityId);

        return AbstractDao.readSingleValue(sql, mapColumnValuesToModel());
    }

    private static DataMap<NotificationRecord> mapColumnValuesToModel() {
        return row -> {
            NotificationRecord record = new NotificationRecord(getCursorValue(row, "base_entity_id"));
            record.setClientName(getCursorValue(row, "full_name"));
            record.setCareGiverName(getCursorValue(row, "cg_full_name"));
            record.setVillage(getCursorValue(row, "village"));
            record.setVisitDate(getCursorValue(row, "visit_date"));
            record.setDiagnosis(getCursorValue(row, "diagnosis"));
            record.setResults(getCursorValue(row, "results"));
            return record;
        };
    }

    /**
     * This method is used to check whether a referral has been marked as done or not
     *
     * @param referralTaskId the unique identifier for the referral task
     * @return true if the referral with the provided task id is already marked as done false otherwise
     */
    public static boolean isMarkedAsDone(String referralTaskId) {
        String sql = String.format("select count(*) count from ec_referral_dismissal where referral_task = '%s'", referralTaskId);

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return false;

        return res.get(0) > 0;
    }
}
