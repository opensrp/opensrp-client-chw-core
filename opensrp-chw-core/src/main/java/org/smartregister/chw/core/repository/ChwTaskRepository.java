package org.smartregister.chw.core.repository;

import net.sqlcipher.Cursor;

import org.smartregister.cloudant.models.Event;
import org.smartregister.domain.Task;
import org.smartregister.repository.BaseRepository;
import org.smartregister.repository.EventClientRepository;
import org.smartregister.repository.TaskNotesRepository;
import org.smartregister.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ChwTaskRepository extends TaskRepository {

    public ChwTaskRepository(TaskNotesRepository taskNotesRepository) {
        super(taskNotesRepository);
    }

    public List<Task> getTasksWithClientsAndEvents() {
        List<Task> tasks = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().rawQuery(String.format(
                "SELECT * FROM %s " +
                        " WHERE %s NOT IN " +
                        " (SELECT %s FROM %s " +
                        " INNER JOIN %s ON %s.%s = %s.%s " +
                        " INNER JOIN %s ON %s.%s = %s.%s " +
                        " WHERE %s =? OR %s IS NULL) AND %s <> %S AND %s IS NOT NULL "
                , "task", "_id", "_id", "task", "ec_family_member", "ec_family_member", "base_entity_id",
                "task", "for", EventClientRepository.Table.event.name(), EventClientRepository.Table.event.name(), Event.form_submission_id_key,
                "task", "reason_reference", "sync_status", "server_version","status","'COMPLETED'","reason_reference"), new String[]{BaseRepository.TYPE_Created})) {
            while (cursor.moveToNext()) {
                tasks.add(readCursor(cursor));
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return tasks;
    }

}
