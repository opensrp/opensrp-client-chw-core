package org.smartregister.chw.core.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by cozej4 on 2020-03-23.
 *
 * @author cozej4 https://github.com/cozej4
 */
public class CoreReferralUtilsTest {

    @Test
    public void mainSelectTest() {
        Assert.assertEquals(CoreReferralUtils.mainSelect("test_tbl", "family_tbl", ""), "Select test_tbl.id as _id , test_tbl.relational_id as relationalid , test_tbl.last_interacted_with , test_tbl.base_entity_id , test_tbl.first_name , test_tbl.middle_name , test_tbl.last_name , family_tbl.village_town as family_home_address , family_tbl.primary_caregiver , family_tbl.family_head , test_tbl.unique_id , test_tbl.gender , test_tbl.dob , test_tbl.dob_unknown FROM test_tbl LEFT JOIN family_tbl ON  test_tbl.relational_id = family_tbl.id COLLATE NOCASE  WHERE test_tbl.base_entity_id = '' ");
    }

    @Test
    public void mainColumnsTest() {
        String tableName = "test_tbl";
        String familyTable = "family_tbl";
        String[] expectedColumns = {tableName + ".relational_id as relationalid", tableName + ".last_interacted_with", tableName + ".base_entity_id", tableName + ".first_name", tableName + ".middle_name", tableName + ".last_name", familyTable + ".village_town as family_home_address", familyTable + ".primary_caregiver", familyTable + ".family_head", tableName + ".unique_id", tableName + ".gender", tableName + ".dob", tableName + ".dob_unknown"};
        Assert.assertArrayEquals(expectedColumns, CoreReferralUtils.mainColumns(tableName, familyTable));
    }

    @Test
    public void mainCaregiverSelectTest(){

        Assert.assertEquals("Select test_tbl.id as _id , test_tbl.relational_id as relationalid , test_tbl.first_name as family_first_name , test_tbl.middle_name as family_last_name , test_tbl.last_name as family_middle_name , test_tbl.phone_number as family_member_phone_number , test_tbl.other_phone_number as family_member_phone_number_other FROM test_tbl WHERE test_tbl.base_entity_id = '' ",CoreReferralUtils.mainCareGiverSelect("test_tbl",""));
    }

    @Test
    public void mainAncDetailsSelectTest(){
        Assert.assertEquals("Select test_tbl.id as _id , test_tbl.relational_id as relationalid , test_tbl.last_menstrual_period , ec_anc_log.date_created , ec_family.village_town , test_tbl.confirmed_visits , test_tbl.last_home_visit FROM test_tbl LEFT JOIN ec_anc_log ON  test_tbl.base_entity_id = ec_anc_log.id COLLATE NOCASE  LEFT JOIN ec_family ON  test_tbl.relational_id = ec_family.base_entity_id WHERE test_tbl.base_entity_id = 'base_entity_id' ",CoreReferralUtils.mainAncDetailsSelect("test_tbl","base_entity_id"));
    }

}