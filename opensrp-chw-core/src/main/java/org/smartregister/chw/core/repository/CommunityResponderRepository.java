package org.smartregister.chw.core.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.chw.core.activity.CoreCommunityRespondersRegisterActivity;
import org.smartregister.chw.core.adapter.CommunityResponderCustomAdapter;
import org.smartregister.chw.core.model.CommunityResponderModel;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.repository.BaseRepository;

import java.util.ArrayList;

import timber.log.Timber;

public class CommunityResponderRepository extends BaseRepository {
    public static final String TABLE_NAME = "community_responders";

    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + "(" +
            CoreConstants.JsonAssets.RESPONDER_ID + "  VARCHAR NOT NULL PRIMARY KEY, " +
            CoreConstants.JsonAssets.RESPONDER_NAME + "  VARCHAR, " +
            CoreConstants.JsonAssets.RESPONDER_PHONE_NUMBER + "  VARCHAR, " +
            CoreConstants.JsonAssets.RESPONDER_GPS + "  VARCHAR )";
    public static final String BASE_ID_INDEX = "CREATE UNIQUE INDEX " + TABLE_NAME + "_" + CoreConstants.JsonAssets.RESPONDER_ID + "_index ON " + TABLE_NAME + "(" + CoreConstants.JsonAssets.RESPONDER_ID + " COLLATE NOCASE " + ")";


    public static void createTable(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_SQL);
        database.execSQL(BASE_ID_INDEX);
    }

    public void addOrUpdate(CommunityResponderModel communityResponderModel) {
        addOrUpdateCommunityResponder(communityResponderModel, getWritableDatabase());
    }

    public void addOrUpdateCommunityResponder(CommunityResponderModel communityResponderModel, SQLiteDatabase database) {
        if (communityResponderModel == null) {
            return;
        }
        database.replace(TABLE_NAME, null, createValues(communityResponderModel));

    }

    private ContentValues createValues(CommunityResponderModel communityResponderModel) {
        ContentValues values = new ContentValues();

        values.put(CoreConstants.JsonAssets.RESPONDER_ID, communityResponderModel.getId());
        values.put(CoreConstants.JsonAssets.RESPONDER_NAME, communityResponderModel.getResponderName());
        values.put(CoreConstants.JsonAssets.RESPONDER_PHONE_NUMBER, communityResponderModel.getResponderPhoneNumber());
        values.put(CoreConstants.JsonAssets.RESPONDER_GPS, communityResponderModel.getResponderLocation());
        return values;
    }

    public CommunityResponderCustomAdapter readAllRespondersAdapter(Context context, CoreCommunityRespondersRegisterActivity activity) {
        String[] columns = {CoreConstants.JsonAssets.RESPONDER_ID, CoreConstants.JsonAssets.RESPONDER_NAME, CoreConstants.JsonAssets.RESPONDER_PHONE_NUMBER, CoreConstants.JsonAssets.RESPONDER_GPS};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, columns, null, null, null, null, null, null);
        try {
            ArrayList<CommunityResponderModel> communityResponderModels = new ArrayList<>();

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    communityResponderModels.add(new CommunityResponderModel(
                            cursor.getString(cursor.getColumnIndex(CoreConstants.JsonAssets.RESPONDER_NAME)),
                            cursor.getString(cursor.getColumnIndex(CoreConstants.JsonAssets.RESPONDER_PHONE_NUMBER)),
                            cursor.getString(cursor.getColumnIndex(CoreConstants.JsonAssets.RESPONDER_GPS)),
                            cursor.getString(cursor.getColumnIndex(CoreConstants.JsonAssets.RESPONDER_ID))));
                } while (cursor.moveToNext());
            }

            return new CommunityResponderCustomAdapter(communityResponderModels, context, activity);
        } catch (Exception e) {
            Timber.e(e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

}
