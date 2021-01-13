package org.smartregister.chw.core.fragment;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.contract.FamilyCallDialogContract;
import org.smartregister.chw.core.presenter.FamilyCallDialogPresenter;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class FamilyCallDialogFragmentTest extends BaseUnitTest {
    @Mock
    private CommonRepository commonRepository;
    @Mock
    private Context context;

    @Mock
    private FamilyCallDialogFragment fragment;

    @Mock
    private FamilyCallDialogContract.Model model;

    @Mock
    private TextView textView;

    @Mock
    private LinearLayout linearLayout;


    private FragmentActivity activity;

    private ActivityController controller;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = Mockito.mock(FamilyCallDialogFragment.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(fragment, "tvFamilyHeadTitle", textView);
        ReflectionHelpers.setField(fragment, "tvFamilyHeadName", textView);
        ReflectionHelpers.setField(fragment, "tvFamilyHeadPhone", textView);
        ReflectionHelpers.setField(fragment, "tvCareGiverTitle", textView);
        ReflectionHelpers.setField(fragment, "tvCareGiverName", textView);
        ReflectionHelpers.setField(fragment, "tvCareGiverPhone", textView);
        ReflectionHelpers.setField(fragment, "dialogTitle", textView);
        ReflectionHelpers.setField(fragment, "llFamilyHead", linearLayout);
        ReflectionHelpers.setField(fragment, "llCareGiver", linearLayout);
        ReflectionHelpers.setField(fragment, "familyBaseEntityId", "12345");

        CoreLibrary.init(context);
        when(context.commonrepository(anyString())).thenReturn(commonRepository);
        controller = Robolectric.buildActivity(AppCompatActivity.class).create().resume();
        activity = (FragmentActivity) controller.get();
        Context.bindtypes = new ArrayList<>();
        SyncStatusBroadcastReceiver.init(activity);
    }

    @Test
    public void testInitializePresenter() {
        fragment.initializePresenter();
        FamilyCallDialogPresenter presenter = new FamilyCallDialogPresenter(fragment, "12345");
        Assert.assertTrue(presenter instanceof FamilyCallDialogPresenter);
        assertNotNull(presenter);
    }

    @Test
    public void whenRefreshHeadOfFamilyViewAnswered() {
        Mockito.when(model.getName()).thenReturn("Family head Name");
        Mockito.when(model.getRole()).thenReturn("Family Head");
        fragment.refreshHeadOfFamilyView(model);
        Assert.assertNotNull(model);
        Assert.assertNull(model.getPhoneNumber());
        Assert.assertEquals("Family head Name", model.getName());
        Assert.assertEquals("Family Head", model.getRole());
    }

    @Test
    public void whenRefreshCareGiverViewAnswered() {
        Mockito.when(model.getName()).thenReturn("Care giver Name");
        Mockito.when(model.getRole()).thenReturn("Care Giver");
        fragment.refreshCareGiverView(model);
        Assert.assertNotNull(model);
        Assert.assertNull(model.getPhoneNumber());
        Assert.assertEquals("Care giver Name", model.getName());
        Assert.assertEquals("Care Giver", model.getRole());
    }


}
