package org.smartregister.chw.core.interactor;

import android.os.Build;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.annotation.Config;
import org.smartregister.Context;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.model.CommunityResponderModel;
import org.smartregister.chw.core.shadows.UtilsShadowUtil;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.family.activity.FamilyWizardFormActivity;
import org.smartregister.family.domain.FamilyMetadata;
import org.smartregister.family.util.AppExecutors;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.contract.FamilyRemoveMemberContract;
import org.smartregister.family.util.Utils;
import org.smartregister.view.activity.BaseProfileActivity;
import java.util.concurrent.Executor;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Config(shadows = {UtilsShadowUtil.class})
public class CoreFamilyRemoveMemberInteractorTest extends BaseUnitTest implements Executor {
    private CoreFamilyRemoveMemberInteractor interactor;


    @Mock
    private  FamilyRemoveMemberContract.Presenter presenter;

    @Mock
    private CoreChwApplication coreApplication;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        AppExecutors appExecutors = new AppExecutors(this, this, this);
        interactor = Mockito.spy(new CoreFamilyRemoveMemberInteractorChild(appExecutors));
    }

    @Test
    public void testRemoveMemberInteractorCallsMemberRemovedInPresenter(){
        interactor.removeMember("famId", "lastLocationId", new JSONObject(), presenter);
        verify(presenter).memberRemoved(null);
    }


    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public class CoreFamilyRemoveMemberInteractorChild extends CoreFamilyRemoveMemberInteractor{
        public CoreFamilyRemoveMemberInteractorChild(AppExecutors appExecutors) {
            super(appExecutors);
        }

        @Override
        protected void setCoreChwApplication() {
            this.coreChwApplication = coreApplication;
        }
    }
}
