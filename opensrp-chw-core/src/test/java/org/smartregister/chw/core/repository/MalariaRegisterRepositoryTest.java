package org.smartregister.chw.core.repository;

import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smartregister.chw.anc.util.Constants;
import org.smartregister.chw.core.BaseRobolectricTest;

import java.util.HashMap;

public class MalariaRegisterRepositoryTest extends BaseRobolectricTest {

    private MalariaRegisterRepository malariaRegisterRepository;

    @Mock
    private SQLiteDatabase database;


    @Before
    public void setUp() throws Exception {
        malariaRegisterRepository = Mockito.spy(new MalariaRegisterRepository());
        Mockito.doReturn(database).when(malariaRegisterRepository).getReadableDatabase();
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
