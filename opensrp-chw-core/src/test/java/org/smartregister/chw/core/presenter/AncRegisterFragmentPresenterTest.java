package org.smartregister.chw.core.presenter;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.smartregister.chw.anc.contract.BaseAncRegisterFragmentContract;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.ViewConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AncRegisterFragmentPresenterTest extends BaseUnitTest {

    private AncRegisterFragmentPresenter presenter;

    @Mock
    private BaseAncRegisterFragmentContract.View view;

    @Mock
    private BaseAncRegisterFragmentContract.Model model;

    @Mock
    private ViewConfiguration viewConfiguration;

    @Mock
    private RegisterConfiguration registerConfiguration;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Context context = RuntimeEnvironment.application.getApplicationContext();
        when(view.getContext()).thenReturn(context);
        when(viewConfiguration.getMetadata()).thenReturn(registerConfiguration);
        when(model.getViewConfiguration(anyString())).thenReturn(viewConfiguration);
        when(model.defaultRegisterConfiguration()).thenReturn(registerConfiguration);
        presenter = new AncRegisterFragmentPresenter(view, model, "123");
    }

    @Test
    public void testGetDefaultSortQuery() {
        Assert.assertEquals(presenter.getDefaultSortQuery(), "ec_anc_register.last_interacted_with DESC ");
    }

    @Test
    public void testGetMainConditionReturnsCorrectClause(){
        assertThat(presenter.getMainCondition(), is(equalTo(" ec_family_member.date_removed is null AND ec_anc_register.is_closed is 0 ")));
    }

    @Test
    public void testProcessViewConfigurationsCallsUpdateSearchBarHint(){
        when(registerConfiguration.getSearchBarText()).thenReturn("Search");
        presenter.processViewConfigurations();
        String text = view.getContext().getString(R.string.search_name_or_id);
        verify(view, atLeastOnce()).updateSearchBarHint(eq(text));
    }

    @Test
    public void testGetMainTable() {
        Assert.assertEquals(presenter.getMainTable(), "ec_anc_register");
    }

}
