package org.smartregister.chw.core.sync.intent;

import android.content.Intent;

import org.smartregister.chw.core.utils.ChwDBConstants;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.Utils;
import org.smartregister.commonregistry.CommonRepository;

/**
 * Created by cozej4 on 2020-02-08.
 *
 * @author cozej4 https://github.com/cozej4
 */
public class CloseExpiredReferralsIntentService extends ChwCoreSyncIntentService {

    private static final String TAG = CloseExpiredReferralsIntentService.class.getSimpleName();
    private final CommonRepository commonRepository;


    public CloseExpiredReferralsIntentService() {
        super(TAG);
        commonRepository = Utils.context().commonrepository("task");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        commonRepository.customQuery(
                String.format(
                        "SELECT * FROM %s LEFT JOIN %s ON %s.%s = %s.%s WHERE %s = ?  ORDER BY %s DESC",
                        CoreConstants.TABLE_NAME.TASK, CoreConstants.TABLE_NAME.REFERRAL, CoreConstants.TABLE_NAME.TASK, ChwDBConstants.TaskTable.REASON_REFERENCE, CoreConstants.TABLE_NAME.REFERRAL, CommonRepository.ID_COLUMN,
                        ChwDBConstants.TaskTable.BUSINESS_STATUS, ChwDBConstants.TaskTable.START),
                new String[]{CoreConstants.BUSINESS_STATUS.REFERRED});


    }
}