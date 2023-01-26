package org.smartregister.chw.core.repository;

import static org.smartregister.repository.BaseRepository.COLLATE_NOCASE;

import net.sqlcipher.MatrixCursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smartregister.chw.anc.util.Constants;
import org.smartregister.chw.core.BaseRobolectricTest;

import java.util.HashMap;

public class MalariaRegisterRepositoryTest extends BaseRobolectricTest {
    @Mock
    private MalariaRegisterRepository malariaRegisterRepository;

    @Mock
    private SQLiteDatabase database;

    @Captor
    ArgumentCaptor<String> tableNameCaptor;
    @Captor
    ArgumentCaptor<String[]> tableColumnsCaptor;
    @Captor
    ArgumentCaptor<String> selectionCaptor;
    @Captor
    ArgumentCaptor<String[]> selectionArgsCaptor;
    @Captor
    ArgumentCaptor<String> groupByCaptor;
    @Captor
    ArgumentCaptor<String> havingCaptor;
    @Captor
    ArgumentCaptor<String> orderByCaptor;


    @Before
    public void setUp() throws Exception {
        malariaRegisterRepository = Mockito.spy(new MalariaRegisterRepository());
        Mockito.doReturn(database).when(malariaRegisterRepository).getReadableDatabase();
    }

    @Test
    public void testDatabaseQueryIsCreatedWithTheCorrectArgumentInGetFamilyNamePhone(){
        String baseEntityId = "4b3e6408-0549-470a-b24a-82ac71180a30";
        String selection = "base_entity_id = ? "+COLLATE_NOCASE;
        malariaRegisterRepository.getFamilyNameAndPhone(baseEntityId);
        Mockito.verify(database).query(tableNameCaptor.capture(), tableColumnsCaptor.capture(), selectionCaptor.capture(), selectionArgsCaptor.capture(),
                groupByCaptor.capture(), havingCaptor.capture(), orderByCaptor.capture());
        Assert.assertEquals(MalariaRegisterRepository.TABLE_NAME, tableNameCaptor.getValue());
        Assert.assertArrayEquals(MalariaRegisterRepository.TABLE_COLUMNS, tableColumnsCaptor.getValue());
        Assert.assertArrayEquals(new String[]{baseEntityId}, selectionArgsCaptor.getValue());
        Assert.assertEquals(selection, selectionCaptor.getValue());
    }



    @Test
    public void testGetFamilyNameAndPhoneWithBaseEntityIdReturnsCorrectDetails(){

        String correctBaseEntityId = "4b3e6408-0549-470a-b24a-82ac71180a30";

        HashMap<String, String> results = new HashMap<>();
        results.put(Constants.ANC_MEMBER_OBJECTS.FAMILY_HEAD_NAME, "Emilio Mwai Kibaki");
        results.put(Constants.ANC_MEMBER_OBJECTS.FAMILY_HEAD_PHONE, "0799938383");

        MatrixCursor mockCursor =  new MatrixCursor(new String[]
                {
                        MalariaRegisterRepository.FIRST_NAME,
                        MalariaRegisterRepository.LAST_NAME,
                        MalariaRegisterRepository.MIDDLE_NAME,
                        MalariaRegisterRepository.PHONE_NUMBER
                });

        mockCursor.newRow()
                .add("Emilio")
                .add("Kibaki")
                .add("Mwai")
                .add("0799938383");

        Mockito.doReturn(mockCursor).when(database).query(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        Assert.assertEquals(results, malariaRegisterRepository.getFamilyNameAndPhone(correctBaseEntityId));

    }

}
