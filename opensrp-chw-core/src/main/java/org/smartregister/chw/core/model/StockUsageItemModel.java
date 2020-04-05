package org.smartregister.chw.core.model;

public class StockUsageItemModel {
    private String stockName;
    private String unitsOfMeasure;
    private String stockUsageValue;

    public StockUsageItemModel(String stockName, String unitsOfMeasure, String stockUsageValue) {
        this.stockName = stockName;
        this.unitsOfMeasure = unitsOfMeasure;
        this.stockUsageValue = stockUsageValue;
    }

    public String getStockName() {
        return stockName;
    }

    public String getUnitsOfMeasure() {
        return unitsOfMeasure;
    }

    public String getStockValue() {
        return stockUsageValue;
    }
}
