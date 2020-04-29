package org.smartregister.chw.core.sync.intent;

import android.content.Intent;

import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.repository.ChwTaskRepository;
import org.smartregister.domain.Task;
import org.smartregister.repository.TaskRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by cozej4 on 2020-02-08.
 *
 * @author cozej4 https://github.com/cozej4
 */
public class CloseExpiredReferralsIntentService extends ChwCoreSyncIntentService {

    private static final String TAG = CloseExpiredReferralsIntentService.class.getSimpleName();
    private final TaskRepository taskRepository;


    public CloseExpiredReferralsIntentService() {
        super(TAG);
        taskRepository = CoreChwApplication.getInstance().getTaskRepository();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<Task> readyTasks = ((ChwTaskRepository) taskRepository).getReadyTasks();
        for(Task task:readyTasks){
            Date refereddate;
            switch (task.getFocus()){
                case "Gender Based Violence Referral":
            }
        }

    }
}