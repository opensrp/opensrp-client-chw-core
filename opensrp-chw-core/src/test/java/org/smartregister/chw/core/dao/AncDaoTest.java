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
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.repository.Repository;

@RunWith(MockitoJUnitRunner.class)
public class AncDaoTest extends AncDao {

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
    public void testGetAncDateCreated() {

        Mockito.doReturn(database).when(repository).getReadableDatabase();
        AncDao.getAncDateCreated("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testIsANCMember() {

        Mockito.doReturn(database).when(repository).getReadableDatabase();
        AncDao.isANCMember("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetMember() {

        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id"});
        matrixCursor.addRow(new Object[]{"12345"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(),Mockito.any());

        MemberObject memberObject = AncDao.getMember("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertEquals(memberObject.getBaseEntityId(), "12345");
    }

    @Test
    public void testGetAncWomenCount() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();
        AncDao.getAncWomenCount("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }
}
