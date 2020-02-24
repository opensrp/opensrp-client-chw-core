package org.smartregister.chw.core.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.adapter.CoreStockMonthlyReportAdapter;
import org.smartregister.chw.core.adapter.CoreStockUsageRecyclerViewAdapter;
import org.smartregister.chw.core.model.MonthStockUsageModel;
import org.smartregister.chw.core.model.StockUsageModel;
import org.smartregister.view.activity.SecuredActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;

import java.util.ArrayList;
import java.util.List;

public class CoreStockInventoryReportActivity extends SecuredActivity {
   // CoreStockMonthlyReportAdapter adapter;
   private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_usage_report);

        Spinner spinner = findViewById(R.id.spinner);

        List<MonthStockUsageModel> monthStockUsageReport = new ArrayList<>();

        monthStockUsageReport.add(new MonthStockUsageModel("February", "2020"));
        monthStockUsageReport.add(new MonthStockUsageModel("January", "2020"));
        monthStockUsageReport.add(new MonthStockUsageModel("December", "2019"));
        monthStockUsageReport.add(new MonthStockUsageModel("November", "2019"));
        monthStockUsageReport.add(new MonthStockUsageModel("October", "2019"));


        CoreStockMonthlyReportAdapter adapter = new CoreStockMonthlyReportAdapter(monthStockUsageReport, this);
        spinner.setAdapter(adapter);

      recyclerView = findViewById(R.id.rv_stock_usage_report);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MonthStockUsageModel selected = monthStockUsageReport.get(position);
                reloadRecycler(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void reloadRecycler(MonthStockUsageModel selected){
        List<StockUsageModel> stockUsageModels = new ArrayList<>();
        stockUsageModels.add(new StockUsageModel(
                "ORS 5",
                "Packets",
                15
        ));
        stockUsageModels.add(new StockUsageModel(
                "Zinc 10",
                "Tablets",
                28
        ));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        CoreStockUsageRecyclerViewAdapter coreStockUsageRecyclerViewAdapter = new CoreStockUsageRecyclerViewAdapter(stockUsageModels);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(coreStockUsageRecyclerViewAdapter);
    }

    @Override
    protected void onCreation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        CustomFontTextView customFontTextView = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
            upArrow.setColorFilter(getResources().getColor(R.color.text_blue), PorterDuff.Mode.SRC_ATOP);
            actionBar.setHomeAsUpIndicator(upArrow);
        }
        //  customFontTextView.setText(getString(R.string.stock_usage_report));
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
