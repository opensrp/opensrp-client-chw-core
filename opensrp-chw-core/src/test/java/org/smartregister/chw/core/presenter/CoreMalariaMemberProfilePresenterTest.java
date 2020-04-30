package org.smartregister.chw.core.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.core.contract.CoreMalariaProfileContract;

import java.lang.ref.WeakReference;

public class CoreMalariaMemberProfilePresenterTest {

    @Mock
    private CoreMalariaProfileContract.View view;

    private CoreMalariaMemberProfilePresenter memberProfilePresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        memberProfilePresenter = Mockito.mock(CoreMalariaMemberProfilePresenter.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(memberProfilePresenter, "view", new WeakReference<>(view));
    }

    @Test
    public void nonNullViewReturnedWhenPresenterInitialised() {
        Assert.assertEquals(view, memberProfilePresenter.getView());
    }
}
