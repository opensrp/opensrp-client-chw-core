package org.smartregister.chw.core.dao;

import net.sqlcipher.MatrixCursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.anc.domain.VisitDetail;
import org.smartregister.repository.Repository;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VisitDaoTest extends VisitDao {

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
    public void testGetVisitSummary() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"base_entity_id", "visit_type", "visit_date"});
        matrixCursor.addRow(new Object[]{"1232", "Test", 12123});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getVisitSummary("123456");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void canQueryPregnancyRiskLevel() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"details", "max(visit_date)"});
        matrixCursor.addRow(new Object[]{"Low", "1588982400000"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getMemberPregnancyRiskLevel("base-ID-123456");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testQueryMUACValue() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"details", "max(visit_date)"});
        matrixCursor.addRow(new Object[]{"green", "1588982400000"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getMUACValue("123456");

        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetChildDateCreated() throws ParseException {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"date_created"});

        Date date = getDobDateFormat().parse(getDobDateFormat().format(new Date()));

        matrixCursor.addRow(new Object[]{getDobDateFormat().format(date)});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        long count = VisitDao.getChildDateCreated("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertEquals(count, date.getTime());
    }

    @Test
    public void testUndoChildVisitNotDone() {
        Mockito.doReturn(database).when(repository).getWritableDatabase();
        VisitDao.undoChildVisitNotDone("123456");
        Mockito.verify(database).rawExecSQL(Mockito.anyString());
    }

    @Test
    public void testMemberHasBirthCert() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"certificates"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        boolean count = VisitDao.memberHasBirthCert("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertTrue(count);
    }

    @Test
    public void testMemberHasVaccineCard() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"certificates"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        boolean count = VisitDao.memberHasVaccineCard("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertTrue(count);
    }

    @Test
    public void testMemberHasVisits() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"total"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        boolean count = VisitDao.memberHasVisits("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
        Assert.assertTrue(count);
    }

    @Test
    public void testGetUnprocessedVaccines() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"visit_key", "details"});
        matrixCursor.addRow(new Object[]{"asd", "2019-01-01"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getUnprocessedVaccines("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetUnprocessedServiceRecords() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"visit_key"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getUnprocessedServiceRecords("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetPNCVisitsMedicalHistory() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"visit_key"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getPNCVisitsMedicalHistory("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetPNCMedicalHistory() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"visit_key"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getPNCMedicalHistoryVisitDetails("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetMedicalHistory() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"visit_key"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getMedicalHistory("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetVisitHistoryShouldReturnListOfVisitDetails() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"visit_id", "visit_type", "parent_visit_id", "base_entity_id", "visit_date",
        "visit_json", "pre_processed", "form_submission_id", "processed", "updated_at", "created_at", "visit_group"});
        matrixCursor.addRow(new Object[]{"4", "test", "111", "222", "1615772099000", "{test_json}", "yes", "444", "yes", "1615772099000",
        "1615772099000", "12344557"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        Map<String, List<VisitDetail>> visitHistory = VisitDao.getVisitHistory("222", "test");
        Assert.assertNotNull(visitHistory);
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetVisitsByMemberID() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"visit_key"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getVisitsByMemberID("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetVisitsToDelete() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"visit_key"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getVisitsToDelete();
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetPncHealthFacilityVisits() {
        Mockito.doReturn(database).when(repository).getReadableDatabase();

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"visit_id"});
        matrixCursor.addRow(new Object[]{"4"});
        Mockito.doReturn(matrixCursor).when(database).rawQuery(Mockito.any(), Mockito.any());

        VisitDao.getPncHealthFacilityVisits("123456");
        Mockito.verify(database).rawQuery(Mockito.anyString(), Mockito.any());
    }

}
