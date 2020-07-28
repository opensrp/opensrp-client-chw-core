package org.smartregister.chw.core.fragment;

import android.view.View;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.presenter.CoreChildRegisterFragmentPresenter;

public class CoreChildRegisterFragmentTest extends BaseUnitTest {

    @Mock
    private CoreChildRegisterFragmentPresenter presenter;

    private CoreChildRegisterFragment fragment;

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = Mockito.mock(CoreChildRegisterFragment.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(fragment, "presenter", presenter);
        ReflectionHelpers.setField(fragment, "view", view);
        ReflectionHelpers.setField(fragment, "dueOnlyLayout", view);
        ReflectionHelpers.setField(fragment, "dueFilterActive", true);
    }

    @Test
    public void testInitializePresenter() {
        fragment.initializePresenter();
        Assert.assertNotNull(presenter);
    }

    @Test
    public void testGetMainCondition() {
        Assert.assertEquals(fragment.getMainCondition(), presenter.getMainCondition());
    }

    @Test
    public void testGetDefaultSortQuery() {
        Assert.assertEquals(fragment.getDefaultSortQuery(), presenter.getDefaultSortQuery());
    }

    @Test
    public void testGetToolBarTitle() {
        Assert.assertEquals(fragment.getToolBarTitle(), R.string.child_register_title);
    }

    private void getDueFilterCondition() {
        Mockito.doReturn("").when(fragment).getDueFilterCondition();
    }

    @Test
    public void testOnResumption() {
        Mockito.doNothing().when(fragment).FilterAndSortExecute();
        getDueFilterCondition();
        Mockito.doReturn(textView).when(fragment).getDueOnlyTextView(fragment.dueOnlyLayout);
        fragment.onResumption();
        Assert.assertEquals(fragment.dueOnlyLayout, view);
    }

}