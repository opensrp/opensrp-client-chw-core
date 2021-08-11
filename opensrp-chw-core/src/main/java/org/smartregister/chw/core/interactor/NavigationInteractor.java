package org.smartregister.chw.core.interactor;

import org.smartregister.chw.core.contract.CoreApplication;
import org.smartregister.chw.core.contract.NavigationContract;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.dao.NavigationDao;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.QueryUtils;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.chw.referral.util.Constants;
import org.smartregister.family.util.AppExecutors;

import java.util.Date;

import timber.log.Timber;

import static org.smartregister.chw.core.utils.QueryConstant.ANC_DANGER_SIGNS_OUTCOME_COUNT_QUERY;
import static org.smartregister.chw.core.utils.QueryConstant.FAMILY_PLANNING_UPDATE_COUNT_QUERY;
import static org.smartregister.chw.core.utils.QueryConstant.MALARIA_HF_FOLLOW_UP_COUNT_QUERY;
import static org.smartregister.chw.core.utils.QueryConstant.NOT_YET_DONE_REFERRAL_COUNT_QUERY;
import static org.smartregister.chw.core.utils.QueryConstant.PNC_DANGER_SIGNS_OUTCOME_COUNT_QUERY;
import static org.smartregister.chw.core.utils.QueryConstant.SICK_CHILD_FOLLOW_UP_COUNT_QUERY;

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

    private String getChildSqlString() {
        if (NavigationMenu.getChildNavigationCountString() == null) {
            return "select count(*) from ec_child c " +
                    "inner join ec_family_member m on c.base_entity_id = m.base_entity_id COLLATE NOCASE " +
                    "inner join ec_family f on f.base_entity_id = m.relational_id COLLATE NOCASE " +
                    "where m.date_removed is null and m.is_closed = 0 " +
                    "and ((( julianday('now') - julianday(c.dob))/365.25) < 5) and c.is_closed = 0 " +
                    " and (( ( ifnull(entry_point,'') <> 'PNC' ) ) or (ifnull(entry_point,'') = 'PNC' and ( date (c.dob, '+28 days') <= date() and ((SELECT is_closed FROM ec_family_member WHERE base_entity_id = mother_entity_id ) = 0)))  or (ifnull(entry_point,'') = 'PNC'  and (SELECT is_closed FROM ec_family_member WHERE base_entity_id = mother_entity_id ) = 1)) ";
        } else {
            return NavigationMenu.getChildNavigationCountString();
        }
    }

    private String getDeathCertificateSqlString() {
        if (NavigationMenu.getChildNavigationCountString() == null) {
            return "Select (SELECT count(*) from ec_family_member LEFT JOIN ec_family ON ec_family_member.base_entity_id = ec_family.primary_caregiver COLLATE NOCASE WHERE ec_family_member.is_closed = 1) + (Select count(*) FROM ec_child LEFT JOIN ec_family ON  ec_child.relational_id = ec_family.id COLLATE NOCASE LEFT JOIN ec_family_member ON  ec_family_member.base_entity_id = ec_family.primary_caregiver COLLATE NOCASE  LEFT JOIN (select base_entity_id , max(visit_date) visit_date from visits GROUP by base_entity_id) VISIT_SUMMARY ON VISIT_SUMMARY.base_entity_id = ec_child.base_entity_id WHERE  ec_child.is_closed is 1) + (Select count(*) from ec_pregnancy_outcome LEFT JOIN ec_family_member ON ec_pregnancy_outcome.base_entity_id = ec_family_member.base_entity_id LEFT JOIN ec_family ON  ec_pregnancy_outcome.relational_id = ec_family.id COLLATE NOCASE WHERE ec_pregnancy_outcome.preg_outcome = 'Stillbirth' ) + (Select count(*) from ec_out_of_area_death ) as sumcount";
        } else {
            return NavigationMenu.getChildNavigationCountString();
        }
    }

    private String getBirthSummarySize() {
        if (NavigationMenu.getChildNavigationCountString() == null) {
            return QueryUtils.countBirthSummary;
        } else {
            return NavigationMenu.getChildNavigationCountString();
        }
    }

    private String getOutOfAreaChildSize() {
        if (NavigationMenu.getChildNavigationCountString() == null) {
            return "Select count(*) from ec_out_of_area_child";
        } else {
            return NavigationMenu.getChildNavigationCountString();
        }
    }

    private String getOutOfAreaDeathSize() {
        if (NavigationMenu.getChildNavigationCountString() == null) {
            return "Select count(*) from ec_out_of_area_death";
        } else {
            return NavigationMenu.getChildNavigationCountString();
        }
    }

    private int getCount(String tableName) {
        switch (tableName.toLowerCase().trim()) {
            case CoreConstants.TABLE_NAME.CHILD:
                return NavigationDao.getQueryCount(getChildSqlString());

            case CoreConstants.TABLE_NAME.FAMILY:
                return NavigationDao.getQueryCount(sqlFamily());

            case CoreConstants.TABLE_NAME.ANC_MEMBER:
                return NavigationDao.getQueryCount(sqlAncMember());

            case CoreConstants.TABLE_NAME.TASK:
                return NavigationDao.getQueryCount(sqlTask());

            case CoreConstants.TABLE_NAME.ANC_PREGNANCY_OUTCOME:
                return NavigationDao.getQueryCount(QueryUtils.countEcPregnencyOutcome);

            case CoreConstants.TABLE_NAME.MALARIA_CONFIRMATION:
                return NavigationDao.getQueryCount(QueryUtils.countEcMalaria);

            case FamilyPlanningConstants.DBConstants.FAMILY_PLANNING_TABLE:
                String sqlFP = QueryUtils.countEcFamilyPlanning;
                return NavigationDao.getQueryCount(sqlFP);

            case CoreConstants.TABLE_NAME.FAMILY_MEMBER:
                String allClients = "/**COUNT REGISTERED CHILD CLIENTS*/\n" +
                        QueryUtils.countRegisteredChildClients;
                return NavigationDao.getQueryCount(allClients);

            case Constants.Tables.REFERRAL:
                String sqlReferral = QueryUtils.countReferral;
                return NavigationDao.getQueryCount(sqlReferral);

            case CoreConstants.TABLE_NAME.NOTIFICATION_UPDATE:
                return NavigationDao.getQueryCount(referralNotificationQuery());

            case CoreConstants.TABLE_NAME.BIRTH_CERTIFICATE:
                String birthCertification = getBirthSummarySize();
                return NavigationDao.getQueryCount(birthCertification);

            case CoreConstants.TABLE_NAME.DEATH_CERTIFICATE:
                String deathCertification = getDeathCertificateSqlString();
                return NavigationDao.getQueryCount(deathCertification);

            case CoreConstants.TABLE_NAME.OUT_OF_AREA_CHILD:
                String outOfAreaChild = getOutOfAreaChildSize();
                return NavigationDao.getQueryCount(outOfAreaChild);

            case CoreConstants.TABLE_NAME.OUT_OF_AREA_DEATH:
                String outOfAreaDeath = getOutOfAreaDeathSize();
                return NavigationDao.getQueryCount(outOfAreaDeath);

            default:
                return NavigationDao.getTableCount(tableName);
        }
    }

    private String sqlFamily() {
        return QueryUtils.countEcFamily;
    }

    private String sqlAncMember() {
        return QueryUtils.countAncMember;
    }

    private String sqlTask() {
        return String.format(QueryUtils.countTask, CoreConstants.BUSINESS_STATUS.REFERRED);
    }

    private String referralNotificationQuery() {
        String query = String.format("SELECT SUM(c) FROM (\n %s \nUNION ALL\n %s \nUNION ALL\n %s \nUNION ALL\n %s \nUNION ALL\n %s \nUNION ALL %s)",
                SICK_CHILD_FOLLOW_UP_COUNT_QUERY, ANC_DANGER_SIGNS_OUTCOME_COUNT_QUERY,
                PNC_DANGER_SIGNS_OUTCOME_COUNT_QUERY, FAMILY_PLANNING_UPDATE_COUNT_QUERY,
                MALARIA_HF_FOLLOW_UP_COUNT_QUERY, NOT_YET_DONE_REFERRAL_COUNT_QUERY);
        return query;
    }

    private Long getLastCheckTimeStamp() {
        return coreApplication.getEcSyncHelper().getLastCheckTimeStamp();
    }
}