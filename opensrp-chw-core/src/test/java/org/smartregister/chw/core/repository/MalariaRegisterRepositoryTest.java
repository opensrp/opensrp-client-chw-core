package org.smartregister.chw.core.repository;

import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smartregister.chw.core.BaseRobolectricTest;

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

    }
}
