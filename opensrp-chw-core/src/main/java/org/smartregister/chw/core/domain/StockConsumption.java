package org.smartregister.chw.core.domain;

import java.util.List;

public class StockConsumption {
    private String stockName;
    private List<StockConsumptionUsage> usages;

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public List<StockConsumptionUsage> getUsages() {
        return usages;
    }

    public void setUsages(List<StockConsumptionUsage> usages) {
        this.usages = usages;
    }
}
