package org.smartregister.chw.core.utils;

import org.joda.time.LocalDate;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.application.CoreChwApplication;

import java.util.LinkedHashMap;
import java.util.Map;

public class StockUsageReportUtils {
    public Map<Integer, Integer> previousMonths() {
        Map<Integer, Integer> monthsAndYearsList = new LinkedHashMap<>();

        for (int i = 1; i < 13; i++) {
            LocalDate prevDate = new LocalDate().minusMonths(i);
            int month = prevDate.getMonthOfYear();
            int year = prevDate.getYear();
            monthsAndYearsList.put(month, year);
        }
        return monthsAndYearsList;
    }

    public String monthNumber(String month) {
        String valMonth = "";
        switch (month) {
            case "Jan":
                valMonth = "01";
                break;
            case "Feb":
                valMonth = "02";
                break;
            case "Mar":
                valMonth = "03";
                break;
            case "Apr":
                valMonth = "04";
                break;
            case "May":
                valMonth = "05";
                break;
            case "Jun":
                valMonth = "06";
                break;
            case "Jul":
                valMonth = "07";
                break;
            case "Aug":
                valMonth = "08";
                break;
            case "Sep":
                valMonth = "09";
                break;
            case "Oct":
                valMonth = "10";
                break;
            case "Nov":
                valMonth = "11";
                break;
            case "Dec":
                valMonth = "12";
                break;
            default:
                break;
        }
        return valMonth;
    }


    public String getFormattedItem(String item) {
        String formattedItem = "";
        switch (item) {
            case "ORS 5":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.ors_5);
                break;
            case "Zinc 10":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.zinc_10);
                break;
            case "Paracetamol":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.paracetamol);
                break;
            case "ALU 6":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.alu_6);
                break;
            case "ALU 12":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.alu_12);
                break;
            case "ALU 18":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.alu_18);
                break;
            case "ALU 24":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.alu_24);
                break;
            case "COC":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.coc);
                break;
            case "POP":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.pop);
                break;
            case "Emergency contraceptive":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.emergency_contraceptive);
                break;
            case "RDTs":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.rdts);
                break;
            case "Male condom":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.male_condoms);
                break;
            case "Female condom":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.female_condoms);
                break;
            case "Standard day method":
                formattedItem = CoreChwApplication.getInstance().getString(R.string.cycle_beads);
                break;
            default:
                break;
        }
        return formattedItem;
    }

    public String getUnitOfMeasure(String item) {
        String unitOfMeasure = "";
        switch (item) {
            case "ORS 5":
                unitOfMeasure = CoreChwApplication.getInstance().getString(R.string.packets);
                break;
            case "Zinc 10":
            case "Paracetamol":
            case "ALU 6":
            case "ALU 12":
            case "ALU 18":
            case "ALU 24":
                unitOfMeasure = CoreChwApplication.getInstance().getString(R.string.tablets);
                break;
            case "COC":
            case "POP":
            case "Emergency contraceptive":
                unitOfMeasure = CoreChwApplication.getInstance().getString(R.string.packs);
                break;
            case "RDTs":
                unitOfMeasure = CoreChwApplication.getInstance().getString(R.string.tests);
                break;
            case "Male condom":
            case "Female condom":
            case "Standard day method":
                unitOfMeasure = CoreChwApplication.getInstance().getString(R.string.pieces);
                break;
            default:
                break;
        }
        return unitOfMeasure;
    }

    public String monthConverter(Integer value) {
        String formattedMonth;
        switch (value) {
            case 1:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.january);
                break;
            case 2:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.february);
                break;
            case 3:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.march);
                break;
            case 4:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.april);
                break;
            case 5:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.may);
                break;
            case 6:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.june);
                break;
            case 7:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.july);
                break;
            case 8:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.august);
                break;
            case 9:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.september);
                break;
            case 10:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.october);
                break;
            case 11:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.november);
                break;
            case 12:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.december);
                break;
            default:
                formattedMonth = CoreChwApplication.getInstance().getString(R.string.january);
                break;
        }
        return formattedMonth;
    }
}
