package org.smartregister.chw.core.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.contract.AncMemberProfileContract;
import org.smartregister.chw.core.shadows.ChwCoreUtilsShadowHelper;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.FamilyUtilsShadowUtil;
import org.smartregister.chw.core.shadows.FormUtilsShadowHelper;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.domain.Task;
import org.smartregister.repository.AllSharedPreferences;

import java.util.HashSet;
import java.util.Set;

@Config(shadows = {ChwCoreUtilsShadowHelper.class, FamilyUtilsShadowUtil.class, FormUtilsShadowHelper.class, ContextShadow.class})
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
        memberObject.setLastMenstrualPeriod("01-01-2022");
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
    public void testStartAncDangerSignsOutcomeForm() {
        ancMemberProfilePresenter.startAncDangerSignsOutcomeForm(memberObject);
        Mockito.verify(ancMemberProfileView, Mockito.atLeastOnce()).startFormActivity(Mockito.any());
    }

}