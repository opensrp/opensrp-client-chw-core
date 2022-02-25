package org.smartregister.chw.core.utils;

import org.smartregister.chw.referral.util.Constants;

public class QueryUtils {

    public static String countEcFamily = "select count(*) from ec_family where date_removed is null AND (entity_type = 'ec_family' OR entity_type = 'ec_family_member' OR entity_type IS NULL)";
    public static String countTask = "select count(*) from task inner join " +
            "ec_family_member member on member.base_entity_id = task.for COLLATE NOCASE " +
            "WHERE task.business_status = '%s' and member.date_removed is null ";
    public static String countAncMember = "select count(*) " +
            "from ec_anc_register r " +
            "inner join ec_family_member m on r.base_entity_id = m.base_entity_id COLLATE NOCASE " +
            "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
            "where m.date_removed is null and m.is_closed = 0 and r.is_closed = 0 ";
    public static String countEcPregnencyOutcome = "select count(*) " +
            "from ec_pregnancy_outcome p " +
            "inner join ec_family_member m on p.base_entity_id = m.base_entity_id COLLATE NOCASE " +
            "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
            "where m.date_removed is null and p.delivery_date is not null and p.is_closed = 0 and m.is_closed = 0 ";
    public static String countEcMalaria = "select count (p.base_entity_id) from ec_malaria_confirmation p " +
            "inner join ec_family_member m on p.base_entity_id = m.base_entity_id COLLATE NOCASE " +
            "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
            "where m.date_removed is null and p.is_closed = 0 AND p.malaria = 1 " +
            "AND datetime('NOW') <= datetime(p.last_interacted_with/1000, 'unixCoreDeadClientsProviderepoch', 'localtime','+15 days')";
    public static String countReferral = "select count(*) " +
            "from " + Constants.Tables.REFERRAL + " p " +
            "inner join ec_family_member m on p.entity_id = m.base_entity_id COLLATE NOCASE " +
            "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
            "inner join task t on p.id = t.reason_reference COLLATE NOCASE " +
            "where m.date_removed is null and t.business_status = '" + CoreConstants.BUSINESS_STATUS.REFERRED + "' ";
    public static String countBirthSummary = "Select ((Select count(*) from ec_child LEFT JOIN ec_family ON  ec_child.relational_id = ec_family.id COLLATE NOCASE  LEFT JOIN ec_family_member ON ec_family_member.base_entity_id = ec_family.primary_caregiver COLLATE NOCASE  LEFT JOIN (select base_entity_id , max(visit_date) visit_date from visits GROUP by base_entity_id) VISIT_SUMMARY ON VISIT_SUMMARY.base_entity_id = ec_child.base_entity_id WHERE  ec_child.date_removed is null AND ((( julianday('now') - julianday(ec_child.dob))/365.25) <5)   and (( ifnull(ec_child.entry_point,'') <> 'PNC' ) or (ifnull(ec_child.entry_point,'') = 'PNC' and ( date(ec_child.dob, '+28 days') <= date() and ((SELECT is_closed FROM ec_family_member WHERE base_entity_id = ec_child.mother_entity_id ) = 0))) or (ifnull(ec_child.entry_point,'') = 'PNC'  and (SELECT is_closed FROM ec_family_member WHERE base_entity_id = ec_child.mother_entity_id ) = 1)) and ((( julianday('now') - julianday(ec_child.dob))/365.25) < 5)) + (Select count(*) from ec_out_of_area_child)) as sumcount";
    public static String countEcFamilyPlanning = "select count(*) " +
            "from ec_family_planning p " +
            "inner join ec_family_member m on p.base_entity_id = m.base_entity_id COLLATE NOCASE " +
            "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
            "where m.date_removed is null and p.is_closed = 0 ";
    public static String countRegisteredChildClients = "SELECT SUM(c)\n" +
            "FROM (\n" +
            "         SELECT COUNT(*) AS c\n" +
            "         FROM ec_child\n" +
            "                  inner join ec_family_member on ec_family_member.base_entity_id = ec_child.base_entity_id\n" +
            "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
            "         WHERE ec_family_member.is_closed = '0'\n" +
            "           AND ec_family_member.date_removed is null\n" +
            "           AND cast(strftime('%Y-%m-%d %H:%M:%S', 'now') - strftime('%Y-%m-%d %H:%M:%S', ec_child.dob) as int) > 0\n" +
            "         UNION ALL\n" +
            "/**COUNT REGISTERED ANC CLIENTS*/\n" +
            "         SELECT COUNT(*) AS c\n" +
            "         FROM ec_anc_register\n" +
            "                  inner join ec_family_member on ec_family_member.base_entity_id = ec_anc_register.base_entity_id\n" +
            "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
            "         where ec_family_member.date_removed is null\n" +
            "           and ec_anc_register.is_closed is 0\n" +
            "         UNION ALL\n" +
            "/**COUNT REGISTERED PNC CLIENTS*/\n" +
            "         SELECT COUNT(*) AS c\n" +
            "         FROM ec_pregnancy_outcome\n" +
            "                  inner join ec_family_member on ec_family_member.base_entity_id = ec_pregnancy_outcome.base_entity_id\n" +
            "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
            "         where ec_family_member.date_removed is null\n" +
            "           and ec_pregnancy_outcome.is_closed is 0\n" +
            "           AND ec_pregnancy_outcome.base_entity_id NOT IN\n" +
            "               (SELECT base_entity_id FROM ec_anc_register WHERE ec_anc_register.is_closed IS 0)\n" +
            "         UNION ALL\n" +
            "/*COUNT OTHER FAMILY MEMBERS*/\n" +
            "         SELECT COUNT(*) AS c\n" +
            "         FROM ec_family_member\n" +
            "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
            "         where ec_family_member.date_removed is null\n" +
            "           AND (ec_family.entity_type = 'ec_family' OR ec_family.entity_type is null)\n" +
            "           AND ec_family_member.base_entity_id NOT IN (\n" +
            "             SELECT ec_anc_register.base_entity_id AS base_entity_id\n" +
            "             FROM ec_anc_register\n" +
            "             UNION ALL\n" +
            "             SELECT ec_pregnancy_outcome.base_entity_id AS base_entity_id\n" +
            "             FROM ec_pregnancy_outcome\n" +
            "             UNION ALL\n" +
            "             SELECT ec_child.base_entity_id AS base_entity_id\n" +
            "             FROM ec_child\n" +
            "             UNION ALL\n" +
            "             SELECT ec_malaria_confirmation.base_entity_id AS base_entity_id\n" +
            "             FROM ec_malaria_confirmation\n" +
            "             UNION ALL\n" +
            "             SELECT ec_family_planning.base_entity_id AS base_entity_id\n" +
            "             FROM ec_family_planning\n" +
            "         )\n" +
            "         UNION ALL\n" +
            "/*COUNT INDEPENDENT MEMBERS*/\n" +
            "         SELECT COUNT(*) AS c\n" +
            "         FROM ec_family_member\n" +
            "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
            "         where ec_family_member.date_removed is null\n" +
            "           AND ec_family.entity_type = 'ec_independent_client'\n" +
            "           AND ec_family_member.base_entity_id NOT IN (\n" +
            "             SELECT ec_anc_register.base_entity_id AS base_entity_id\n" +
            "             FROM ec_anc_register\n" +
            "             UNION ALL\n" +
            "             SELECT ec_pregnancy_outcome.base_entity_id AS base_entity_id\n" +
            "             FROM ec_pregnancy_outcome\n" +
            "             UNION ALL\n" +
            "             SELECT ec_child.base_entity_id AS base_entity_id\n" +
            "             FROM ec_child\n" +
            "             UNION ALL\n" +
            "             SELECT ec_malaria_confirmation.base_entity_id AS base_entity_id\n" +
            "             FROM ec_malaria_confirmation\n" +
            "             UNION ALL\n" +
            "             SELECT ec_family_planning.base_entity_id AS base_entity_id\n" +
            "             FROM ec_family_planning\n" +
            "         )\n" +
            "         UNION ALL\n" +
            "/**COUNT REGISTERED MALARIA CLIENTS*/\n" +
            "         SELECT COUNT(*) AS c\n" +
            "         FROM ec_family_member\n" +
            "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
            "                  inner join ec_malaria_confirmation\n" +
            "                             on ec_family_member.base_entity_id = ec_malaria_confirmation.base_entity_id\n" +
            "         where ec_family_member.date_removed is null\n" +
            "           AND ec_family_member.base_entity_id NOT IN (\n" +
            "             SELECT ec_anc_register.base_entity_id AS base_entity_id\n" +
            "             FROM ec_anc_register\n" +
            "             UNION ALL\n" +
            "             SELECT ec_pregnancy_outcome.base_entity_id AS base_entity_id\n" +
            "             FROM ec_pregnancy_outcome\n" +
            "             UNION ALL\n" +
            "             SELECT ec_child.base_entity_id AS base_entity_id\n" +
            "             FROM ec_child\n" +
            "             UNION ALL\n" +
            "             SELECT ec_family_planning.base_entity_id AS base_entity_id\n" +
            "             FROM ec_family_planning\n" +
            "         )\n" +
            "         UNION ALL\n" +
            "/**COUNT FAMILY_PLANNING CLIENTS*/\n" +
            "         SELECT COUNT(*) AS c\n" +
            "         FROM ec_family_member\n" +
            "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
            "                  inner join ec_family_planning on ec_family_member.base_entity_id = ec_family_planning.base_entity_id\n" +
            "         where ec_family_member.date_removed is null\n" +
            "           AND ec_family_member.base_entity_id NOT IN (\n" +
            "             SELECT ec_anc_register.base_entity_id AS base_entity_id\n" +
            "             FROM ec_anc_register\n" +
            "             UNION ALL\n" +
            "             SELECT ec_pregnancy_outcome.base_entity_id AS base_entity_id\n" +
            "             FROM ec_pregnancy_outcome\n" +
            "             UNION ALL\n" +
            "             SELECT ec_child.base_entity_id AS base_entity_id\n" +
            "             FROM ec_child\n" +
            "             UNION ALL\n" +
            "             SELECT ec_malaria_confirmation.base_entity_id AS base_entity_id\n" +
            "             FROM ec_malaria_confirmation\n" +
            "         ));";


    public static String countDeathCertification = "SELECT COUNT(*) FROM (Select ec_family_member.id as _id , ec_family_member.relational_id as relationalid , " +
            "ec_family_member.last_interacted_with , ec_family_member.base_entity_id , '' as first_name , '' as middle_name , ec_family_member.first_name as family_first_name , " +
            "ec_family_member.last_name as family_last_name , ec_family_member.middle_name as family_middle_name , ec_family_member.phone_number as family_member_phone_number , " +
            "ec_family_member.other_phone_number as family_member_phone_number_other , ec_family.village_town as family_home_address ,'' as last_name, ec_family_member.unique_id, " +
            "ec_family_member.gender, ec_family_member.dob, ec_family_member.dob_unknown, '' as last_home_visit, '' as visit_not_done, '' as early_bf_1hr, " +
            "'' as physically_challenged, '' as birth_cert, '' as birth_cert_issue_date, '' as birth_cert_num, '' as birth_notification, '' as date_of_illness, " +
            "'' as illness_description, '' as date_created, '' as action_taken, '' as vaccine_card, '' as preg_outcome, ec_family_member.received_death_certificate, " +
            "ec_family_member.death_certificate_issue_date, ec_family_member.death_notification_done, ec_family_member.death_certificate_number, ec_family_member.official_id, " +
            "ec_family_member.official_name, ec_family_member.official_position, ec_family_member.official_address, ec_family_member.official_number, ec_family_member.informant_name, " +
            "ec_family_member.informant_relationship, ec_family_member.informant_address, ec_family_member.informant_phone, 'ec_family_member' as 'clientType' from ec_family_member " +
            "LEFT JOIN ec_family ON ec_family_member.base_entity_id = ec_family.primary_caregiver COLLATE NOCASE WHERE ec_family_member.is_closed = 1 AND ec_family_member.dod IS NOT NULL  " +
            "UNION Select ec_child.id as _id , ec_child.relational_id as relationalid , ec_child.last_interacted_with , ec_child.base_entity_id , ec_child.first_name , ec_child.middle_name , " +
            "ec_family_member.first_name as family_first_name , ec_family_member.last_name as family_last_name , ec_family_member.middle_name as family_middle_name , " +
            "ec_family_member.phone_number as family_member_phone_number , ec_family_member.other_phone_number as family_member_phone_number_other , ec_family.village_town as family_home_address , " +
            "ec_child.last_name , ec_child.unique_id , ec_child.gender , ec_child.dob , ec_child.dob_unknown , ec_child.last_home_visit , ec_child.visit_not_done , ec_child.early_bf_1hr , " +
            "ec_child.physically_challenged , ec_child.birth_cert , ec_child.birth_cert_issue_date , ec_child.birth_cert_num , ec_child.birth_notification , ec_child.date_of_illness , " +
            "ec_child.illness_description , ec_child.date_created , ec_child.action_taken , ec_child.vaccine_card, '' as preg_outcome, ec_child.received_death_certificate, " +
            "ec_child.death_certificate_issue_date, ec_child.death_notification_done, ec_child.death_certificate_number, ec_child.official_id , ec_child.official_name, ec_child.official_position, " +
            "ec_child.official_address, ec_child.official_number, ec_child.informant_name, ec_child.informant_relationship, ec_child.informant_address, ec_child.informant_phone, " +
            "'ec_child' as 'clientType' FROM ec_child LEFT JOIN ec_family ON  ec_child.relational_id = ec_family.id COLLATE NOCASE " +
            "LEFT JOIN ec_family_member ON  ec_family_member.base_entity_id = ec_family.primary_caregiver COLLATE NOCASE  " +
            "LEFT JOIN (select base_entity_id , max(visit_date) visit_date from visits GROUP by base_entity_id) VISIT_SUMMARY ON VISIT_SUMMARY.base_entity_id = ec_child.base_entity_id WHERE  " +
            "ec_child.is_closed is 1 and ec_child.dod IS NOT NULL UNION Select ec_pregnancy_outcome.id as _id, ec_pregnancy_outcome.relational_id as relationalid, ec_pregnancy_outcome.last_interacted_with, " +
            "ec_pregnancy_outcome.base_entity_id, '' as first_name, '' as middle_name, ec_family_member.first_name as family_first_name , ec_family_member.last_name as family_last_name , " +
            "ec_family_member.middle_name as family_middle_name , ec_family_member.phone_number as family_member_phone_number , ec_family_member.other_phone_number as family_member_phone_number_other , " +
            "ec_family.village_town as family_home_address ,'' as last_name, ec_family_member.unique_id, ec_family_member.gender, ec_family_member.dob, ec_family_member.dob_unknown, '' as last_home_visit, " +
            "'' as visit_not_done, '' as early_bf_1hr, '' as physically_challenged, '' as birth_cert, '' as birth_cert_issue_date, '' as birth_cert_num, '' as birth_notification, '' as date_of_illness, " +
            "'' as illness_description, '' as date_created, '' as action_taken, '' as vaccine_card, ec_pregnancy_outcome.preg_outcome, ec_family_member.received_death_certificate, " +
            "ec_family_member.death_certificate_issue_date, ec_family_member.death_notification_done, ec_family_member.death_certificate_number, ec_family_member.official_id , ec_family_member.official_name, " +
            "ec_family_member.official_position, ec_family_member.official_address, ec_family_member.official_number, ec_family_member.informant_name, ec_family_member.informant_relationship, " +
            "ec_family_member.informant_address, ec_family_member.informant_phone, 'ec_pregnancy_outcome' as 'clientType' from ec_pregnancy_outcome " +
            "LEFT JOIN ec_family_member ON ec_pregnancy_outcome.base_entity_id = ec_family_member.base_entity_id LEFT JOIN ec_family ON ec_pregnancy_outcome.relational_id = ec_family.id COLLATE NOCASE WHERE " +
            "ec_pregnancy_outcome.preg_outcome = 'Stillbirth' UNION Select ec_out_of_area_death.id as _id , ec_out_of_area_death.relationalid as relationalid , ec_out_of_area_death.last_interacted_with , " +
            "ec_out_of_area_death.base_entity_id , ec_out_of_area_death.name as first_name , '' as middle_name , '' as family_first_name , '' as family_last_name , '' as family_middle_name , " +
            "ec_out_of_area_death.official_number as family_member_phone_number , '' as family_member_phone_number_other , ec_out_of_area_death.death_place as family_home_address , '' as last_name, " +
            "ec_out_of_area_death.unique_id, ec_out_of_area_death.sex as gender, ec_out_of_area_death.dob, ec_out_of_area_death.dob_unknown, '' as last_home_visit, '' as visit_not_done, '' as early_bf_1hr, " +
            "'' as physically_challenged, '' as birth_cert, '' as birth_cert_issue_date, '' as birth_cert_num, '' as birth_notification, '' as date_of_illness, '' as illness_description, " +
            "ec_out_of_area_death.date_created, '' as action_taken, '' as vaccine_card, '' as preg_outcome, ec_out_of_area_death.received_death_certificate, ec_out_of_area_death.death_certificate_issue_date, " +
            "ec_out_of_area_death.death_notification_done, ec_out_of_area_death.death_certificate_number, ec_out_of_area_death.official_id , ec_out_of_area_death.official_name, ec_out_of_area_death.official_position, " +
            "ec_out_of_area_death.official_address, ec_out_of_area_death.official_number, ec_out_of_area_death.informant_name, ec_out_of_area_death.informant_relationship, ec_out_of_area_death.informant_address, " +
            "ec_out_of_area_death.informant_phone, 'ec_out_of_area_death' as 'clientType' from ec_out_of_area_death ORDER BY last_interacted_with DESC  ) AS cnt;";


    public static String countBirthCertification = "SELECT COUNT(*) FROM (Select ec_child.id as _id , ec_child.relational_id as relationalid , ec_child.last_interacted_with , ec_child.base_entity_id , ec_child.first_name , " +
            "ec_child.middle_name , ec_family_member.first_name as family_first_name , ec_family_member.last_name as family_last_name , ec_family_member.middle_name as family_middle_name , " +
            "ec_family_member.phone_number as family_member_phone_number , ec_family_member.other_phone_number as family_member_phone_number_other , ec_family.village_town as family_home_address , ec_child.last_name , " +
            "ec_child.unique_id , ec_child.gender , ec_child.dob , ec_child.dob_unknown , ec_child.last_home_visit , ec_child.visit_not_done , ec_child.early_bf_1hr , ec_child.physically_challenged , ec_child.birth_cert , " +
            "ec_child.birth_cert_issue_date , ec_child.birth_cert_num , ec_child.birth_notification , ec_child.birth_reg_type , ec_child.birth_registration , ec_child.system_birth_notification , ec_child.informant_reason , " +
            "ec_child.date_of_illness , ec_child.illness_description , ec_child.date_created , ec_child.action_taken , ec_child.vaccine_card , 'ec_child' as clientType FROM ec_child " +
            "LEFT JOIN ec_family ON  ec_child.relational_id = ec_family.id COLLATE NOCASE  LEFT JOIN ec_family_member ON  ec_family_member.base_entity_id = ec_family.primary_caregiver COLLATE NOCASE  " +
            "LEFT JOIN (select base_entity_id , max(visit_date) visit_date from visits GROUP by base_entity_id) VISIT_SUMMARY ON VISIT_SUMMARY.base_entity_id = ec_child.base_entity_id WHERE  " +
            "ec_child.date_removed is null AND  ((( julianday('now') - julianday(ec_child.dob))/365.25) <5)   " +
            "and (( ifnull(ec_child.entry_point,'') <> 'PNC' ) or (ifnull(ec_child.entry_point,'') = 'PNC' " +
            "and (((SELECT is_closed FROM ec_family_member WHERE base_entity_id = ec_child.mother_entity_id ) = 0)))  or (ifnull(ec_child.entry_point,'') = 'PNC'  " +
            "and (SELECT is_closed FROM ec_family_member WHERE base_entity_id = ec_child.mother_entity_id ) = 1))  and ((( julianday('now') - julianday(ec_child.dob))/365.25) < 5)   " +
            " UNION Select ec_out_of_area_child.id as _id , '' as relationalid , ec_out_of_area_child.last_interacted_with , ec_out_of_area_child.base_entity_id , ec_out_of_area_child.first_name , " +
            "ec_out_of_area_child.middle_name , ec_out_of_area_child.mother_name as family_first_name , '' as family_last_name , '' as family_middle_name , '' as family_member_phone_number , " +
            "'' as family_member_phone_number_other , '' as family_home_address , ec_out_of_area_child.middle_name as last_name , ec_out_of_area_child.unique_id , ec_out_of_area_child.gender , " +
            "ec_out_of_area_child.dob , ec_out_of_area_child.dob_unknown , '' as last_home_visit , '' as visit_not_done , '' as early_bf_1hr , '' as physically_challenged , ec_out_of_area_child.birth_cert , " +
            "ec_out_of_area_child.birth_cert_issue_date , ec_out_of_area_child.birth_cert_num , ec_out_of_area_child.birth_notification , ec_out_of_area_child.birth_reg_type , ec_out_of_area_child.birth_registration , " +
            "ec_out_of_area_child.system_birth_notification , ec_out_of_area_child.informant_reason , '' as date_of_illness , '' as illness_description , ec_out_of_area_child.date_created , " +
            "'' as action_taken , '' as vaccine_card , 'ec_out_of_area_child' as clientType FROM ec_out_of_area_child WHERE   ((( julianday('now') - julianday(ec_out_of_area_child.dob))/365.25) < 5)  ) AS cnt;";
}
