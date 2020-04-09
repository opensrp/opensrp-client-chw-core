package org.smartregister.chw.core.domain;

import com.google.gson.annotations.SerializedName;

import org.smartregister.domain.db.Event;

import java.util.List;

public class StockUsageEvent extends Event {

    @SerializedName("usage_report")
    private List<StockConsumption> usageReport;

    public List<StockConsumption> getUsageReport() {
        return usageReport;
    }

    public void setUsageReport(List<StockConsumption> usageReport) {
        this.usageReport = usageReport;
    }
}
