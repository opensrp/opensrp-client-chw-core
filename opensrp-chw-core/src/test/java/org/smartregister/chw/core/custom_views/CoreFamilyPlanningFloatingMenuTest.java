package org.smartregister.chw.core.custom_views;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.listener.OnClickFloatingMenu;

public class CoreFamilyPlanningFloatingMenuTest {
    @Mock
    private CoreFamilyPlanningFloatingMenu coreFamilyPlanningFloatingMenu;

    @Mock
    private View view;

    @Mock
    private OnClickFloatingMenu onClickFloatingMenu;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void whenSetupViewsAnswered() {
        Mockito.doNothing().when(coreFamilyPlanningFloatingMenu).setFloatingMenuOnClickListener(onClickFloatingMenu);
        coreFamilyPlanningFloatingMenu.setFloatingMenuOnClickListener(onClickFloatingMenu);

        ArgumentCaptor<OnClickFloatingMenu> captor = ArgumentCaptor.forClass(OnClickFloatingMenu.class);
        Mockito.verify(coreFamilyPlanningFloatingMenu, Mockito.times(1)).setFloatingMenuOnClickListener(captor.capture());
        Assert.assertEquals(captor.getValue(), onClickFloatingMenu);
    }

    @Test
    public void whenOnClickAnswered() {
        Mockito.doNothing().when(coreFamilyPlanningFloatingMenu).onClick(view);
        coreFamilyPlanningFloatingMenu.onClick(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreFamilyPlanningFloatingMenu, Mockito.times(1)).onClick(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }

    @Test
    public void whenRedrawAnswered() {
        Mockito.doNothing().when(coreFamilyPlanningFloatingMenu).redraw(true);
        coreFamilyPlanningFloatingMenu.redraw(true);

        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(coreFamilyPlanningFloatingMenu, Mockito.times(1)).redraw(captor.capture());
        Assert.assertEquals(captor.getValue(), true);
    }

    @Test
    public void testGetCallLayout() {
        Mockito.when(coreFamilyPlanningFloatingMenu.getCallLayout())
                .thenReturn(view);
        Assert.assertEquals(view, coreFamilyPlanningFloatingMenu.getCallLayout());
    }
}
