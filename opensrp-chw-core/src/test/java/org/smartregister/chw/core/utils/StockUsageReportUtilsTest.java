package org.smartregister.chw.core.utils;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.TestCoreChwApplication;

import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestCoreChwApplication.class)
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
            String month = stockUsageReportUtils.monthConverter(testDate.minusMonths(i).getMonthOfYear());
            String year = String.valueOf(testDate.minusMonths(i).getYear());
            monthsAndYearsMap.put(month, year);
        }
        Assert.assertEquals(stockUsageReportUtils.getPreviousMonths(), monthsAndYearsMap);
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
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Panadol"), "Paracetamol");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ORS 5"), "ORS 5");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Zinc 10"), "Zinc 10");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ALU 6"), "ALU 6");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ALU 12"), "ALU 12");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ALU 24"), "ALU 24");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("ALU 18"), "ALU 18");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("COC"), "COC");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("POP"), "POP");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Emergency contraceptive"), "Emergency contraceptive");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("RDTs"), "RDTs");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Male condom"), "Male Condoms");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Female condom"), "Female Condoms");
        Assert.assertEquals(stockUsageReportUtils.getFormattedItem("Standard day method"), "Cycle beads (Standard day method)");

    }

    @Test
    public void testGetUnitOfMeasure() {
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("ORS 5"), "Packets");
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("Zinc 10"), "Tablets");
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("COC"), "Packs");
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("RDTs"), "Tests");
        Assert.assertEquals(stockUsageReportUtils.getUnitOfMeasure("Male condom"), "Pieces");
    }

    @Test
    public void testMonthConverter() {
        Assert.assertEquals(stockUsageReportUtils.monthConverter(1), "January");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(2), "February");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(3), "March");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(4), "April");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(5), "May");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(6), "June");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(7), "July");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(8), "August");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(9), "September");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(10), "October");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(11), "November");
        Assert.assertEquals(stockUsageReportUtils.monthConverter(12), "December");
    }
}