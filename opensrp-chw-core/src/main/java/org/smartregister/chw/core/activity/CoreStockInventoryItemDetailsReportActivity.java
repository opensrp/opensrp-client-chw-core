package org.smartregister.chw.core.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.adapter.CoreStockUsageItemDetailsRecyclerViewAdapter;
import org.smartregister.chw.core.model.StockUsageItemDetailsModel;
import org.smartregister.view.activity.SecuredActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;

import java.util.ArrayList;
import java.util.List;

public class CoreStockInventoryItemDetailsReportActivity extends SecuredActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_usage_item_details);

        List<StockUsageItemDetailsModel> stockUsageItemDetailsModelList = new ArrayList<>();
        stockUsageItemDetailsModelList.add(new StockUsageItemDetailsModel(
                "January",
                "2020",
                15
        ));
        stockUsageItemDetailsModelList.add(new StockUsageItemDetailsModel(
                "December",
                "2020",
                28
        ));
        RecyclerView recyclerView = findViewById(R.id.rv_stock_usage_item_detail_report);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        CoreStockUsageItemDetailsRecyclerViewAdapter coreStockUsageItemDetailsRecyclerViewAdapter = new CoreStockUsageItemDetailsRecyclerViewAdapter(stockUsageItemDetailsModelList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(coreStockUsageItemDetailsRecyclerViewAdapter);
    }

    @Override
    protected void onCreation() {
        Toolbar toolbar = findViewById(R.id.skt_detail_toolbar);
        CustomFontTextView customFontTextView = findViewById(R.id.tv_skt_detail_toolbar_title);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
            upArrow.setColorFilter(getResources().getColor(R.color.text_blue), PorterDuff.Mode.SRC_ATOP);
            actionBar.setHomeAsUpIndicator(upArrow);
        }
        //customFontTextView.setText(getString(R.string.stock_usage_report));
    }

    @Override
    protected void onResumption() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //adapter.onRestoreInstanceState(savedInstanceState);
    }

}