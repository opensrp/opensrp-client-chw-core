package org.smartregister.chw.core.utils;

import android.util.Log;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.smartregister.child.util.JsonFormUtils;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.domain.Report;
import org.smartregister.chw.core.domain.ReportHia2Indicator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportUtils {
    private static final String TAG = ReportUtils.class.getCanonicalName();


    public static void createReport(List<ReportHia2Indicator> hia2Indicators, Date month, String reportType) {
        try {
            String providerId = CoreChwApplication.getInstance().getContext().allSharedPreferences().fetchRegisteredANM();
            String locationId = CoreChwApplication.getInstance().getContext().allSharedPreferences().getPreference(CoreConstants.CURRENT_LOCATION_ID);
            Report report = new Report();
            report.setFormSubmissionId(JsonFormUtils.generateRandomUUIDString());
            report.setHia2Indicators(hia2Indicators);
            report.setLocationId(locationId);
            report.setProviderId(providerId);

            // Get the second last day of the month
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(month);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 2);

            report.setReportDate(new DateTime(calendar.getTime()));
            report.setReportType(reportType);
            JSONObject reportJson = new JSONObject(JsonFormUtils.gson.toJson(report));
            try {
                CoreChwApplication.getInstance().hia2ReportRepository().addReport(reportJson);
            } catch (Exception e) {
                Log.e(TAG, "Exception", e);
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }
    }
}
