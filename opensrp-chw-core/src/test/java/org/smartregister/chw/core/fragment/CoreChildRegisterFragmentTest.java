package org.smartregister.chw.core.fragment;

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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = Mockito.mock(CoreChildRegisterFragment.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(fragment, "presenter", presenter);
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

}