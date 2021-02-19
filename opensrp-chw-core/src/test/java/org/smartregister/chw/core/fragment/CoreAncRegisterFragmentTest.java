package org.smartregister.chw.core.fragment;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.Robolectric;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.chw.anc.AncLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.fragment.impl.CoreAncRegisterFragmentImpl;
import org.smartregister.chw.core.presenter.AncRegisterFragmentPresenter;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.configurableviews.model.View;
import org.smartregister.repository.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CoreAncRegisterFragmentTest extends BaseUnitTest {

    @Mock
    private ProgressBar syncProgressBar;

    @Mock
    private ImageView syncButton;

    @Mock
    private AncRegisterFragmentPresenter presenter;


    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private CoreAncRegisterFragmentImpl fragment;

    @Mock
    private Repository repository;

    @Mock
    private Context context;

    @Before
    public void setUp() {

        AncLibrary.init(context, repository, 1, 1);
        Context.bindtypes = new ArrayList<>();
        fragment = new CoreAncRegisterFragmentImpl();
        AppCompatActivity activity = Robolectric.buildActivity(AppCompatActivity.class).create().start().get();
        activity.setContentView(org.smartregister.family.R.layout.activity_family_profile);
        Whitebox.setInternalState(fragment, "presenter", presenter);
        activity.getSupportFragmentManager().beginTransaction().add(fragment, "Presenter").commit();
    }

    @Test
    public void refreshSyncProgressSpinnerTogglesSyncVisibility() {
        ReflectionHelpers.setField(fragment, "syncButton", syncButton);
        ReflectionHelpers.setField(fragment, "syncProgressBar", syncProgressBar);
        fragment.refreshSyncProgressSpinner();
        Mockito.verify(syncProgressBar, Mockito.times(1)).setVisibility(android.view.View.GONE);
        Mockito.verify(syncButton, Mockito.times(1)).setVisibility(android.view.View.GONE);
    }

    @Test
    public void testInitializeAdapter() {
        Set<View> visibleColumns = new HashSet<>();
        fragment.initializeAdapter(visibleColumns);
        Assert.assertNotNull(ReflectionHelpers.getField(fragment, "clientsView"));
    }

    @Test
    public void testOnViewClicked() {
        fragment = Mockito.spy(fragment);
        android.view.View view = Mockito.mock(android.view.View.class);
        Mockito.doReturn(R.id.due_only_layout).when(view).getId();

        CommonRepository commonRepository = Mockito.mock(CommonRepository.class);
        Mockito.doReturn(commonRepository).when(fragment).commonRepository();


        TextView dueOnlyLayout = Mockito.mock(TextView.class);
        Mockito.doReturn(dueOnlyLayout).when(view).findViewById(R.id.due_only_text_view);

        fragment.onViewClicked(view);
        Assert.assertTrue(((boolean) (ReflectionHelpers.getField(fragment, "dueFilterActive"))));
    }

}
