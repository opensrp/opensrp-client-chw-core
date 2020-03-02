package org.smartregister.chw.core.model;

public class StockUsageItemDetailsModel {
    private String itemDetailsMonth;
    private String itemDetailsYear;
    private String itemDetailsStockCount;

    public StockUsageItemDetailsModel(String itemDetailsMonth, String itemDetailsYear, String itemDetailsStockUsage) {
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
    public String getItemDetailsStockUsage() {
        return itemDetailsStockCount;
    }
}
