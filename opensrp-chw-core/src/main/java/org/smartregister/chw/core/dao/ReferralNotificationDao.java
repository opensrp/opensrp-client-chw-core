package org.smartregister.chw.core.dao;

import org.smartregister.chw.core.domain.ReferralNotificationRecord;
import org.smartregister.dao.AbstractDao;

import java.util.List;

public class ReferralNotificationDao extends AbstractDao {

    /**
     *This method is used to get details of referral notification with the provided task id
     * @param referralId unique identifier for the task
     * @return a notification record with details for the referral
     */
    public static ReferralNotificationRecord getSuccessfulReferral(String referralId) {
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
                "  AND task.business_status = 'Complete'\n" +
                "  AND task.status = 'COMPLETED'\n" +
                "  AND task.code = 'Referral'\n" +
                "  AND task._id = '%s'\n", referralId);

        return AbstractDao.readSingleValue(sql, mapColumnValuesToModel());

    }

    private static DataMap<ReferralNotificationRecord> mapColumnValuesToModel() {
        return row -> {
            ReferralNotificationRecord record = new ReferralNotificationRecord(getCursorValue(row, "base_entity_id"));
            record.setClientName(getCursorValue(row, "full_name"));
            record.setClientDateOfBirth(getCursorValue(row, "dob"));
            record.setVillage(getCursorValue(row, "village"));
            record.setNotificationDate(getCursorValue(row, "notification_date"));
            record.setPhone(getCursorValue(row, "phone_number"));
            return record;
        };
    }

    /**
     * This method is used to check whether a referral has been marked as done or not
     * @param referralTaskId the unique identifier for the referral task
     * @return true if the referral with the provided task id is already marked as done false otherwise
     */
    public static boolean isMarkedAsDone(String referralTaskId){
        String sql = String.format("select count(*) count from ec_referral_dismissal where referral_task = '%s'" , referralTaskId);

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return false;

        return res.get(0) > 0;
    }
}
