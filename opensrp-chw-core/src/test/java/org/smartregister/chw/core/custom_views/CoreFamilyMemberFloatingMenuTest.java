package org.smartregister.chw.core.custom_views;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.listener.OnClickFloatingMenu;
import org.smartregister.commonregistry.CommonRepository;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CoreFamilyMemberFloatingMenuTest {
    @Mock
    private CommonRepository commonRepository;

    @Mock
    private Context context;

    private CoreFamilyMemberFloatingMenu coreFamilyMemberFloatingMenu;

    @Mock
    private View view;

    @Mock
    private OnClickFloatingMenu onClickFloatingMenu;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        coreFamilyMemberFloatingMenu = Mockito.mock(CoreFamilyMemberFloatingMenu.class, Mockito.CALLS_REAL_METHODS);

        CoreLibrary.init(context);
        when(context.commonrepository(anyString())).thenReturn(commonRepository);
        Context.bindtypes = new ArrayList<>();
    }

    @Test
    public void whenSetupViewsAnswered() {
        coreFamilyMemberFloatingMenu.setClickListener(onClickFloatingMenu);

        ArgumentCaptor<OnClickFloatingMenu> captor = ArgumentCaptor.forClass(OnClickFloatingMenu.class);
        Mockito.verify(coreFamilyMemberFloatingMenu, Mockito.times(1)).setClickListener(captor.capture());
        Assert.assertEquals(captor.getValue(), onClickFloatingMenu);
    }

    @Test
    public void whenOnClickAnswered() {
        Mockito.doNothing().when(coreFamilyMemberFloatingMenu).onClick(view);
        coreFamilyMemberFloatingMenu.onClick(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        Mockito.verify(coreFamilyMemberFloatingMenu, Mockito.times(1)).onClick(captor.capture());
        Assert.assertEquals(captor.getValue(), view);
    }

    @Test
    public void whenRedrawAnswered() {
        Mockito.doNothing().when(coreFamilyMemberFloatingMenu).reDraw(true);
        coreFamilyMemberFloatingMenu.reDraw(true);

        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(coreFamilyMemberFloatingMenu, Mockito.times(1)).reDraw(captor.capture());
        Assert.assertEquals(captor.getValue(), true);
    }
}
