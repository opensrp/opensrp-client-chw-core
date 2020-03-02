package org.smartregister.chw.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.smartregister.chw.core.model.StockUsageItemModel;

import java.util.Arrays;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LayoutInflater.class})
public class CoreStockUsageItemAdapterTest {
    @Mock
    Context context;
    private CoreStockUsageItemAdapter adapter;
    @Mock
    private CoreStockUsageItemAdapter.CoreStockUsageReportViewHolder viewHolder;
    @Mock
    StockUsageItemModel stockUsageItemModel;

    private List<StockUsageItemModel> stockUsageItemModelList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        stockUsageItemModelList = Arrays.asList(new StockUsageItemModel("zinc 10", "packets", "10"),
                new StockUsageItemModel("Paracetamol", "packets", "20"));
        adapter = new CoreStockUsageItemAdapter(stockUsageItemModelList, context);
    }

    @Test
    public void testOnCreateViewHolder() throws Exception {
    }

    @Test
    public void testItemAtPosition() {
        Assert.assertEquals(stockUsageItemModel.getStockName(), stockUsageItemModelList.get(0).getStockName());
    }

    @Test
    public void testOnBindViewHolder() {

        StockUsageItemModel stockUsageItemModel = Mockito.spy(StockUsageItemModel.class);
        Whitebox.setInternalState(viewHolder, "stockName", Mockito.mock(TextView.class));
        Whitebox.setInternalState(viewHolder, "stockUnitOfMeasure", Mockito.mock(TextView.class));
        Whitebox.setInternalState(viewHolder, "stockCount", Mockito.mock(TextView.class));
        Whitebox.setInternalState(viewHolder, "view", Mockito.mock(View.class));
        adapter.onBindViewHolder(viewHolder, 0);
        Mockito.verify(stockUsageItemModel, Mockito.times(1)).getStockName();
    }


    @Test
    public void testGetItemCount() {
        List<StockUsageItemModel> stockUsageItemModelList = Arrays.asList(new StockUsageItemModel("zinc 10", "packets", "10"),
                new StockUsageItemModel("Paracetamol", "packets", "20"));
        adapter = new CoreStockUsageItemAdapter(stockUsageItemModelList, context);
        int count = stockUsageItemModelList.size();
        Assert.assertEquals(adapter.getItemCount(), count);
    }
}