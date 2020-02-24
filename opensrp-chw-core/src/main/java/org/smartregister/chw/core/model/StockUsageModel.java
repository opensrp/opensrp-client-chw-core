package org.smartregister.chw.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class Stock implements Parcelable {
    private String stockName;
    private String unitsOfMeasure;
    private Map<String,String> stockUsage;

    public void setStockUsage(Map<String, String> stockUsage) {
        this.stockUsage = stockUsage;
    }

    public Map<String, String> getStockUsage() {
        return stockUsage;
    }

    protected Stock(Parcel in) {
        stockName = in.readString();
        unitsOfMeasure = in.readString();
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stockName);
        dest.writeString(unitsOfMeasure);
    }
}
