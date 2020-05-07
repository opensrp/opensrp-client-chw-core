package org.smartregister.chw.core.sync.intent;

import android.content.Intent;

import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.utils.ChwDBConstants;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.Utils;
import org.smartregister.chw.referral.util.DBConstants;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.domain.Task;
import org.smartregister.family.FamilyLibrary;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.repository.TaskRepository;
import org.smartregister.sync.helper.ECSyncHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.smartregister.chw.core.utils.ChwDBConstants.TaskTable;
import static org.smartregister.chw.core.utils.CoreConstants.TASKS_FOCUS;

/**
 * Created by cozej4 on 2020-02-08.
 *
 * @author cozej4 https://github.com/cozej4
 */
public class CloseExpiredReferralsIntentService extends ChwCoreSyncIntentService {

    private static final String TAG = CloseExpiredReferralsIntentService.class.getSimpleName();
    private final CommonRepository commonRepository;
    private final TaskRepository taskRepository;
    private AllSharedPreferences sharedPreferences;
    private ECSyncHelper syncHelper;


    public CloseExpiredReferralsIntentService() {
        super(TAG);
        commonRepository = Utils.context().commonrepository("task");
        taskRepository = CoreLibrary.getInstance().context().getTaskRepository();
        sharedPreferences = Utils.getAllSharedPreferences();
        syncHelper = FamilyLibrary.getInstance().getEcSyncHelper();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<CommonPersonObject> tasks = commonRepository.customQuery(
                String.format(
                        "SELECT * FROM %s LEFT JOIN %s ON %s.%s = %s.%s WHERE %s = ?  ORDER BY %s DESC",
                        CoreConstants.TABLE_NAME.TASK, CoreConstants.TABLE_NAME.REFERRAL, CoreConstants.TABLE_NAME.TASK, ChwDBConstants.TaskTable.FOR, CoreConstants.TABLE_NAME.REFERRAL, CommonRepository.ID_COLUMN,
                        ChwDBConstants.TaskTable.BUSINESS_STATUS, TaskTable.START),
                new String[]{CoreConstants.BUSINESS_STATUS.REFERRED}, CoreConstants.TABLE_NAME.TASK);

        for (CommonPersonObject task : tasks) {
            String appointmentDate = task.getColumnmaps().get(DBConstants.Key.REFERRAL_APPOINTMENT_DATE);
            String startDate = task.getColumnmaps().get(TaskTable.START);
            String focus = task.getDetails().get(ChwDBConstants.TaskTable.FOCUS);
            if (focus != null && startDate != null) {
                Calendar expiredCalendar = Calendar.getInstance();
                if (focus.equals(TASKS_FOCUS.ANC_DANGER_SIGNS) || focus.equals(TASKS_FOCUS.PNC_DANGER_SIGNS)) {
                    expiredCalendar.setTimeInMillis(Long.parseLong(startDate));
                    expiredCalendar.add(Calendar.HOUR_OF_DAY, 24);
                    checkIfExpired(expiredCalendar, task);
                } else if (focus.equals(TASKS_FOCUS.SICK_CHILD) || focus.equals(TASKS_FOCUS.SUSPECTED_MALARIA) || focus.equals(TASKS_FOCUS.FP_SIDE_EFFECTS)) {
                    Calendar referralNotYetDoneCalendar = Calendar.getInstance();
                    referralNotYetDoneCalendar.setTimeInMillis(Long.parseLong(startDate));
                    referralNotYetDoneCalendar.add(Calendar.DAY_OF_MONTH, 3);

                    expiredCalendar.setTimeInMillis(Long.parseLong(startDate));
                    expiredCalendar.add(Calendar.DAY_OF_MONTH, 7);

                    if (Objects.requireNonNull(task.getColumnmaps().get(TaskTable.STATUS)).equals(Task.TaskStatus.READY.name())) {
                        checkIfNotYetDone(referralNotYetDoneCalendar, task);
                    } else {
                        checkIfExpired(expiredCalendar, task);
                    }
                } else if (focus.equals(TASKS_FOCUS.SUSPECTED_TB)) {
                    expiredCalendar.setTimeInMillis(Long.parseLong(appointmentDate));
                    expiredCalendar.add(Calendar.DAY_OF_MONTH, 3);

                    checkIfExpired(expiredCalendar, task);
                } else {
                    if (appointmentDate != null && !appointmentDate.isEmpty()) {
                        expiredCalendar.setTimeInMillis(Long.parseLong(appointmentDate));
                    } else {
                        expiredCalendar.setTimeInMillis(Long.parseLong(startDate));
                    }
                    expiredCalendar.add(Calendar.DAY_OF_MONTH, 7);

                    checkIfExpired(expiredCalendar, task);

                }
            }
        }
    }

    public void checkIfExpired(Calendar expiredCalendar, CommonPersonObject taskEvent) {
        if (Calendar.getInstance().getTime().after(expiredCalendar.getTime())) {
            //Implement expired referrals events
        }
    }

    public void checkIfNotYetDone(Calendar referralNotYetDoneCalendar, CommonPersonObject taskEvent) {
        if (Calendar.getInstance().getTime().after(referralNotYetDoneCalendar.getTime())) {
            //Implement expired referrals events
        }
    }

}
