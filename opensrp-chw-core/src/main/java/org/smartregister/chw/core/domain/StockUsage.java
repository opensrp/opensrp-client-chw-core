package org.smartregister.chw.core.domain;

import java.util.Date;

public class StockUsage {
    private String id;
    private String stockName;
    private String stockUsage;
    private String year;
    private String month;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    private Date createdAt;
    private Date updatedAt;



    public void setId(String id) {
        this.id = id;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setStockUsage(String stockUsage) {
        this.stockUsage = stockUsage;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getId() {
        return id;
    }

    public String getStockName() {
        return stockName;
    }

    public String getStockUsage() {
        return stockUsage;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

}
