package org.smartregister.chw.core.utils;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.TestCoreChwApplication;
import org.smartregister.chw.core.domain.Report;
import org.smartregister.chw.core.domain.ReportHia2Indicator;
import org.smartregister.util.JsonFormUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestCoreChwApplication.class)
public class ReportUtilsTest {
    @Mock
    private String providerId = "12345";
    @Mock
    private String locationId = "54321";

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateReport() throws JSONException {
        Date testDate = new DateTime().minusDays(10).toDate();
        String reportType = "monthlyReport";
        List<ReportHia2Indicator> hia2IndicatorsList = new ArrayList<>();
        hia2IndicatorsList.add(new ReportHia2Indicator());
        Calendar calendar = Calendar.getInstance();

        Report report = new Report();
        report.setFormSubmissionId(JsonFormUtils.generateRandomUUIDString());
        report.setHia2Indicators(hia2IndicatorsList);
        report.setLocationId(locationId);
        report.setProviderId(providerId);

        calendar.setTime(testDate);
        report.setReportDate(new DateTime(testDate));
        report.setReportType(reportType);

        JSONObject reportJson = new JSONObject(JsonFormUtils.gson.toJson(report));
        ReportUtils.createReport(hia2IndicatorsList, testDate, reportType);

        assertEquals(reportType, reportJson.getString("reportType"));
        assertEquals(1, reportJson.getJSONArray("hia2Indicators").length());
    }
}