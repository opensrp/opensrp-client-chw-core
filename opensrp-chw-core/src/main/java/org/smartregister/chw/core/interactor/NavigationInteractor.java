package org.smartregister.chw.core.interactor;

import org.smartregister.chw.core.contract.CoreApplication;
import org.smartregister.chw.core.contract.NavigationContract;
import org.smartregister.chw.core.dao.NavigationDao;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.family.util.AppExecutors;

import java.util.Date;

import timber.log.Timber;

public class NavigationInteractor implements NavigationContract.Interactor {

    private static NavigationInteractor instance;
    private AppExecutors appExecutors = new AppExecutors();
    private CoreApplication coreApplication;

    private NavigationInteractor() {

    }

    public static NavigationInteractor getInstance() {
        if (instance == null) {
            instance = new NavigationInteractor();
        }

        return instance;
    }

    @Override
    public Date getLastSync() {
        return null;
    }

    @Override
    public void getRegisterCount(final String tableName,
                                 final NavigationContract.InteractorCallback<Integer> callback) {
        if (callback != null) {
            appExecutors.diskIO().execute(() -> {
                try {
                    final Integer finalCount = getCount(tableName);
                    appExecutors.mainThread().execute(() -> callback.onResult(finalCount));
                } catch (final Exception e) {
                    appExecutors.mainThread().execute(() -> callback.onError(e));
                }
            });

        }
    }

    private int getCount(String tableName) {

        switch (tableName.toLowerCase().trim()) {
            case CoreConstants.TABLE_NAME.CHILD:
                String sqlChild = "select count(*) from ec_child c " +
                        "inner join ec_family_member m on c.base_entity_id = m.base_entity_id COLLATE NOCASE " +
                        "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
                        "where m.date_removed is null and m.is_closed = 0 " +
                        "and ((( julianday('now') - julianday(c.dob))/365.25) < 5) and c.is_closed = 0 " +
                        "and (( ifnull(entry_point,'') <> 'PNC' ) or (ifnull(entry_point,'') = 'PNC' and date(c.dob, '+28 days') <= date())) ";
                return NavigationDao.getQueryCount(sqlChild);

            case CoreConstants.TABLE_NAME.FAMILY:
                String sqlFamily = "select count(*) from ec_family where date_removed is null";
                return NavigationDao.getQueryCount(sqlFamily);

            case CoreConstants.TABLE_NAME.ANC_MEMBER:
                String sqlAncMember = "select count(*) " +
                        "from ec_anc_register r " +
                        "inner join ec_family_member m on r.base_entity_id = m.base_entity_id COLLATE NOCASE " +
                        "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
                        "where m.date_removed is null and m.is_closed = 0 and r.is_closed = 0 ";
                return NavigationDao.getQueryCount(sqlAncMember);

            case CoreConstants.TABLE_NAME.TASK:
                String sqlTask = "select count(*) from task inner join " +
                        "ec_family_member member on member.base_entity_id = task.for COLLATE NOCASE " +
                        "WHERE task.status =\"READY\" and member.date_removed is null ";
                return NavigationDao.getQueryCount(sqlTask);

            case CoreConstants.TABLE_NAME.ANC_PREGNANCY_OUTCOME:
                String sqlPregnancy = "select count(*) " +
                        "from ec_pregnancy_outcome p " +
                        "inner join ec_family_member m on p.base_entity_id = m.base_entity_id COLLATE NOCASE " +
                        "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
                        "where m.date_removed is null and p.delivery_date is not null and p.is_closed = 0 and m.is_closed = 0 ";
                return NavigationDao.getQueryCount(sqlPregnancy);

            case CoreConstants.TABLE_NAME.MALARIA_CONFIRMATION:
                String sqlMalaria = "select count(*) " +
                        "from ec_malaria_confirmation p " +
                        "inner join ec_family_member m on p.base_entity_id = m.base_entity_id COLLATE NOCASE " +
                        "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
                        "where m.date_removed is null and p.is_closed = 0 ";
                return NavigationDao.getQueryCount(sqlMalaria);

            case CoreConstants.TABLE_NAME.FAMILY_MEMBER:
                String allClients = "SELECT SUM(c)\n" +
                        "FROM (\n" +
                        "         SELECT COUNT(*) AS c\n" +
                        "         FROM ec_child\n" +
                        "                  inner join ec_family_member\n" +
                        "                             on ec_family_member.base_entity_id = ec_child.base_entity_id\n" +
                        "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
                        "         WHERE ec_family_member.is_closed = '0'\n" +
                        "           AND ec_family_member.date_removed is null\n" +
                        "           AND cast(strftime('%Y-%m-%d %H:%M:%S', 'now') -\n" +
                        "                    strftime('%Y-%m-%d %H:%M:%S', ec_child.dob) as int) > 0\n" +
                        "         UNION ALL\n" +
                        "         SELECT COUNT(*)\n" +
                        "         FROM ec_anc_register\n" +
                        "                  inner join ec_family_member\n" +
                        "                             on ec_family_member.base_entity_id = ec_anc_register.base_entity_id\n" +
                        "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
                        "         where ec_family_member.date_removed is null\n" +
                        "           and ec_anc_register.is_closed is 0\n" +
                        "\n" +
                        "         UNION ALL\n" +
                        "         SELECT COUNT(*)\n" +
                        "         FROM ec_pregnancy_outcome\n" +
                        "                  inner join ec_family_member\n" +
                        "                             on ec_family_member.base_entity_id = ec_pregnancy_outcome.base_entity_id\n" +
                        "                  inner join ec_family on ec_family.base_entity_id = ec_family_member.relational_id\n" +
                        "         where ec_family_member.date_removed is null\n" +
                        "           and ec_pregnancy_outcome.is_closed is 0\n" +
                        "           AND ec_pregnancy_outcome.base_entity_id NOT IN\n" +
                        "               (SELECT base_entity_id FROM ec_anc_register WHERE ec_anc_register.is_closed IS 0)\n" +
                        "         UNION ALL\n" +
                        "         SELECT COUNT(*)\n" +
                        "         FROM ec_family_member\n" +
                        "                  inner join ec_family\n" +
                        "                             on ec_family.base_entity_id = ec_family_member.relational_id\n" +
                        "         where ec_family_member.date_removed is null\n" +
                        "           AND ec_family_member.base_entity_id NOT IN (\n" +
                        "             SELECT ec_anc_register.base_entity_id AS base_entity_id\n" +
                        "             FROM ec_anc_register\n" +
                        "             UNION ALL\n" +
                        "             SELECT ec_pregnancy_outcome.base_entity_id AS base_entity_id\n" +
                        "             FROM ec_pregnancy_outcome\n" +
                        "             UNION ALL\n" +
                        "             SELECT ec_child.base_entity_id AS base_entity_id\n" +
                        "             FROM ec_child\n" +
                        "             UNION ALL\n" +
                        "             SELECT ec_malaria_confirmation.base_entity_id AS base_entity_id\n" +
                        "             FROM ec_malaria_confirmation\n" +
                        "         )\n" +
                        "         UNION ALL\n" +
                        "         SELECT COUNT(*)\n" +
                        "         FROM ec_family_member\n" +
                        "                  inner join ec_family\n" +
                        "                             on ec_family.base_entity_id = ec_family_member.relational_id\n" +
                        "                  inner join ec_malaria_confirmation\n" +
                        "                             on ec_family_member.base_entity_id = ec_malaria_confirmation.base_entity_id\n" +
                        "         where ec_family_member.date_removed is null\n" +
                        "           AND ec_family_member.base_entity_id NOT IN (\n" +
                        "             SELECT ec_anc_register.base_entity_id AS base_entity_id\n" +
                        "             FROM ec_anc_register\n" +
                        "             UNION ALL\n" +
                        "             SELECT ec_pregnancy_outcome.base_entity_id AS base_entity_id\n" +
                        "             FROM ec_pregnancy_outcome\n" +
                        "             UNION ALL\n" +
                        "             SELECT ec_child.base_entity_id AS base_entity_id\n" +
                        "             FROM ec_child\n" +
                        "         )\n" +
                        "     )\n";
                return NavigationDao.getQueryCount(allClients);

            default:
                return NavigationDao.getTableCount(tableName);
        }
    }

    @Override
    public Date sync() {
        Date res = null;
        try {
            res = new Date(getLastCheckTimeStamp());
        } catch (Exception e) {
            Timber.e(e.toString());
        }
        return res;
    }

    @Override
    public void setApplication(CoreApplication coreApplication) {
        this.coreApplication = coreApplication;
    }

    private Long getLastCheckTimeStamp() {
        return coreApplication.getEcSyncHelper().getLastCheckTimeStamp();
    }
}