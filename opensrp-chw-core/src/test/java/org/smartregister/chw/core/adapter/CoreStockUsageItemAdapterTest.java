package org.smartregister.chw.core.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.model.StockUsageItemModel;

import java.util.Arrays;
import java.util.List;

public class CoreStockUsageItemAdapterTest extends BaseUnitTest {
    private Context context = RuntimeEnvironment.application;
    private CoreStockUsageItemAdapter adapter;

    @Mock
    private CoreStockUsageItemAdapter.CoreStockUsageReportViewHolder viewHolder;
    private List<StockUsageItemModel> stockUsageItemModelList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        stockUsageItemModelList = Arrays.asList(new StockUsageItemModel("zinc 10", "packets", "10", "chwone"),
                new StockUsageItemModel("Paracetamol", "packets", "20", "chwone"));
        adapter = new CoreStockUsageItemAdapter(stockUsageItemModelList, context);
    }

    @Test
    public void testItemAtPosition() {
        StockUsageItemModel stockUsageItemModel;
        stockUsageItemModel = stockUsageItemModelList.get(0);
        Assert.assertEquals(stockUsageItemModel.getStockName(), stockUsageItemModelList.get(0).getStockName());
    }

    @Test
    public void testOnBindViewHolder() {
        TextView stockName = Mockito.mock(TextView.class);
        TextView stockUnitOfMeasure = Mockito.mock(TextView.class);
        TextView stockCount = Mockito.mock(TextView.class);
        View view = Mockito.mock(View.class);

        ReflectionHelpers.setField(viewHolder, "stockName", stockName);
        ReflectionHelpers.setField(viewHolder, "stockUnitOfMeasure", stockUnitOfMeasure);
        ReflectionHelpers.setField(viewHolder, "stockCount", stockCount);
        ReflectionHelpers.setField(viewHolder, "view", view);

        StockUsageItemModel stockUsageItemModel = stockUsageItemModelList.get(0) ;
        adapter.onBindViewHolder(viewHolder, 0);

        Mockito.verify(stockName).setText(stockUsageItemModel.getStockName());
        Mockito.verify(stockUnitOfMeasure).setText(stockUsageItemModel.getUnitsOfMeasure());
        Mockito.verify(stockCount).setText(stockUsageItemModel.getStockValue());
    }

    @Test
    public void testGetItemCount() {
        int count = stockUsageItemModelList.size();
        Assert.assertEquals(adapter.getItemCount(), count);
    }
}