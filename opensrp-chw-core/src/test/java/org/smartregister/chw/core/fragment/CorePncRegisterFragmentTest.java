package org.smartregister.chw.core.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.anc.util.DBConstants;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.pnc.presenter.BasePncRegisterFragmentPresenter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class CorePncRegisterFragmentTest extends BaseUnitTest {

    @Mock
    private View view;

    @Mock
    private ProgressBar syncProgressBar;

    @Mock
    private ImageView syncButton;

    @Mock
    private BasePncRegisterFragmentPresenter presenter;

    private CorePncRegisterFragment fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = Mockito.mock(CorePncRegisterFragment.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(fragment, "presenter", presenter);
        ReflectionHelpers.setField(fragment, "view", view);
        ReflectionHelpers.setField(fragment, "dueOnlyLayout", view);
    }

    @Test
    public void presenterInitializesCorrectly() {
        fragment.initializePresenter();
        Assert.assertNotNull(presenter);
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
    public void testOnViewClick() {
        fragment.onViewClicked(view);
        ArgumentCaptor<View> captor = ArgumentCaptor.forClass(View.class);
        verify(fragment, Mockito.times(1)).onViewClicked(captor.capture());
        assertEquals(captor.getValue(), view);
    }

    @Test
    public void getConditionReturnsCorrectValue() {
        String expectedCondition = " " + CoreConstants.TABLE_NAME.FAMILY_MEMBER + "." + DBConstants.KEY.DATE_REMOVED + " is null " +
                "AND " + CoreConstants.TABLE_NAME.ANC_PREGNANCY_OUTCOME + "." + DBConstants.KEY.IS_CLOSED + " is 0 ";
        Assert.assertEquals(expectedCondition, fragment.getCondition());
    }

    @Test
    public void getDueCondition() {
        String expectedDueCondition = "ec_pregnancy_outcome.base_entity_id in (select base_entity_id from schedule_service where strftime('%Y-%m-%d') BETWEEN due_date and expiry_date " +
                "and schedule_name = '" + CoreConstants.SCHEDULE_TYPES.PNC_VISIT + "' and ifnull(not_done_date,'') = '' and ifnull(completion_date,'') = '' )  ";
        Assert.assertEquals(expectedDueCondition, fragment.getDueCondition());
    }
}
