package org.smartregister.chw.core.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.CoreStockInventoryItemDetailsReportActivity;
import org.smartregister.chw.core.model.StockUsageItemModel;

import java.util.List;

public class CoreStockUsageItemAdapter extends RecyclerView.Adapter<CoreStockUsageItemAdapter.CoreStockUsageReportViewHolder> {
    protected LayoutInflater inflater;
    private List<StockUsageItemModel> stockUsageItemModelList;
    private Context context;
    public CoreStockUsageItemAdapter(List<StockUsageItemModel> stockUsageItemModelList, Context context) {
        this.stockUsageItemModelList = stockUsageItemModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CoreStockUsageItemAdapter.CoreStockUsageReportViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                       int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.stock_usage_report_item, parent, false);
        return new CoreStockUsageItemAdapter.CoreStockUsageReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CoreStockUsageReportViewHolder holder, int position) {
        StockUsageItemModel usageModelItem = stockUsageItemModelList.get(position);
        holder.stockName.setText(usageModelItem.getStockName());
        holder.stockUnitOfMeasure.setText(usageModelItem.getUnitsOfMeasure());
        holder.stockCount.setText(String.format("%s", usageModelItem.getStockValue()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockName = "stockName";
                String providerId = "providerName";
                Intent intent = new Intent(context, CoreStockInventoryItemDetailsReportActivity.class);
                intent.putExtra(stockName, usageModelItem.getStockName());
                intent.putExtra(providerId, usageModelItem.getProviderName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockUsageItemModelList.size();
    }

    public static class CoreStockUsageReportViewHolder extends RecyclerView.ViewHolder {
        private TextView stockName;
        private TextView stockUnitOfMeasure;
        private TextView stockCount;
        private ImageView goToDetails;
        private View view;

        private CoreStockUsageReportViewHolder(View v) {
            super(v);
            view = v;
            stockName = v.findViewById(R.id.stock_name);
            stockUnitOfMeasure = v.findViewById(R.id.stock_unit_of_measure);
            stockCount = v.findViewById(R.id.stock_count);
            goToDetails = v.findViewById(R.id.go_to_item_details_image_view);
        }

    }
}