package org.smartregister.chw.core.utils;

import android.os.Build;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.TestApplication;

import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, sdk = Build.VERSION_CODES.P)
public class StockUsageReportUtilsTest {

    private StockUsageReportUtils stockUsageReportUtils = new StockUsageReportUtils();
    private LocalDate testDate = new LocalDate();
    private Map<String, String> monthsAndYearsMap = new LinkedHashMap<>();

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPreviousMonths() {
        for (int i = 0; i < 12; i++) {
            String month = stockUsageReportUtils.monthConverter(testDate.minusMonths(i).getMonthOfYear(), context);
            String year = String.valueOf(testDate.minusMonths(i).getYear());
            monthsAndYearsMap.put(month, year);
        }
        Assert.assertEquals(stockUsageReportUtils.getPreviousMonths(this), monthsAndYearsMap);
    }

    @Test
    public void testGetMonthNumber() {
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("January".substring(0, 3)), "01");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("February".substring(0, 3)), "02");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("March".substring(0, 3)), "03");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("April".substring(0, 3)), "04");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("May".substring(0, 3)), "05");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("June".substring(0, 3)), "06");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("July".substring(0, 3)), "07");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("August".substring(0, 3)), "08");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("September".substring(0, 3)), "09");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("October".substring(0, 3)), "10");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("November".substring(0, 3)), "11");
        Assert.assertEquals(stockUsageReportUtils.getMonthNumber("December".substring(0, 3)), "12");
    }

    @Test
    public void testGetFormattedItem() {
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Panadol", this), "Paracetamol");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ORS 5", this), "ORS 5");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Zinc 10", this), "Zinc 10");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ALU 6", this), "ALU 6");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ALU 12", this), "ALU 12");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ALU 24", this), "ALU 24");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ALU 18", this), "ALU 18");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("COC", this), "COC");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("POP", this), "POP");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Emergency contraceptive", this), "Emergency contraceptive");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("RDTs", this), "RDTs");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Male condom", this), "Male Condoms");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Female condom", this), "Female Condoms");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Standard day method", this), "Cycle beads (Standard day method)");

    }

    @Test
    public void testGetUnitOfMeasure() {
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("ORS 5", this), "Packets");
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("Zinc 10", this), "Tablets");
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("COC", this), "Packs");
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("RDTs", this), "Tests");
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("Male condom", this), "Pieces");
    }

    @Test
    public void testMonthConverter() {
        Assert.assertEquals(stockUsageReportUtils.monthConverter(1, context), "January");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(2, context), "February");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(3, context), "March");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(4, context), "April");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(5, context), "May");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(6, context), "June");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(7, context), "July");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(8, context), "August");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(9, context), "September");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(10, context), "October");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(11, context), "November");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(12, context), "December");
    }
}