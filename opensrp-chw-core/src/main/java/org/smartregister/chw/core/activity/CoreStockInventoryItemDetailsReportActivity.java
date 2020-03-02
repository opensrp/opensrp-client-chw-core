package org.smartregister.chw.core.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.adapter.CoreStockUsageItemDetailsAdapter;
import org.smartregister.chw.core.dao.StockUsageReportDao;
import org.smartregister.chw.core.model.StockUsageItemDetailsModel;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.StockUsageReportUtils;
import org.smartregister.view.activity.SecuredActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoreStockInventoryItemDetailsReportActivity extends SecuredActivity {
    protected AppBarLayout appBarLayout;
    private StockUsageReportUtils stockUsageReportUtils = new StockUsageReportUtils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_usage_item_details);
         String stockMonth;
         String stockYear;
         String stockUsage;
        String stockName;
        Intent intent = getIntent();
        stockName = intent.getStringExtra("stock Name");

        String stock_name;
        switch (stockName){
            case ("Male Condoms"):
                stock_name = "Male condom";
                break;
            case ("Female Condoms"):
                stock_name = "Female condom";
                break;
            case ("Cycle beads (Standard day method)"):
                stock_name = "Standard day method";
                break;
            default:
                stock_name = stockName;
                break;
        }
        StockUsageReportDao stockUsageReportDao = new StockUsageReportDao();
        List<StockUsageItemDetailsModel> stockUsageItemDetailsModelList = new ArrayList<>();
        if (stockUsageReportUtils.previousMonths().size() > 0) {
            for (Map.Entry<Integer, Integer> entry : stockUsageReportUtils.previousMonths().entrySet()) {
                stockMonth = stockUsageReportUtils.monthConverter(entry.getKey());
                stockYear = entry.getValue().toString();
                stockUsage = stockUsageReportDao.getStockUsageForMonth(stockUsageReportUtils.monthNumber(stockMonth.substring(0, 3)), stock_name, stockYear);
                if (!(" ").equals(stockUsage)) {
                    stockUsageItemDetailsModelList.add(new StockUsageItemDetailsModel(stockMonth, stockYear, stockUsage));
                } else {
                    stockUsageItemDetailsModelList.add(new StockUsageItemDetailsModel(stockMonth, stockYear, "0"));
                }
            }
        }
        TextView textViewName = findViewById(R.id.item_detail_name);
        textViewName.setText(String.format("%s Stock Used", stockName));

        RecyclerView recyclerView = findViewById(R.id.rv_stock_usage_item_detail_report);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        CoreStockUsageItemDetailsAdapter coreStockUsageItemDetailsAdapter = new CoreStockUsageItemDetailsAdapter(stockUsageItemDetailsModelList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(coreStockUsageItemDetailsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    @Override
    protected void onCreation() {
        setContentView(R.layout.activity_stock_usage_item_details);
        Toolbar toolbar = findViewById(R.id.back_stock_usage_toolbar);
        CustomFontTextView toolBarTextView = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
            upArrow.setColorFilter(getResources().getColor(R.color.text_blue), PorterDuff.Mode.SRC_ATOP);
            upArrow.setVisible(true, true);
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setElevation(0);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
        toolBarTextView.setOnClickListener(v -> finish());
        appBarLayout = findViewById(R.id.app_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setOutlineProvider(null);
        }
    }

    @Override
    protected void onResumption() {

    }
/*
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
*/
}