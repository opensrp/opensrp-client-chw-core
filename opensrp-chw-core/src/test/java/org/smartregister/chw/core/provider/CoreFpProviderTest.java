package org.smartregister.chw.core.provider;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;
import org.robolectric.RuntimeEnvironment;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;

public class CoreFpProviderTest extends BaseUnitTest {

    private final Context context = RuntimeEnvironment.application;
    private CoreFpProvider coreFpProvider;
    @Mock
    private View.OnClickListener onClickListener;

    private Button dueButton;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        coreFpProvider = Mockito.mock(CoreFpProvider.class, Mockito.CALLS_REAL_METHODS);
        Whitebox.setInternalState(coreFpProvider, "onClickListener", onClickListener);
        dueButton = Mockito.mock(Button.class);
    }

    @Test
    public void setVisitButtonNextDueStatusSetsButtonToCorrectState() throws Exception {
        String visitDueText = "today";
        Whitebox.invokeMethod(coreFpProvider, "setVisitButtonNextDueStatus", context, visitDueText, dueButton);
        Mockito.verify(dueButton).setText(String.format(context.getString(R.string.fp_visit_day_next_due), visitDueText));
        Mockito.verify(dueButton).setTextColor(context.getResources().getColor(R.color.light_grey_text));
        Mockito.verify(dueButton).setBackgroundResource(R.drawable.colorless_btn_selector);
        Mockito.verify(dueButton).setOnClickListener(null);
    }

    @Test
    public void setVisitButtonDueStatusSetsButtonToCorrectState() throws Exception {
        String visitDueText = "today";
        Whitebox.invokeMethod(coreFpProvider, "setVisitButtonDueStatus", context, visitDueText, dueButton);
        Mockito.verify(dueButton).setTextColor(context.getResources().getColor(R.color.alert_in_progress_blue));
        Mockito.verify(dueButton).setText(String.format(context.getString(R.string.fp_visit_day_due), visitDueText));
        Mockito.verify(dueButton).setBackgroundResource(R.drawable.blue_btn_selector);
        Mockito.verify(dueButton).setOnClickListener(onClickListener);
    }

    @Test
    public void setVisitButtonOverdueStatusSetsButtonToCorrectState() throws Exception {
        String visitDueText = "2 weeks ago";
        Whitebox.invokeMethod(coreFpProvider, "setVisitButtonOverdueStatus", context, visitDueText, dueButton);
        Mockito.verify(dueButton).setTextColor(context.getResources().getColor(R.color.white));
        Mockito.verify(dueButton).setText(String.format(context.getString(R.string.fp_visit_day_overdue), visitDueText));
        Mockito.verify(dueButton).setBackgroundResource(R.drawable.overdue_red_btn_selector);
        Mockito.verify(dueButton).setOnClickListener(onClickListener);
    }

    @Test
    public void setVisitDoneSetsButtonToCorrectState() throws Exception {
        Whitebox.invokeMethod(coreFpProvider, "setVisitDone", context, dueButton);
        Mockito.verify(dueButton).setTextColor(context.getResources().getColor(R.color.alert_complete_green));
        Mockito.verify(dueButton).setText(context.getString(R.string.visit_done));
        Mockito.verify(dueButton).setBackgroundColor(context.getResources().getColor(R.color.transparent));
        Mockito.verify(dueButton).setOnClickListener(null);
    }

}
