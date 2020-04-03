package org.smartregister.chw.core.fragment;

import android.os.Bundle;
import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class CoreFpRegisterFragmentTest {
    @Mock
    private Bundle bundle;

    @Mock
    private CoreFpRegisterFragment coreFpRegisterFragment;

    @Mock
    private View view;

    @Mock
    private Set<org.smartregister.configurableviews.model.View> visibleColumns;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenSetupViewsAnswered() {
        Mockito.doNothing().when(coreFpRegisterFragment).setupViews(view);
        coreFpRegisterFragment.setupViews(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreFpRegisterFragment, Mockito.times(1)).setupViews(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }

    @Test
    public void whenInitializeAdapterAnswered() {
        Mockito.doNothing().when(coreFpRegisterFragment).initializeAdapter(visibleColumns);
        coreFpRegisterFragment.initializeAdapter(visibleColumns);

        ArgumentCaptor<Set> captor = ArgumentCaptor.forClass(Set.class);
        Mockito.verify(coreFpRegisterFragment, Mockito.times(1)).initializeAdapter(captor.capture());
        Assert.assertEquals(captor.getValue(), visibleColumns);
    }

    @Test
    public void whenOnViewClickedAnswered() {
        Mockito.doNothing().when(coreFpRegisterFragment).onViewClicked(view);
        coreFpRegisterFragment.onViewClicked(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreFpRegisterFragment, Mockito.times(1)).onViewClicked(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }

    @Test
    public void whenToggleFilterSelectionAnswered() {
        Mockito.doNothing().when(coreFpRegisterFragment).toggleFilterSelection(view);
        coreFpRegisterFragment.toggleFilterSelection(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreFpRegisterFragment, Mockito.times(1)).toggleFilterSelection(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }

    @Test
    public void whenDueFiltersAnswered() {
        Mockito.doNothing().when(coreFpRegisterFragment).dueFilter(view);
        coreFpRegisterFragment.dueFilter(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreFpRegisterFragment, Mockito.times(1)).dueFilter(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }

    @Test
    public void whenNormalFilterAnswered() {
        Mockito.doNothing().when(coreFpRegisterFragment).normalFilter(view);
        coreFpRegisterFragment.normalFilter(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreFpRegisterFragment, Mockito.times(1)).normalFilter(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }

    @Test
    public void whenFilterDueAnswered() {
        String filterString = "filterString";
        String joinTableString = "joinTableString";
        String mainConditionString = "mainConditionString";
        Mockito.doNothing().when(coreFpRegisterFragment).filterDue(filterString, joinTableString, mainConditionString);
        coreFpRegisterFragment.filterDue(filterString, joinTableString, mainConditionString);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
        Mockito.verify(coreFpRegisterFragment, Mockito.times(1)).filterDue(captor.capture(), captor1.capture(), captor2.capture());
        Assert.assertEquals(captor.getValue(), filterString);
        Assert.assertEquals(captor1.getValue(), joinTableString);
        Assert.assertEquals(captor2.getValue(), mainConditionString);
    }


}
