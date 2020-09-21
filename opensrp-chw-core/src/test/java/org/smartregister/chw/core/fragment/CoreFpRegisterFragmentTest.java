package org.smartregister.chw.core.fragment;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CoreFpRegisterFragmentTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    public RecyclerView clientsView;
    @Mock
    private Context context;

    @Captor
    private ArgumentCaptor<RecyclerViewPaginatedAdapter> adapterArgumentCaptor;

    @Mock
    private CoreFpRegisterFragment coreFpRegisterFragment;

    @Mock
    private CommonRepository commonRepository;

    @Mock
    private View view;

    private FragmentActivity activity;
    private ActivityController<AppCompatActivity> activityController;

    private BaseRegisterFragmentContract.Presenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        coreFpRegisterFragment = Mockito.mock(CoreFpRegisterFragment.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(coreFpRegisterFragment, "presenter", presenter);
        ReflectionHelpers.setField(coreFpRegisterFragment, "view", view);
        ReflectionHelpers.setField(coreFpRegisterFragment, "dueOnlyLayout", view);
        ReflectionHelpers.setField(coreFpRegisterFragment, "dueFilterActive", true);

        CoreLibrary.init(context);
        when(context.commonrepository(anyString())).thenReturn(commonRepository);
        activityController = Robolectric.buildActivity(AppCompatActivity.class).create().resume();
        activity = activityController.get();
        Context.bindtypes = new ArrayList<>();
        Whitebox.setInternalState(coreFpRegisterFragment, "clientsView", clientsView);
        Whitebox.setInternalState(coreFpRegisterFragment, "presenter", presenter);
        SyncStatusBroadcastReceiver.init(activity);
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
    public void testInitializeAdapter() {
        when(coreFpRegisterFragment.getActivity()).thenReturn(activity);
        coreFpRegisterFragment.initializeAdapter(new HashSet<>());
        verify(clientsView).setAdapter(adapterArgumentCaptor.capture());
        assertNotNull(adapterArgumentCaptor.getValue());
        assertEquals(20, adapterArgumentCaptor.getValue().currentlimit);
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

    @After
    public void tearDown() {
        try {
            activityController.pause().stop().destroy();
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
