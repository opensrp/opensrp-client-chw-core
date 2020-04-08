package org.smartregister.chw.core.model;

import org.smartregister.chw.core.contract.BaseReferralNotificationFragmentContract;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.cursoradapter.SmartRegisterQueryBuilder;
import org.smartregister.domain.Task;
import org.smartregister.family.util.DBConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseReferralNotificationModel implements BaseReferralNotificationFragmentContract.Model {

    @Override
    public String countQueryStatement(String taskTable, String mainCondition) {
        SmartRegisterQueryBuilder countQueryBuilder = new SmartRegisterQueryBuilder();
        countQueryBuilder.SelectInitiateMainTableCounts(taskTable);
        return countQueryBuilder.mainCondition(mainCondition);
    }

    @Override
    public String mainQueryStatement(String tableName, String entityTable, String mainCondition) {
        SmartRegisterQueryBuilder queryBuilder = new SmartRegisterQueryBuilder();
        queryBuilder.selectInitiateMainTable(tableName, mainColumns(tableName, entityTable), CoreConstants.DB_CONSTANTS.ID);
        queryBuilder.customJoin(String.format("INNER JOIN %s  ON  %s.%s = %s.%s AND %s.%s = '%s' COLLATE NOCASE ", entityTable, entityTable, DBConstants.KEY.BASE_ENTITY_ID, tableName, CoreConstants.DB_CONSTANTS.FOR, tableName, CoreConstants.DB_CONSTANTS.STATUS, Task.TaskStatus.COMPLETED.name()));
        queryBuilder.customJoin("LEFT JOIN ec_family  ON  ec_family_member.relational_id = ec_family.id COLLATE NOCASE");

        return queryBuilder.mainCondition(mainCondition);
    }

    protected String[] mainColumns(String tableName, String entityTable) {
        Set<String> columns =
                new HashSet<>(Arrays.asList(tableName + "." + CoreConstants.DB_CONSTANTS.FOCUS, tableName + "." +
                        CoreConstants.DB_CONSTANTS.REQUESTER, tableName + "." + CoreConstants.DB_CONSTANTS.START, tableName + "." + CoreConstants.DB_CONSTANTS.LAST_MODIFIED));
        addClientDetails(entityTable, columns);
        addTaskDetails(columns);
        return columns.toArray(new String[]{});
    }

    private void addClientDetails(String table, Set<String> columns) {
        columns.add(table + "." + "relational_id as relationalid");
        columns.add(table + "." + DBConstants.KEY.BASE_ENTITY_ID);
        columns.add(table + "." + DBConstants.KEY.FIRST_NAME);
        columns.add(table + "." + DBConstants.KEY.MIDDLE_NAME);
        columns.add(table + "." + DBConstants.KEY.LAST_NAME);
        columns.add(table + "." + DBConstants.KEY.DOB);
        columns.add(table + "." + DBConstants.KEY.GENDER);
        columns.add(CoreConstants.TABLE_NAME.FAMILY + "." + DBConstants.KEY.FAMILY_HEAD);

    }

    private void addTaskDetails(Set<String> columns) {
        columns.add(CoreConstants.TABLE_NAME.TASK + "." + CoreConstants.DB_CONSTANTS.FOCUS);
        columns.add(CoreConstants.TABLE_NAME.TASK + "." + CoreConstants.DB_CONSTANTS.OWNER);
        columns.add(CoreConstants.TABLE_NAME.TASK + "." + CoreConstants.DB_CONSTANTS.REQUESTER);
        columns.add(CoreConstants.TABLE_NAME.TASK + "." + CoreConstants.DB_CONSTANTS.START);
        columns.add(CoreConstants.TABLE_NAME.TASK + "." + CoreConstants.DB_CONSTANTS.LAST_MODIFIED);
    }
}

