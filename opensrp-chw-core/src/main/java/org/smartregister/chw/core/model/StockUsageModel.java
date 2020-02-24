package org.smartregister.chw.core.model;

public class StockUsageModel {
    private String stockName;
    private String unitsOfMeasure;
    private int stockUsage;

    public StockUsageModel(String stockName, String unitsOfMeasure, int stockUsage) {
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

    public int getStockUsage() {
        return stockUsage;
    }
}
