package org.smartregister.chw.core.dao;

import org.joda.time.DateTime;
import org.smartregister.dao.AbstractDao;
import org.smartregister.domain.Task;

import java.util.List;

public class ReferralTaskDao extends AbstractDao{
    /**
     * This method return a list of referral tasks that were marked as done on CHW which are suppose to
     * be dismissed (ended)
     * @return list of referral tasks to be ended
     */
    public static List<Task> getToBeEndedReferralTasks(){
        String queryStatement =
                "SELECT task.*\n" +
                "FROM task\n" +
                "         INNER JOIN ec_referral_dismissal on task._id = ec_referral_dismissal.referral_task\n" +
                "WHERE (Cast((JulianDay(ec_referral_dismissal.notification_dismissal_date) -\n" +
                "             JulianDay(date('now'))) As Integer) < 0\n" +
                "    OR ec_referral_dismissal.notification_dismissal_date is null)\n" +
                "  AND task.end is null\n" +
                "\n";
        return AbstractDao.readData(queryStatement, mapColumnValuesToTask());
    }

    private static AbstractDao.DataMap<Task> mapColumnValuesToTask() {
        return row -> {
            Task task = new Task();
            task.setIdentifier(getCursorValue(row, "_id"));
            task.setPlanIdentifier(getCursorValue(row, "plan_id"));
            task.setGroupIdentifier(getCursorValue(row, "group_id"));
            task.setStatus(Task.TaskStatus.valueOf(getCursorValue(row, "status")));
            task.setBusinessStatus(getCursorValue(row, "business_status"));
            task.setPriority(getCursorIntValue(row, "priority"));
            task.setCode(getCursorValue(row, "code"));
            task.setDescription(getCursorValue(row, "description"));
            task.setFocus(getCursorValue(row, "focus"));
            task.setForEntity(getCursorValue(row, "for"));
            task.setExecutionStartDate(new DateTime(getCursorValueAsDate(row, "start")));
            task.setExecutionEndDate(new DateTime(getCursorValueAsDate(row, "end")));
            task.setAuthoredOn(new DateTime(getCursorValueAsDate(row, "authored_on")));
            task.setLastModified(new DateTime(getCursorValueAsDate(row, "last_modified")));
            task.setOwner(getCursorValue(row, "owner"));
            task.setSyncStatus(getCursorValue(row, "sync_status"));
            task.setServerVersion(getCursorLongValue(row, "server_version"));
            task.setStructureId(getCursorValue(row, "structure_id"));
            task.setReasonReference(getCursorValue(row, "reason_reference"));
            task.setLocation(getCursorValue(row, "location"));
            task.setRequester(getCursorValue(row, "requester"));
            return task;
        };
    }
}
