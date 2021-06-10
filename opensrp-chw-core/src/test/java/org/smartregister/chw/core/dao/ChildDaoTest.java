package org.smartregister.chw.core.dao;

import net.sqlcipher.MatrixCursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.smartregister.chw.core.domain.Child;
import org.smartregister.repository.Repository;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ChildDaoTest extends ChildDao {

    @Mock
    private Repository repository;

    @Mock
    private SQLiteDatabase database;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setRepository(repository);
    }

    @Test
    public void testGetFamilyChildren() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id"});
        matrixCursor.addRow(new Object[]{"12345"});
        matrixCursor.addRow(new Object[]{"23456"});

        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        List<Child> children = ChildDao.getFamilyChildren("12345");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertEquals(children.size(), 2);
    }

    @Test
    public void testGetFamilyChildrenReturnsNewArrayList() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id"});

        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        List<Child> children = ChildDao.getFamilyChildren("12345");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertEquals(children.size(), 0);
    }

    @Test
    public void testGetChild() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id"});
        matrixCursor.addRow(new Object[]{"12345"});

        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        Child child = ChildDao.getChild("12345");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertEquals(child.getBaseEntityID(), "12345");
    }

    @Test
    public void testGetChildReturnsNull() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id"});

        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        Child child = ChildDao.getChild("12345");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertNull(child);
    }


    @Test
    public void testIsChild() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"count"});
        matrixCursor.addRow(new Object[]{2});

        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        Boolean dueVaccines = ChildDao.isChild("12345");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertEquals(dueVaccines, true);
    }

    @Test
    public void testIsChildReturnsFalse() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();
        boolean status = ChildDao.isChild("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertFalse(status);
    }


    @Test
    public void testIsMotherAlive() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"is_closed"});
        matrixCursor.addRow(new Object[]{"0"});

        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        Boolean isAlive = ChildDao.isMotherAlive("12345");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertFalse(isAlive);
    }

    @Test
    public void testGetBaseEntityID() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id", "thinkmd_id"});
        matrixCursor.addRow(new Object[]{"12345", "45678"});

        Mockito.doReturn(matrixCursor).when(database).query(Mockito.eq("ec_child"), Mockito.any(String[].class)
                , Mockito.anyString(), Mockito.nullable(String[].class), Mockito.nullable(String.class)
                , Mockito.nullable(String.class), Mockito.nullable(String.class));

        String baseEntityID = ChildDao.getBaseEntityID("thinkmd_id", "45678");

        Mockito.verify(database).query(Mockito.eq("ec_child"), Mockito.any(String[].class)
                , Mockito.anyString(), Mockito.nullable(String[].class), Mockito.nullable(String.class)
                , Mockito.nullable(String.class), Mockito.nullable(String.class));

        Assert.assertEquals(baseEntityID, "12345");
    }

    @Test
    public void testQueryColumnWithEntityId() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id", "html_assessment"});
        matrixCursor.addRow(new Object[]{"1234", "assessment plan"});

        Mockito.doReturn(matrixCursor).when(database).query(Mockito.eq("ec_child"), Mockito.any(String[].class)
                , Mockito.anyString(), Mockito.nullable(String[].class), Mockito.nullable(String.class)
                , Mockito.nullable(String.class), Mockito.nullable(String.class));

        String htmlAssessment = ChildDao.getThinkMDCarePlan("1234", "html_assessment");

        Mockito.verify(database).query(Mockito.eq("ec_child"), Mockito.any(String[].class)
                , Mockito.anyString(), Mockito.nullable(String[].class), Mockito.nullable(String.class)
                , Mockito.nullable(String.class), Mockito.nullable(String.class));

        Assert.assertEquals(htmlAssessment, "assessment plan");
    }

    @Test
    public void testIsThinkMDCarePlanExist() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id", "thinkmd_id"});
        matrixCursor.addRow(new Object[]{"1234", "5678"});

        Mockito.doReturn(matrixCursor).when(database).query(Mockito.eq("ec_child"), Mockito.any(String[].class)
                , Mockito.anyString(), Mockito.nullable(String[].class), Mockito.nullable(String.class)
                , Mockito.nullable(String.class), Mockito.nullable(String.class));

        boolean thinkMDCarePlanExist = ChildDao.isThinkMDCarePlanExist("1234");

        Mockito.verify(database).query(Mockito.eq("ec_child"), Mockito.any(String[].class)
                , Mockito.anyString(), Mockito.nullable(String[].class), Mockito.nullable(String.class)
                , Mockito.nullable(String.class), Mockito.nullable(String.class));

        Assert.assertTrue(thinkMDCarePlanExist);
    }

    @Test
    public void testGetFamilyMembers() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id"});
        matrixCursor.addRow(new Object[]{"12345"});
        matrixCursor.addRow(new Object[]{"23456"});

        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        List<String> children = ChildDao.getFamilyMembers("12345");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertEquals(children.size(), 2);
    }

    @Test
    public void testGetFamilyMembersReturnsEmptyArrayList() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id"});

        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        List<String> children = ChildDao.getFamilyMembers("12345");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertEquals(children.size(), 0);
    }
}
