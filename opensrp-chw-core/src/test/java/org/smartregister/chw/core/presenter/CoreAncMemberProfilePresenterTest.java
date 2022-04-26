package org.smartregister.chw.core.presenter;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.contract.AncMemberProfileContract;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.FormUtilsShadowHelper;
import org.smartregister.chw.core.shadows.LocationHelperShadowHelper;
import org.smartregister.chw.core.shadows.LocationPickerViewShadowHelper;
import org.smartregister.chw.core.shadows.UtilsShadowUtil;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.chw.core.utils.FormUtils;
import org.smartregister.domain.Task;
import org.smartregister.repository.AllSharedPreferences;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

@Config(shadows = {UtilsShadowUtil.class, FormUtilsShadowHelper.class, ContextShadow.class})
public class CoreAncMemberProfilePresenterTest extends BaseUnitTest {

    @Mock
    private AncMemberProfileContract.View ancMemberProfileView;

    @Mock
    private AncMemberProfileContract.Interactor interactor;

    private MemberObject memberObject = new MemberObject();

    private String baseEntityId = "some-base0ent-id";

    private CoreAncMemberProfilePresenter ancMemberProfilePresenter;

    @Mock
    private AllSharedPreferences allSharedPreferences;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        memberObject.setBaseEntityId(baseEntityId);
        ancMemberProfilePresenter = new CoreAncMemberProfilePresenter(ancMemberProfileView, interactor, memberObject);
    }

    @Test
    public void setClientTasks() {
        Set<Task> taskSet = new HashSet<>();
        ancMemberProfilePresenter.setClientTasks(taskSet);
        Mockito.verify(ancMemberProfileView, Mockito.atLeastOnce()).setClientTasks(taskSet);
    }

    @Test
    public void getEntityId() {
        Assert.assertEquals(ancMemberProfilePresenter.getEntityId(), baseEntityId);
    }

    @Test
    public void createReferralEvent() throws Exception {
        String jsonString = "{encounter_type:'Sample form'}";
        ancMemberProfilePresenter.createReferralEvent(allSharedPreferences, jsonString);
        Mockito.verify(interactor, Mockito.atLeastOnce()).createReferralEvent(allSharedPreferences, jsonString, baseEntityId);
    }

    @Test
    public void createAncDangerSignsOutcomeEvent() throws Exception {
        String jsonString = "{encounter_type:'Sample form'}";
        ancMemberProfilePresenter.createAncDangerSignsOutcomeEvent(allSharedPreferences, jsonString, baseEntityId);
        Mockito.verify(interactor, Mockito.atLeastOnce()).createAncDangerSignsOutcomeEvent(allSharedPreferences, jsonString, baseEntityId);
    }

    @Test
    public void fetchTasks() {
        ancMemberProfilePresenter.fetchTasks();
        Mockito.verify(interactor, Mockito.atLeastOnce()).getClientTasks(CoreConstants.REFERRAL_PLAN_ID, baseEntityId, ancMemberProfilePresenter);
    }

    @Test
    public void testStartAncDangerSignsOutcomeForm() throws JSONException {
//        ancMemberProfilePresenter = Mockito.mock(CoreAncMemberProfilePresenter.class, Mockito.CALLS_REAL_METHODS);
//        ReflectionHelpers.setField(ancMemberProfilePresenter, "view", new WeakReference<>(ancMemberProfileView));
        ancMemberProfilePresenter.startAncDangerSignsOutcomeForm(Mockito.mock(MemberObject.class));
        Mockito.verify(ancMemberProfileView, Mockito.atLeastOnce()).startFormActivity(Mockito.any());

    }

}