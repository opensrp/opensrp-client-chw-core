package org.smartregister.chw.core.dao;

import net.sqlcipher.MatrixCursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.model.CoreFamilyMemberModel;
import org.smartregister.repository.Repository;

import java.util.List;

public class FamilyMemberDaoTest extends FamilyMemberDao {

    @Mock
    private Repository repository;

    @Mock
    private SQLiteDatabase database;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setRepository(repository);
        Mockito.doReturn(database).when(repository).getReadableDatabase();
    }

    @Test
    public void testGetActiveAlerts() {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"last_name", "base_entity_id", "relational_id", "entity_type"});
        matrixCursor.addRow(new Object[]{"demo_last_name", "123456", "654321", "Update"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        List<CoreFamilyMemberModel> familyMembers = FamilyMemberDao.familyMembersToUpdateLastName("123456");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());

        Assert.assertNotNull(familyMembers);
        Assert.assertEquals(familyMembers.get(0).getLastName(), "demo_last_name");
    }

    @Test
    public void testGetMaleFamilyMembers() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"id", "relationalId", "details", "is_closed", "base_entity_id", "unique_id",
                "relational_id", "first_name", "middle_name", "last_name", "dob", "dod", "dob_unknown", "gender", "phone_number", "other_phone_number",
                "highest_edu_level", "national_id", "entity_type", "last_interacted_with", "date_removed", "marital_status"});
        matrixCursor.addRow(new Object[]{"1", "11334", "test_detail", "1", "1", "1", "11334", "f", "m", "l", "2019-01-01", "2019-01-01",
                "2019-01-01", "Male", "0304050", "040506", "test", "112233444", "ec_family_member", "12134213", "2019-01-01", "Single"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        List<CoreFamilyMemberModel> familyMembers = FamilyMemberDao.getMaleFamilyMembers("11334");
        Assert.assertNotNull(familyMembers);
        Assert.assertEquals("l", familyMembers.get(0).getLastName());
    }

    @Test
    public void testGetAliveOrDeadFamilyMembersShouldReturnListOfFamilyMembers() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"id", "relationalId", "details", "is_closed", "base_entity_id", "unique_id",
                "relational_id", "first_name", "middle_name", "last_name", "dob", "dod", "dob_unknown", "gender", "phone_number", "other_phone_number",
                "highest_edu_level", "national_id", "entity_type", "last_interacted_with", "date_removed", "marital_status"});
        matrixCursor.addRow(new Object[]{"1", "11334", "test_detail", "1", "1", "1", "11334", "f", "m", "l", "2019-01-01", "2019-01-01",
                "2019-01-01", "Male", "0304050", "040506", "test", "112233444", "ec_family_member", "12134213", "2019-01-01", "Single"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        List<CoreFamilyMemberModel> familyMembers = FamilyMemberDao.getAliveOrDeadFamilyMembers("1");
        Assert.assertNotNull(familyMembers);
        Assert.assertEquals("l", familyMembers.get(0).getLastName());
    }

}
