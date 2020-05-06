package org.smartregister.chw.core.sync.intent;

import android.content.Intent;

import org.smartregister.chw.core.utils.ChwDBConstants;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.Utils;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.commonregistry.CommonRepository;

import java.util.Date;
import java.util.List;

import static org.smartregister.chw.core.utils.ChwDBConstants.TaskTable.START;
import static org.smartregister.chw.core.utils.CoreConstants.TASKS_FOCUS.ANC_DANGER_SIGNS;
import static org.smartregister.chw.core.utils.CoreConstants.TASKS_FOCUS.FP_SIDE_EFFECTS;
import static org.smartregister.chw.core.utils.CoreConstants.TASKS_FOCUS.PNC_DANGER_SIGNS;
import static org.smartregister.chw.core.utils.CoreConstants.TASKS_FOCUS.SICK_CHILD;
import static org.smartregister.chw.core.utils.CoreConstants.TASKS_FOCUS.SUSPECTED_MALARIA;
import static org.smartregister.chw.core.utils.CoreConstants.TASKS_FOCUS.SUSPECTED_TB;
import static org.smartregister.chw.referral.util.DBConstants.Key.REFERRAL_APPOINTMENT_DATE;

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
        List<CommonPersonObject> tasks = commonRepository.customQuery(
                String.format(
                        "SELECT * FROM %s LEFT JOIN %s ON %s.%s = %s.%s WHERE %s = ?  ORDER BY %s DESC",
                        CoreConstants.TABLE_NAME.TASK, CoreConstants.TABLE_NAME.REFERRAL, CoreConstants.TABLE_NAME.TASK, ChwDBConstants.TaskTable.FOR, CoreConstants.TABLE_NAME.REFERRAL, CommonRepository.ID_COLUMN,
                        ChwDBConstants.TaskTable.BUSINESS_STATUS, START),
                new String[]{CoreConstants.BUSINESS_STATUS.REFERRED}, CoreConstants.TABLE_NAME.TASK);

        for (CommonPersonObject task : tasks) {
            String appointmentDate = task.getColumnmaps().get(REFERRAL_APPOINTMENT_DATE);
            String startDate = task.getColumnmaps().get(START);
            String focus = task.getDetails().get(ChwDBConstants.TaskTable.FOCUS);
            int timeoutInDays = 0;

            if(focus!=null && startDate!=null) {
                Date initialDate = null;
                switch (focus) {
                    case ANC_DANGER_SIGNS:
                    case PNC_DANGER_SIGNS:
                        timeoutInDays = 1;
                        initialDate = new Date(Long.parseLong(startDate));
                        break;
                    case SICK_CHILD:
                    case SUSPECTED_MALARIA:
                    case FP_SIDE_EFFECTS:
                        timeoutInDays = 7;
                        initialDate = new Date(Long.parseLong(startDate));
                        break;
                    case SUSPECTED_TB:
                        timeoutInDays = 3;
                        initialDate = (appointmentDate != null && !appointmentDate.isEmpty()) ? new Date(Long.parseLong(appointmentDate)) : new Date(Long.parseLong(startDate));
                    default:
                        timeoutInDays = 7;
                        initialDate = (appointmentDate != null && !appointmentDate.isEmpty()) ? new Date(Long.parseLong(appointmentDate)) : new Date(Long.parseLong(startDate));
                        break;
                }

            }


        }

    }
}
