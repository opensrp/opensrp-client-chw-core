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
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.impl.CoreRegisterProviderImpl;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.UtilsShadowUtil;

@Config(shadows = {UtilsShadowUtil.class, ContextShadow.class})
public class CoreRegisterProviderTest extends BaseUnitTest {

    private CoreRegisterProvider coreRegisterProvider;
    private final Context context = RuntimeEnvironment.application;

    @Mock
    private View.OnClickListener onClickListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        coreRegisterProvider = Mockito.mock(CoreRegisterProviderImpl.class, Mockito.CALLS_REAL_METHODS);
        Whitebox.setInternalState(coreRegisterProvider, "onClickListener", onClickListener);
    }

    @Test
    public void canSetVisitNotDoneButtonState() {
        Button button = Mockito.spy(Mockito.mock(Button.class));
        coreRegisterProvider.setVisitNotDone(context, button);
        Mockito.verify(button).setTextColor(context.getColor(R.color.progress_orange));
        Mockito.verify(button).setText(context.getString(R.string.visit_not_done));
        Mockito.verify(button).setBackgroundColor(context.getResources().getColor(R.color.transparent));
        Mockito.verify(button).setOnClickListener(null);
    }

    @Test
    public void canSetVisitAboveTwentyFourButtonState() {
        Button button = Mockito.spy(Mockito.mock(Button.class));
        coreRegisterProvider.setVisitAboveTwentyFourView(context, button);
        Mockito.verify(button).setTextColor(context.getColor(R.color.alert_complete_green));
        Mockito.verify(button).setText(context.getString(R.string.visit_done));
        Mockito.verify(button).setBackgroundColor(context.getResources().getColor(R.color.transparent));
        Mockito.verify(button).setOnClickListener(null);
    }

    @Test
    public void canSetVisitOverdueButtonState() {
        Button button = Mockito.spy(Mockito.mock(Button.class));
        String lastVisitDays = "10 days";
        coreRegisterProvider.setVisitButtonOverdueStatus(context, button, lastVisitDays);
        Mockito.verify(button).setTextColor(context.getResources().getColor(R.color.white));
        Mockito.verify(button).setText(context.getString(R.string.due_visit, lastVisitDays));
        Mockito.verify(button).setBackgroundResource(R.drawable.overdue_red_btn_selector);
        Mockito.verify(button).setOnClickListener(onClickListener);
    }

    @Test
    public void canSetVisitDueButtonState() {
        Button button = Mockito.spy(Mockito.mock(Button.class));
        coreRegisterProvider.setVisitButtonDueStatus(context, button);
        Mockito.verify(button).setTextColor(context.getColor(R.color.alert_in_progress_blue));
        Mockito.verify(button).setText(context.getString(R.string.record_home_visit));
        Mockito.verify(button).setBackgroundResource(R.drawable.blue_btn_selector);
        Mockito.verify(button).setOnClickListener(onClickListener);
    }
}
