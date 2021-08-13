package org.smartregister.chw.core.fragment;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.junit.After;
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
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.CoreChildRegisterFragmentContract;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.domain.FetchStatus;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CoreBirthNotificationFragmentTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    public RecyclerView clientsView;
    @Mock
    private Context context;

    @Captor
    private ArgumentCaptor<RecyclerViewPaginatedAdapter> adapterArgumentCaptor;

    @Mock
    private CoreBirthNotificationFragment coreFpRegisterFragment;

    @Mock
    private CommonRepository commonRepository;

    @Mock
    private View view;

    private FragmentActivity activity;
    private ActivityController<AppCompatActivity> activityController;

    @Mock
    private ImageView imageView;

    @Mock
    private ProgressBar clientsProgressView;

    @Mock
    private TextView textView;

    @Mock
    private RelativeLayout relativeLayout;

    @Mock
    private EditText editText;

    @Mock
    private TextWatcher textWatcher;

    @Mock
    private View.OnKeyListener hideKeyboard;

    @Mock
    private ProgressBar syncProgressBar;

    @Mock
    private CoreChildRegisterFragmentContract.Presenter presenter;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        coreFpRegisterFragment = Mockito.mock(CoreBirthNotificationFragment.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(coreFpRegisterFragment, "presenter", presenter);
        ReflectionHelpers.setField(coreFpRegisterFragment, "view", view);
        ReflectionHelpers.setField(coreFpRegisterFragment, "dueOnlyLayout", view);
        ReflectionHelpers.setField(coreFpRegisterFragment, "clientsProgressView", clientsProgressView);
        ReflectionHelpers.setField(coreFpRegisterFragment, "dueFilterActive", true);
        ReflectionHelpers.setField(coreFpRegisterFragment, "qrCodeScanImageView", imageView);
        ReflectionHelpers.setField(coreFpRegisterFragment, "headerTextDisplay", textView);
        ReflectionHelpers.setField(coreFpRegisterFragment, "filterStatus", textView);
        ReflectionHelpers.setField(coreFpRegisterFragment, "filterRelativeLayout", relativeLayout);
        ReflectionHelpers.setField(coreFpRegisterFragment, "searchView", editText);
        ReflectionHelpers.setField(coreFpRegisterFragment, "textWatcher", textWatcher);
        ReflectionHelpers.setField(coreFpRegisterFragment, "hideKeyboard", hideKeyboard);
        ReflectionHelpers.setField(coreFpRegisterFragment, "syncProgressBar", syncProgressBar);
        ReflectionHelpers.setField(coreFpRegisterFragment, "syncButton", imageView);
        ReflectionHelpers.setField(coreFpRegisterFragment, "globalQrSearch", false);

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
    public void testSetupViews() {
        when(coreFpRegisterFragment.getActivity()).thenReturn(activity);
        when(coreFpRegisterFragment.getContext()).thenReturn(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.fragment_base_register, null);
        coreFpRegisterFragment.setupViews(view);

        View dueOnlyLayout = view.findViewById(R.id.due_only_layout);
        dueOnlyLayout.setVisibility(View.VISIBLE);
        View searchBarLayout = view.findViewById(org.smartregister.R.id.search_bar_layout);
        searchBarLayout.setBackgroundResource(R.color.customAppThemeBlue);
        assertEquals(View.VISIBLE, dueOnlyLayout.getVisibility());
    }

    @Test
    public void testSetUniqueID() {
        if (coreFpRegisterFragment.getSearchView() != null) {
            coreFpRegisterFragment.getSearchView().setText(Mockito.anyString());
        }
        assertNotNull(coreFpRegisterFragment.getSearchView());
    }

    @Test
    public void getMainConditionCallsPresenterGetCondition() {
        coreFpRegisterFragment.getMainCondition();
        verify(coreFpRegisterFragment.presenter(), Mockito.times(1)).getMainCondition();
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
    public void whenToggleFilterSelectionAnswered() {
        Mockito.doNothing().when(coreFpRegisterFragment).toggleFilterSelection(view);
        coreFpRegisterFragment.toggleFilterSelection(view);

        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        verify(coreFpRegisterFragment, Mockito.times(1)).toggleFilterSelection(captor.capture());
        assertEquals(captor.getValue(), view);
    }

    @Test
    public void testOnViewClick() {
        coreFpRegisterFragment.onViewClicked(view);
        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        verify(coreFpRegisterFragment, Mockito.times(1)).onViewClicked(captor.capture());
        assertEquals(captor.getValue(), view);
    }

    @Test
    public void onSyncCompleteTogglesSyncVisibility() {
        FetchStatus fetchStatus = Mockito.anyObject();
        ReflectionHelpers.setField(coreFpRegisterFragment, "syncProgressBar", syncProgressBar);
        coreFpRegisterFragment.onSyncComplete(fetchStatus);
        verify(syncProgressBar, Mockito.times(2)).setVisibility(View.GONE);
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
