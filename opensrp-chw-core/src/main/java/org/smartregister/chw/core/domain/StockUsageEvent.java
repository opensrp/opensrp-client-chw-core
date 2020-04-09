package org.smartregister.chw.core.domain;

import org.smartregister.domain.db.Event;

import java.util.List;

public class StockUsageEvent extends Event {
    private List<StockConsumption> usage_report;

    public List<StockConsumption> getUsage_report() {
        return usage_report;
    }

    public void setUsage_report(List<StockConsumption> usage_report) {
        this.usage_report = usage_report;
    }
}
