package org.smartregister.chw.core.utils;

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

}
