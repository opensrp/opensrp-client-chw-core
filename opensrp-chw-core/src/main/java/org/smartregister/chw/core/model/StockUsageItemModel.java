package org.smartregister.chw.core.model;

public class StockUsageItemModel {
    private String stockName;
    private String unitsOfMeasure;
    private String stockUsage;

    public StockUsageItemModel(String stockName, String unitsOfMeasure, String stockUsage) {
        this.stockName = stockName;
        this.unitsOfMeasure = unitsOfMeasure;
        this.stockUsage = stockUsage;
    }

    public String getStockName() {
        return stockName;
    }

    public String getUnitsOfMeasure() {
        return unitsOfMeasure;
    }

    public String getStockUsage() {
        return stockUsage;
    }
}
