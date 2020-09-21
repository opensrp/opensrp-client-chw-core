package org.smartregister.chw.core.repository;

import android.content.ContentValues;

import net.sqlcipher.MatrixCursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smartregister.chw.core.BaseRobolectricTest;
import org.smartregister.chw.core.model.CommunityResponderModel;
import org.smartregister.chw.core.utils.CoreConstants;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.smartregister.chw.core.repository.CommunityResponderRepository.BASE_ID_INDEX;
import static org.smartregister.chw.core.repository.CommunityResponderRepository.CREATE_TABLE_SQL;
import static org.smartregister.chw.core.repository.CommunityResponderRepository.TABLE_NAME;

public class CommunityResponderRepositoryTest extends BaseRobolectricTest {

    private CommunityResponderRepository repository;

    @Mock
    private SQLiteDatabase database;


    @Before
    public void setUp(){
        repository = Mockito.spy(new CommunityResponderRepository());
        Mockito.doReturn(database).when(repository).getReadableDatabase();
        Mockito.doReturn(database).when(repository).getWritableDatabase();
    }

    @Test
    public void canCreateCommunityRespondersTable() {
        CommunityResponderRepository.createTable(database);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(database, Mockito.times(2)).execSQL(captor.capture());
        List<String> createDbStatements = captor.getAllValues();
        Assert.assertEquals(CREATE_TABLE_SQL, createDbStatements.get(0));
        Assert.assertEquals(BASE_ID_INDEX, createDbStatements.get(1));
    }

    @Test
    public void canAddOrUpdateModels() {
        CommunityResponderModel model = Mockito.mock(CommunityResponderModel.class);
        repository.addOrUpdate(model);
        ArgumentCaptor<String> tableNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ContentValues> contentValuesCaptor = ArgumentCaptor.forClass(ContentValues.class);

        Mockito.verify(database, Mockito.times(1)).replace(tableNameCaptor.capture(), eq(null), contentValuesCaptor.capture());
        Assert.assertEquals(TABLE_NAME, tableNameCaptor.getValue());
    }

    @Test
    public void canGetAllResponders() {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{CoreConstants.JsonAssets.RESPONDER_ID, CoreConstants.JsonAssets.RESPONDER_NAME,
                CoreConstants.JsonAssets.RESPONDER_PHONE_NUMBER, CoreConstants.JsonAssets.RESPONDER_GPS});
        matrixCursor.addRow(new Object[]{"id-123", "James Bond", "078900111", "1.2000,-0.1000"});
        Mockito.doReturn(matrixCursor).when(database).query(Mockito.anyString(), Mockito.any(), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null));
        List<CommunityResponderModel> communityResponderModelList = repository.readAllResponders();
        Assert.assertEquals(1, communityResponderModelList.size());
        Assert.assertEquals("James Bond", communityResponderModelList.get(0).getResponderName());
    }

    @Test
    public void canGetCommunityResponderCustomAdapter() {

    }

    @Test
    public void canPurgeACommunityResponder() {
        repository.purgeCommunityResponder("test_entity_id");
        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(database, Mockito.times(1)).execSQL(queryCaptor.capture());
        Assert.assertEquals("DELETE FROM community_responders WHERE id ='test_entity_id'", queryCaptor.getValue());
    }

    @Test
    public void canGetRespondersCount() {

    }

}
