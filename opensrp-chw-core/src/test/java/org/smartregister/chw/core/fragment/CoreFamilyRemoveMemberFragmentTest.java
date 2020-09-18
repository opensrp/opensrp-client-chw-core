package org.smartregister.chw.core.fragment;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.Robolectric;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.family.contract.FamilyProfileMemberContract;
import org.smartregister.family.presenter.BaseFamilyProfileMemberPresenter;
import org.smartregister.family.util.DBConstants;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CoreFamilyRemoveMemberFragmentTest extends BaseUnitTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    public RecyclerView clientsView;
    @Mock
    private Context context;
    @Mock
    private CommonRepository commonRepository;
    private FragmentActivity activity;
    @Captor
    private ArgumentCaptor<RecyclerViewPaginatedAdapter> adapterArgumentCaptor;
    private CoreFamilyRemoveMemberFragment familyProfileMemberFragment;

    private BaseFamilyProfileMemberPresenter familyProfileMemberPresenter;

    @Before
    public void setUp() throws Exception {
        Context.bindtypes = new ArrayList<>();
        familyProfileMemberFragment = Mockito.mock(CoreFamilyRemoveMemberFragment.class, Mockito.CALLS_REAL_METHODS);
        CoreLibrary.init(context);
        when(context.commonrepository(anyString())).thenReturn(commonRepository);
        familyProfileMemberPresenter = new BaseFamilyProfileMemberPresenter(Mockito.mock(FamilyProfileMemberContract.View.class),
                Mockito.mock(FamilyProfileMemberContract.Model.class), null, "familybaseid", "Head", "Caregiver");
        CoreLibrary.init(context);
        when(context.commonrepository(anyString())).thenReturn(commonRepository);
        activity = Robolectric.buildActivity(AppCompatActivity.class).create().resume().get();
        Context.bindtypes = new ArrayList<>();
        Whitebox.setInternalState(familyProfileMemberFragment, "searchView", new EditText(activity));
        Whitebox.setInternalState(familyProfileMemberFragment, "clientsView", clientsView);
        Whitebox.setInternalState(familyProfileMemberFragment, "presenter", familyProfileMemberPresenter);
        activity.setContentView(org.smartregister.family.R.layout.activity_family_profile);
        SyncStatusBroadcastReceiver.init(activity);
    }

    @Test
    public void getMainCondition() {
        assertEquals(familyProfileMemberFragment.getMainCondition(), String.format(" %s = '%s' and %s is null ", DBConstants.KEY.OBJECT_RELATIONAL_ID, "familybaseid", DBConstants.KEY.DATE_REMOVED));
    }

    @Test
    public void getDefaultSortQuery() {
        assertEquals(familyProfileMemberFragment.getDefaultSortQuery(), DBConstants.KEY.DOD + ", " + DBConstants.KEY.DOB + " ASC ");
    }

    @Test
    public void setFamilyHead() {
        String familyHead = "my head";
        familyProfileMemberFragment.setFamilyHead(familyHead);
        assertEquals(familyHead, familyProfileMemberPresenter.getFamilyHead());
    }

    @Test
    public void setPrimaryCaregiver() {
        String primaryCareGiver = "mrs Caregiver";
        familyProfileMemberFragment.setPrimaryCaregiver(primaryCareGiver);
        assertEquals(primaryCareGiver, familyProfileMemberPresenter.getPrimaryCaregiver());
    }

    @Test
    public void presenter() {
        assertNotNull(familyProfileMemberFragment.presenter());
    }

    @Test
    public void testInitializeAdapter() {
        when(familyProfileMemberFragment.getActivity()).thenReturn(activity);
        familyProfileMemberFragment.initializeAdapter(new HashSet<>(), familyProfileMemberPresenter.getFamilyHead(), familyProfileMemberPresenter.getPrimaryCaregiver());
        verify(clientsView).setAdapter(adapterArgumentCaptor.capture());
        assertNotNull(adapterArgumentCaptor.getValue());
        assertEquals(100, adapterArgumentCaptor.getValue().currentlimit);

    }
}