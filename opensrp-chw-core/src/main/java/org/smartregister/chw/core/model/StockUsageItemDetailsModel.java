package org.smartregister.chw.core.model;

public class StockUsageItemDetailsModel {
    private String itemDetailsMonth;
    private String itemDetailsYear;
    private int itemDetailsStockCount;

    public StockUsageItemDetailsModel(String itemDetailsMonth, String itemDetailsYear, int itemDetailsStockUsage) {
        this.itemDetailsYear = itemDetailsYear;
        this.itemDetailsMonth = itemDetailsMonth;
        this.itemDetailsStockCount = itemDetailsStockUsage;
    }

    public String getItemDetailsMonth() {
        return itemDetailsMonth;
    }

    public String getItemDetailsYear() {
        return itemDetailsYear;
    }
    public int getItemDetailsStockUsage() {
        return itemDetailsStockCount;
    }
}
