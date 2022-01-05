package org.smartregister.chw.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoreFamilyMemberModelTest {

    private String firstName;
    private String lastName;
    private String baseEntityId;
    private String familyBaseEntityId;
    private String entityType;

    @Before
    public void setUp(){
        firstName = "AnotherFirst";
        lastName = "Another";
        baseEntityId = "2356789ijmwertyu";
        familyBaseEntityId = "n93894jmdp";
        entityType = "Other";
    }
    @Test
    public void getBaseEntityIdTest(){
        CoreFamilyMemberModel coreFamilyMemberModel = new CoreFamilyMemberModel(lastName, baseEntityId, familyBaseEntityId, entityType);
        Assert.assertEquals(coreFamilyMemberModel.getBaseEntityId(), baseEntityId);
    }
    @Test
    public void getEntityTypeTest() {
        CoreFamilyMemberModel coreFamilyMemberModel = new CoreFamilyMemberModel(lastName, baseEntityId, familyBaseEntityId, entityType);
        Assert.assertEquals(coreFamilyMemberModel.getEntityType(), entityType);
    }
    @Test
    public void getLastNameTest() {
        CoreFamilyMemberModel coreFamilyMemberModel = new CoreFamilyMemberModel(lastName, baseEntityId, familyBaseEntityId, entityType);
        Assert.assertEquals(coreFamilyMemberModel.getLastName(), lastName);
    }
    @Test
    public void getFamilyBaseEntityIdTest() {
        CoreFamilyMemberModel coreFamilyMemberModel = new CoreFamilyMemberModel(lastName, baseEntityId, familyBaseEntityId, entityType);
        Assert.assertEquals(coreFamilyMemberModel.getFamilyBaseEntityId(), familyBaseEntityId);
    }

    @Test
    public void testCoreFamilyMemberModelShouldReturnCorrectBaseEntityId() {
        CoreFamilyMemberModel coreFamilyMemberModel = new CoreFamilyMemberModel(lastName, baseEntityId, familyBaseEntityId, entityType);
        Assert.assertNotNull(coreFamilyMemberModel.getBaseEntityId());
    }

    @Test
    public void testFamilyBaseEntityId() {
        CoreFamilyMemberModel coreFamilyMemberModel = new CoreFamilyMemberModel(lastName, baseEntityId, familyBaseEntityId, entityType);
        Assert.assertNotNull(coreFamilyMemberModel.getFamilyBaseEntityId());
    }

    @Test
    public void testFirstName() {
        CoreFamilyMemberModel coreFamilyMemberModel = new CoreFamilyMemberModel(lastName, firstName, baseEntityId, familyBaseEntityId, entityType);
        Assert.assertNotNull(coreFamilyMemberModel.getFirstName());
    }

    @Test
    public void testLastName() {
        CoreFamilyMemberModel coreFamilyMemberModel = new CoreFamilyMemberModel(lastName, baseEntityId, familyBaseEntityId, entityType);
        Assert.assertNotNull(coreFamilyMemberModel.getLastName());
    }

    @Test
    public void testCompareFirstName() {
        CoreFamilyMemberModel coreFamilyMemberModel = new CoreFamilyMemberModel(lastName, firstName, baseEntityId, familyBaseEntityId, entityType);
        Assert.assertEquals("AnotherFirst", coreFamilyMemberModel.getFirstName());
    }
}
