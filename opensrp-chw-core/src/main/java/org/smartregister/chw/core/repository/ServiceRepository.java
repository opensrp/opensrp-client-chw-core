package org.smartregister.chw.core.repository;

import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.chw.core.BuildConfig;
import org.smartregister.reporting.ReportingLibrary;
import org.smartregister.reporting.repository.DailyIndicatorCountRepository;
import org.smartregister.reporting.repository.IndicatorQueryRepository;
import org.smartregister.reporting.repository.IndicatorRepository;
import org.smartregister.repository.BaseRepository;
import org.smartregister.repository.EventClientRepository;
import org.smartregister.repository.Hia2ReportRepository;

public class ServiceRepository extends BaseRepository {
    private String indicatorsConfigFile = "config/indicator-definitions.yml";
    private String indicatorDataInitialisedPref = "INDICATOR_DATA_INITIALISED";
    private String appVersionCodePref = "APP_VERSION_CODE";


    public void onCreate(SQLiteDatabase database) {
        //reporting
        IndicatorRepository.createTable(database);
        IndicatorQueryRepository.createTable(database);
        DailyIndicatorCountRepository.createTable(database);
        MonthlyTalliesRepository.createTable(database);

        // initialize from yml file
        ReportingLibrary reportingLibraryInstance = ReportingLibrary.getInstance();

        // Check if indicator data initialised
        boolean indicatorDataInitialised = Boolean.parseBoolean(reportingLibraryInstance.getContext().allSharedPreferences().getPreference(indicatorDataInitialisedPref));
        boolean isUpdated = checkIfAppUpdated();
        if (!indicatorDataInitialised || isUpdated) {
            Log.d("REPO 2!", "initialising");
            reportingLibraryInstance.initIndicatorData(indicatorsConfigFile, database); // This will persist the data in the DB
            reportingLibraryInstance.getContext().allSharedPreferences().savePreference(indicatorDataInitialisedPref, "true");
            reportingLibraryInstance.getContext().allSharedPreferences().savePreference(appVersionCodePref, String.valueOf(BuildConfig.VERSION_CODE));
        }
        //hia2
        EventClientRepository.createTable(database, Hia2ReportRepository.Table.hia2_report, Hia2ReportRepository.report_column.values());

    }
    private boolean checkIfAppUpdated() {
        String savedAppVersion = ReportingLibrary.getInstance().getContext().allSharedPreferences().getPreference(appVersionCodePref);
        if (savedAppVersion.isEmpty()) {
            return true;
        } else {
            int savedVersion = Integer.parseInt(savedAppVersion);
            return (BuildConfig.VERSION_CODE > savedVersion);
        }
    }
}
