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

import org.joda.time.LocalDate;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.adapter.CoreStockMonthlyReportAdapter;
import org.smartregister.chw.core.adapter.CoreStockUsageItemRecyclerViewAdapter;
import org.smartregister.chw.core.model.MonthStockUsageModel;
import org.smartregister.chw.core.model.StockUsageItemModel;
import org.smartregister.view.activity.SecuredActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoreStockInventoryReportActivity extends SecuredActivity {
    private RecyclerView recyclerView;
    private Map<Integer, Integer> monthsAndYearsList = new HashMap<>();
    private List<MonthStockUsageModel> monthStockUsageReportList = new ArrayList<>();
    private List<StockUsageItemModel> stockUsageItemModelsList = new ArrayList<>();
    private Drawable upArrow;

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
                MonthStockUsageModel selected = monthStockUsageReportList.get(position);
                reloadRecycler(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<MonthStockUsageModel> getMonthStockUsageReportList() {
        if (previousMonths().size() > 0) {
            for (Map.Entry<Integer, Integer> entry : previousMonths().entrySet()) {
                monthStockUsageReportList.add(new MonthStockUsageModel(monthConverter(entry.getKey()), entry.getValue().toString()));
            }
        }
        return monthStockUsageReportList;
    }

    private Map<Integer, Integer> previousMonths() {
        for (int i = 1; i < 13; i++) {
            LocalDate prevDate = new LocalDate().minusMonths(i);
            int month = prevDate.getMonthOfYear();
            int year = prevDate.getYear();
            monthsAndYearsList.put(month, year);
        }
        return monthsAndYearsList;
    }

    private String monthConverter(Integer value) {
        String formattedMonth = "";
        if (monthsAndYearsList.size() > 0) {
            for (Map.Entry<Integer, Integer> month : monthsAndYearsList.entrySet()) {
                switch (month.getKey()) {
                    case 1:
                        formattedMonth = this.getString(R.string.january);
                        break;
                    case 2:
                        formattedMonth = this.getString(R.string.february);
                        break;
                    case 3:
                        formattedMonth = this.getString(R.string.march);
                        break;
                    case 4:
                        formattedMonth = this.getString(R.string.april);
                        break;
                    case 5:
                        formattedMonth = this.getString(R.string.may);
                        break;
                    case 6:
                        formattedMonth = this.getString(R.string.june);
                        break;
                    case 7:
                        formattedMonth = this.getString(R.string.july);
                        break;
                    case 8:
                        formattedMonth = this.getString(R.string.august);
                        break;
                    case 9:
                        formattedMonth = this.getString(R.string.september);
                        break;
                    case 10:
                        formattedMonth = this.getString(R.string.october);
                        break;
                    case 11:
                        formattedMonth = this.getString(R.string.november);
                        break;
                    case 12:
                        formattedMonth = this.getString(R.string.december);
                        break;
                    default:
                        formattedMonth = this.getString(R.string.january);
                        break;
                }
            }
        }
        return formattedMonth;
    }

    private List<StockUsageItemModel> getStockUsageItemReportList() {
        String month;
        String year;
        if (previousMonths().size() > 0) {
            for (Map.Entry<Integer, Integer> entry : previousMonths().entrySet()) {
                if (entry.getKey().toString().length() == 1) {
                    StringBuilder sb = new StringBuilder();
                    month = (sb.append("0").append(entry.getKey().toString())).toString();
                    year = entry.getValue().toString();


                }
                //monthStockUsageReportList.add(new MonthStockUsageModel(monthConverter(entry.getKey()), entry.getValue().toString()));
            }
        }
       // get a list of the items for each month and year
        List<String>listItems;


        // get my list of items and check if it exists in the list
        // if not put 0
        // add units of measure
        //


        return stockUsageItemModelsList;
    }

    private String monthNumber(String month) {
        String valMonth = "";
        switch (month) {
            case "Jan":
                valMonth = "01";
                break;
            case "Feb":
                valMonth = "02";
                break;
            case "Mar":
                valMonth = "03";
                break;
            case "Apr":
                valMonth = "04";
                break;
            case "May":
                valMonth = "05";
                break;
            case "Jun":
                valMonth = "06";
                break;
            case "Jul":
                valMonth = "07";
                break;
            case "Aug":
                valMonth = "08";
                break;
            case "Sep":
                valMonth = "09";
                break;
            case "Oct":
                valMonth = "10";
                break;
            case "Nov":
                valMonth = "11";
                break;
            case "Dec":
                valMonth = "12";
            default:
                break;
        }
        return valMonth;
    }

    private String getUnitOfMeasure(String item) {
        String unitOfMeasure = "";
        switch (item) {
            case "ORS 5":
                unitOfMeasure = this.getString(R.string.packets);
                break;
            case "Zinc 10":
            case "Paracetamol":
            case "AL 6":
            case "AL 12":
            case "AL 18":
            case "AL 24":
                unitOfMeasure = this.getString(R.string.tablets);
                break;
            case "COC":
            case "POP":
            case "Emergency contraceptive":
                unitOfMeasure = this.getString(R.string.packs);
                break;
            case "RDTs":
                unitOfMeasure = this.getString(R.string.tests);
                break;
            case "male condom":
            case "female condom":
            case "Standard day method":
                unitOfMeasure = this.getString(R.string.pieces);
                break;
            default:
                break;
        }
        return unitOfMeasure;
    }

    private String getFormattedItem(String item) {
        String formattedItem = "";
        switch (item) {
            case "ORS 5":
                formattedItem = this.getString(R.string.ors_5);
                break;
            case "Zinc 10":
                formattedItem = this.getString(R.string.zinc_10);
                break;
            case "Paracetamol":
                formattedItem = this.getString(R.string.paracetamol);
                break;
            case "AL 6":
                formattedItem = this.getString(R.string.al_6);
                break;
            case "AL 12":
                formattedItem = this.getString(R.string.al_12);
                break;
            case "AL 18":
                formattedItem = this.getString(R.string.al_18);
                break;
            case "AL 24":
                formattedItem = this.getString(R.string.al_24);
                break;
            case "COC":
                formattedItem = this.getString(R.string.coc);
                break;
            case "POP":
                formattedItem = this.getString(R.string.pop);
                break;
            case "Emergency contraceptive":
                formattedItem = this.getString(R.string.emergency_contraceptive);
                break;
            case "RDTs":
                formattedItem = this.getString(R.string.rdts);
                break;
            case "male condom":
                formattedItem = this.getString(R.string.male_condom);
                break;
            case "female condom":
                formattedItem = this.getString(R.string.female_condom);
                break;
            case "Standard day method":
                    formattedItem = this.getString(R.string.cycle_beads);
                    break;
            default:
                break;
        }
        return formattedItem;
    }

    private static List<String>Items(){
        List<String> items = new ArrayList<>(
                Arrays.asList("ORS 5", "Zinc 10","Paracetamol","COC","POP","male condom","female condom","Standard day method","Emergency contraceptive","RDTs", "AL 6","AL 12","AL 18", "AL 24");
        );

        return  items;
    }

    private void reloadRecycler(MonthStockUsageModel selected) {
        String month = monthNumber(selected.getMonth().substring(0,3));
        String year = selected.getYear();


        stockUsageItemModelsList.add(new StockUsageItemModel(
                "ORS 5",
                "Packets",
                "15"
        ));
        stockUsageItemModelsList.add(new StockUsageItemModel(
                "Zinc 10",
                "Tablets",
                "28"
        ));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        CoreStockUsageItemRecyclerViewAdapter coreStockUsageItemRecyclerViewAdapter = new CoreStockUsageItemRecyclerViewAdapter(stockUsageItemModelsList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(coreStockUsageItemRecyclerViewAdapter);

    }

    @Override
    protected void onCreation() {
        Toolbar toolbar = findViewById(R.id.skt_toolbar);
        CustomFontTextView customFontTextView = findViewById(R.id.tv_skt_toolbar_title);
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
