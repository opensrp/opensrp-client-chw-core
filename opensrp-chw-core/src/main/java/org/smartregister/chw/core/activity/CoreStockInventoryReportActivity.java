package org.smartregister.chw.core.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import org.joda.time.LocalDate;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.adapter.CoreStockMonthlyReportAdapter;
import org.smartregister.chw.core.adapter.CoreStockUsageItemAdapter;
import org.smartregister.chw.core.dao.StockUsageReportDao;
import org.smartregister.chw.core.model.MonthStockUsageModel;
import org.smartregister.chw.core.model.StockUsageItemModel;
import org.smartregister.chw.core.utils.StockUsageReportUtils;
import org.smartregister.view.activity.SecuredActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoreStockInventoryReportActivity extends SecuredActivity {
    private RecyclerView recyclerView;
    protected AppBarLayout appBarLayout;
    private  StockUsageReportUtils stockUsageReportUtils = new StockUsageReportUtils();
    private static List<String> getItems() {
        List<String> itemList = new ArrayList<>(
                Arrays.asList("ORS 5", "Zinc 10", "Paracetamol", "COC", "POP", "Male condom", "Female condom", "Standard day method", "Emergency contraceptive", "RDTs", "ALU 6", "ALU 12", "ALU 18", "ALU 24")
        );

        return itemList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_usage_report);

        Spinner spinner = findViewById(R.id.spinner);

        /*List<MonthStockUsageModel> monthStockUsageReportList = new ArrayList<>();

        monthStockUsageReportList.add(new MonthStockUsageModel("February", "2020"));
        monthStockUsageReportList.add(new MonthStockUsageModel("January", "2020"));
        monthStockUsageReportList.add(new MonthStockUsageModel("December", "2019"));
        monthStockUsageReportList.add(new MonthStockUsageModel("November", "2019"));
        monthStockUsageReportList.add(new MonthStockUsageModel("October", "2019"));

*/
        CoreStockMonthlyReportAdapter adapter = new CoreStockMonthlyReportAdapter(getMonthStockUsageReportList(), this);
        spinner.setAdapter(adapter);

        recyclerView = findViewById(R.id.rv_stock_usage_report);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MonthStockUsageModel selected = getMonthStockUsageReportList().get(position);
                reloadRecycler(selected);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<MonthStockUsageModel> getMonthStockUsageReportList() {
        List<MonthStockUsageModel> monthStockUsageReportList = new ArrayList<>();

        if (stockUsageReportUtils.previousMonths().size() > 0) {
            for (Map.Entry<Integer, Integer> entry : stockUsageReportUtils.previousMonths().entrySet()) {
                monthStockUsageReportList.add(new MonthStockUsageModel(stockUsageReportUtils.monthConverter(entry.getKey()), entry.getValue().toString()));
            }
        }
        return monthStockUsageReportList;
    }


    private List<StockUsageItemModel> getStockUsageItemReportList(String month, String year) {
      List<StockUsageItemModel> stockUsageItemModelsList = new ArrayList<>();
        StockUsageReportDao stockUsageReportDao = new StockUsageReportDao();
        for (String item : getItems()) {
            String usage = stockUsageReportDao.getStockUsageForMonth(month, item, year);
            if (!(" ").equals(usage)) {
                stockUsageItemModelsList.add(new StockUsageItemModel(stockUsageReportUtils.getFormattedItem(item), stockUsageReportUtils.getUnitOfMeasure(item), usage));
            } else {
                stockUsageItemModelsList.add(new StockUsageItemModel(stockUsageReportUtils.getFormattedItem(item), stockUsageReportUtils.getUnitOfMeasure(item), "0"));
            }
        }
        return stockUsageItemModelsList;
    }
    private void reloadRecycler(MonthStockUsageModel selected) {
        String stockMonth = stockUsageReportUtils.monthNumber(selected.getMonth().substring(0, 3));
        String stockYear = selected.getYear();
/*
        stockUsageItemModelsList.add(new StockUsageItemModel(
                "ORS 5",
                "Packets",
                "15"
        ));
        stockUsageItemModelsList.add(new StockUsageItemModel(
                "Zinc 10",
                "Tablets",
                "28"
        ));*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        CoreStockUsageItemAdapter coreStockUsageItemAdapter = new CoreStockUsageItemAdapter(getStockUsageItemReportList(stockMonth, stockYear), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(coreStockUsageItemAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    @Override
    protected void onCreation() {
        setContentView(R.layout.activity_stock_usage_report);
        Toolbar toolbar = findViewById(R.id.back_to_nav_toolbar);
        CustomFontTextView toolBarTextView = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
            upArrow.setColorFilter(getResources().getColor(R.color.text_blue), PorterDuff.Mode.SRC_ATOP);
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setElevation(0);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
        toolBarTextView.setText(getString(R.string.stock_usage_report));

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
