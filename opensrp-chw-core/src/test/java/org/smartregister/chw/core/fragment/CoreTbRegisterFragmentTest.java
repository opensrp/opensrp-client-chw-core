package org.smartregister.chw.core.fragment;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;

import java.util.Set;

public class CoreTbRegisterFragmentTest extends BaseUnitTest {

    @Mock
    private CoreTbRegisterFragment coreTbRegisterFragment;

    @Mock
    private View view;

    @Mock
    private Set<org.smartregister.configurableviews.model.View> visibleColumns;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

   

    @Test
    public void testInitializeAdapter() {
        Mockito.doNothing().when(coreTbRegisterFragment).initializeAdapter(visibleColumns);
        coreTbRegisterFragment.initializeAdapter(visibleColumns);

        ArgumentCaptor<Set> captor = ArgumentCaptor.forClass(Set.class);
        Mockito.verify(coreTbRegisterFragment, Mockito.times(1)).initializeAdapter(captor.capture());
        Assert.assertEquals(captor.getValue(), visibleColumns);
    }

    @Test
    public void testSetupViews() {
        Mockito.doNothing().when(coreTbRegisterFragment).setupViews(view);
        coreTbRegisterFragment.setupViews(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreTbRegisterFragment, Mockito.times(1)).setupViews(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }
    
    @Test
    public void testOnViewClicked() {
        Mockito.doNothing().when(coreTbRegisterFragment).onViewClicked(view);
        coreTbRegisterFragment.onViewClicked(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreTbRegisterFragment, Mockito.times(1)).onViewClicked(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }

    @Test
    public void testDueFilters() {
        Mockito.doNothing().when(coreTbRegisterFragment).dueFilter(view);
        coreTbRegisterFragment.dueFilter(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreTbRegisterFragment, Mockito.times(1)).dueFilter(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }

    @Test
    public void testToggleFilterSelection() {
        Mockito.doNothing().when(coreTbRegisterFragment).toggleFilterSelection(view);
        coreTbRegisterFragment.toggleFilterSelection(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreTbRegisterFragment, Mockito.times(1)).toggleFilterSelection(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }
    
    @Test
    public void testFilterDue() {
        String filterString = "filterString";
        String joinTableString = "joinTableString";
        String mainConditionString = "mainConditionString";
        Mockito.doNothing().when(coreTbRegisterFragment).filterDue(filterString, joinTableString, mainConditionString);
        coreTbRegisterFragment.filterDue(filterString, joinTableString, mainConditionString);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
        Mockito.verify(coreTbRegisterFragment, Mockito.times(1)).filterDue(captor.capture(), captor1.capture(), captor2.capture());
        Assert.assertEquals(captor.getValue(), filterString);
        Assert.assertEquals(captor1.getValue(), joinTableString);
        Assert.assertEquals(captor2.getValue(), mainConditionString);
    }
    
    @Test
    public void testNormalFilter() {
        Mockito.doNothing().when(coreTbRegisterFragment).normalFilter(view);
        coreTbRegisterFragment.normalFilter(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreTbRegisterFragment, Mockito.times(1)).normalFilter(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }


}
