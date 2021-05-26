package org.smartregister.chw.core.interactor;

import android.os.Build;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.FamilyLibraryShadowUtil;
import org.smartregister.chw.core.shadows.UtilsShadowUtil;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.family.FamilyLibrary;
import org.smartregister.family.domain.FamilyMetadata;
import org.smartregister.family.util.AppExecutors;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.contract.FamilyRemoveMemberContract;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import refutils.ReflectionHelper;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CoreFamilyRemoveMemberInteractorTest extends BaseUnitTest implements Executor {
    private CoreFamilyRemoveMemberInteractor interactor;
    @Mock
    private  FamilyRemoveMemberContract.Presenter presenter;
    @Mock
    private FamilyLibrary familyLibrary;
    @Mock
    private FamilyMetadata metadata;

    @Mock
    private Context context;
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

    @Test
    public void testProcessFamilyMemberCallsprocessMemberInPresenter(){

        CommonPersonObjectClient  commonPersonObjectClient = Mockito.mock(CommonPersonObjectClient.class);
        CommonRepository commonRepository = Mockito.mock(CommonRepository.class);
        CommonPersonObject personObject = Mockito.mock(CommonPersonObject.class);
        Mockito.doReturn(personObject).when(commonRepository).findByBaseEntityId(Mockito.anyString());
        Mockito.doReturn(context).when(familyLibrary).context();
        metadata.familyRegister = Mockito.mock(FamilyMetadata.FamilyRegister.class);
        Whitebox.setInternalState(metadata.familyRegister, "tableName", "ec_family");

        FamilyLibraryShadowUtil.setInstance(familyLibrary);
        interactor.processFamilyMember("12345",commonPersonObjectClient, presenter);
        verify(presenter).processMember(Mockito.any(), Mockito.any());
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
