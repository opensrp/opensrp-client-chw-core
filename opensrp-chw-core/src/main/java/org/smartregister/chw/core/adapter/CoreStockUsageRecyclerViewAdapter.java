package org.smartregister.chw.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.model.StockUsageModel;

import java.util.List;

public class CoreStockUsageRecyclerViewAdapter extends RecyclerView.Adapter<CoreStockUsageRecyclerViewAdapter.CoreStockUsageReportViewHolder> {
    protected LayoutInflater inflater;
    private List<StockUsageModel> stockUsageModelList;


    public CoreStockUsageRecyclerViewAdapter(List<StockUsageModel> stockUsageModelList) {
        this.stockUsageModelList = stockUsageModelList;
    }

    @NonNull
    @Override
    public CoreStockUsageRecyclerViewAdapter.CoreStockUsageReportViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                               int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.stock_usage_report_item, parent, false);
        return new CoreStockUsageRecyclerViewAdapter.CoreStockUsageReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CoreStockUsageReportViewHolder holder, int position) {
        StockUsageModel usageModelItem = stockUsageModelList.get(position);
        holder.stockName.setText(usageModelItem.getStockName());
        holder.stockUnitOfMeasure.setText(usageModelItem.getUnitsOfMeasure());
        holder.stockCount.setText(String.format("%s", usageModelItem.getStockUsage()));
    }

    @Override
    public int getItemCount() {
        return stockUsageModelList.size();
    }

    public static class CoreStockUsageReportViewHolder extends RecyclerView.ViewHolder {
        private TextView stockName;
        private TextView stockUnitOfMeasure;
        private TextView stockCount;


        public CoreStockUsageReportViewHolder(View v) {
            super(v);
            stockName = v.findViewById(R.id.stock_name);
            stockUnitOfMeasure = v.findViewById(R.id.stock_unit_of_measure);
            stockCount = v.findViewById(R.id.stock_count);
        }
    }
}