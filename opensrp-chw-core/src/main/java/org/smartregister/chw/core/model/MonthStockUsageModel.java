package org.smartregister.chw.core.model;

import org.smartregister.chw.core.utils.expandable_recycler_view.ExpandableGroup;

import java.util.List;
import java.util.Map;

public class StockInventoryMonths extends ExpandableGroup<StockUsageModel> {
    private String month;
    private String year;
    private Map<String, String> stockReportsMonths;

    public StockInventoryMonths(String title, List<StockUsageModel> items) {
        super(title, items);
    }

    public Map<String, String> getStockReportsMonths() {
        return stockReportsMonths;
    }

    public void setStockReportsMonths(Map<String, String> stockReportsMonths) {
        this.stockReportsMonths = stockReportsMonths;
    }
}