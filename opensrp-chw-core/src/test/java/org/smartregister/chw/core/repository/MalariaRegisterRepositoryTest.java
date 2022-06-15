package org.smartregister.chw.core.repository;

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
        String selection = "base_entity_id = ?  COLLATE NOCASE";
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
        String nonExistentRecordBaseEntityId = "adbc49cc-f0f2-42c2-a53f-634983922eb0";

        HashMap<String, String> results = new HashMap<>();
        results.put(Constants.ANC_MEMBER_OBJECTS.FAMILY_HEAD_NAME, "Kibaki");
        results.put(Constants.ANC_MEMBER_OBJECTS.FAMILY_HEAD_PHONE, "9938383");

        Mockito.doReturn(results).when(malariaRegisterRepository).getFamilyNameAndPhone(correctBaseEntityId);

        Assert.assertEquals(results, malariaRegisterRepository.getFamilyNameAndPhone(correctBaseEntityId));
        Assert.assertNull(null, malariaRegisterRepository.getFamilyNameAndPhone(nonExistentRecordBaseEntityId));


    }
}
