package org.smartregister.chw.core.activity;

import android.view.Menu;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.domain.StockUsage;
import org.smartregister.chw.core.model.StockUsageItemModel;

import java.util.ArrayList;
import java.util.List;

public class CoreStockInventoryReportActivityTest extends BaseUnitTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    private CoreStockInventoryReportActivity activity;
    private ActivityController<CoreStockInventoryReportActivity> controller;
    private List<StockUsageItemModel> stockUsageItemModelsList = new ArrayList<>();
    @Mock
    private Menu menu;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Context context = Context.getInstance();
        CoreLibrary.init(context);

        //Auto login by default
        String password = "pwd";
        context.session().start(context.session().lengthInMilliseconds());
        context.configuration().getDrishtiApplication().setPassword(password);
        context.session().setPassword(password);

        controller = Robolectric.buildActivity(CoreStockInventoryReportActivity.class).create().start();
        activity = controller.get();
    }

    @Test
    public void testGetItems() {
        int size = CoreStockInventoryReportActivity.getItems().size();
        Assert.assertEquals(14, size);
    }

    @Test
    public void testGetStockUsageItemReportList() {
        String stockName = "COC";
        String unitsOfMeasure = "Packets";
        String month = "12";
        String year = "2019";
        StockUsage stockUsage = new StockUsage();
        stockUsage.setProviderId("chwone");
        List<StockUsage> providerName = new ArrayList<>();
        providerName.add(stockUsage);

        activity = Mockito.spy(activity);
        Mockito.doReturn("provider").when(activity).getProviderName();
        Mockito.doReturn("usage").when(activity).getStockUsageForMonth(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString());

        stockUsageItemModelsList.add(new StockUsageItemModel(stockName, unitsOfMeasure, "20", providerName.get(0).getProviderId()));
        activity.getStockUsageItemReportList(month, year);
        Assert.assertEquals(1, stockUsageItemModelsList.size());
    }

    /*
   @Test
    public void testReloadRecycler() {
        MonthStockUsageModel selected = activity.getMonthStockUsageReportList().get(0);
        Assert.assertNotNull(selected);
        StockUsageReportUtils stockUsageReportUtils = new StockUsageReportUtils();
        String stockMonth = stockUsageReportUtils.getMonthNumber(selected.getMonth().substring(0, 3));
        String stockYear = selected.getYear();


        List<StockUsageItemModel> stockUsageItemReportList = activity.getStockUsageItemReportList(stockMonth, stockYear);
        CoreStockUsageItemAdapter coreStockUsageItemAdapter = new CoreStockUsageItemAdapter(stockUsageItemReportList, context);

        activity.reloadRecycler(selected);
        //Assert.assertNotNull(activity.reloadRecycler(selected));
    }
*/
    @Test
    public void testOnCreation() {
        // check if created views are found
        activity.onCreation();
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "toolBarTextView"));
        Assert.assertNotNull(ReflectionHelpers.getField(activity, "appBarLayout"));

        // MonthStockUsageModel selected = activity.getMonthStockUsageReportList().get(0);
        //activity.reloadRecycler(selected);

    }

    @Test
    public void testOnCreateOptionsMenu() {
        Assert.assertFalse(activity.onCreateOptionsMenu(menu));
    }

}