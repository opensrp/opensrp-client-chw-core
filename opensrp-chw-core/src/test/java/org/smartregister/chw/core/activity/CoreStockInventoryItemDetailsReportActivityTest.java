package org.smartregister.chw.core.activity;

import android.view.Menu;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.adapter.CoreStockUsageItemDetailsAdapter;
import org.smartregister.chw.core.model.StockUsageItemDetailsModel;

import java.util.List;

import static org.junit.Assert.*;

public class CoreStockInventoryItemDetailsReportActivityTest extends BaseUnitTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private Menu menu;
    @Mock
    RecyclerView recyclerView;
    private CoreStockInventoryItemDetailsReportActivity activity;
    private List<StockUsageItemDetailsModel> stockUsageItemDetailsModelList;
    private String stockName;
    private String stockMonth;
    private String stockYear;
    private String stockUsage;
    private String providerName;

    @Before
    public void setUp() {
        ActivityController<CoreStockInventoryItemDetailsReportActivity> controller;

        MockitoAnnotations.initMocks(this);
        Context context = Context.getInstance();
        CoreLibrary.init(context);
        //SetUp
        stockName = "Male Condoms";
        stockMonth = "12";
        stockUsage = "20";
        stockYear = "2019";
        providerName = "All-CHWs";


        //Auto login by default
        String password = "pwd";
        context.session().start(context.session().lengthInMilliseconds());
        context.configuration().getDrishtiApplication().setPassword(password);
        context.session().setPassword(password);


        controller = Robolectric.buildActivity(CoreStockInventoryItemDetailsReportActivity.class).create().start();
        activity = controller.get();
    }

    @Test
    public void  testEvaluateStockName(){
        Assert.assertEquals("Male condom", activity.evaluateStockName(stockName));
    }

    @Test
    public void tedtOnCreateOptionsMenu() {
        Assert.assertFalse(activity.onCreateOptionsMenu(menu));

    }

    @Test
    public void testOnCreation() {
        // check if created views are found
        activity.onCreation();
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "toolBarTextView"));
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "appBarLayout"));


        stockUsageItemDetailsModelList.add(new StockUsageItemDetailsModel(stockMonth, stockYear, stockUsage));
        CoreStockUsageItemDetailsAdapter coreStockUsageItemDetailsAdapter = new CoreStockUsageItemDetailsAdapter(activity.stockUsageItemDetailsModelList(stockName, providerName));
        recyclerView.setAdapter(coreStockUsageItemDetailsAdapter);
    }

    @Test
    public void onResumption() {
    }
}