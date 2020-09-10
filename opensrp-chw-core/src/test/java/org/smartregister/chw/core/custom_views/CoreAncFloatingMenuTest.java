package org.smartregister.chw.core.custom_views;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.listener.OnClickFloatingMenu;

public class CoreAncFloatingMenuTest extends BaseUnitTest {

    @Mock
    private CoreAncFloatingMenu coreAncFloatingMenu;

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
        Mockito.doNothing().when(coreAncFloatingMenu).setFloatMenuClickListener(onClickFloatingMenu);
        coreAncFloatingMenu.setFloatMenuClickListener(onClickFloatingMenu);

        ArgumentCaptor<OnClickFloatingMenu> captor = ArgumentCaptor.forClass(OnClickFloatingMenu.class);
        Mockito.verify(coreAncFloatingMenu, Mockito.times(1)).setFloatMenuClickListener(captor.capture());
        Assert.assertEquals(captor.getValue(), onClickFloatingMenu);
    }
    @Test
    public void whenOnClickAnswered() {
        Mockito.doNothing().when(coreAncFloatingMenu).onClick(view);
        coreAncFloatingMenu.onClick(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreAncFloatingMenu, Mockito.times(1)).onClick(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }
    @Test
    public void whenRedrawAnswered() {
        Mockito.doNothing().when(coreAncFloatingMenu).redraw(true);
        coreAncFloatingMenu.redraw(true);
        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(coreAncFloatingMenu, Mockito.times(1)).redraw(captor.capture());
        Assert.assertEquals(captor.getValue(), true);
    }

    @Test
    public void testGetCallLayout() {
        Mockito.when(coreAncFloatingMenu.getCallLayout())
                .thenReturn(view);
        Assert.assertEquals(view, coreAncFloatingMenu.getCallLayout());
    }

}
