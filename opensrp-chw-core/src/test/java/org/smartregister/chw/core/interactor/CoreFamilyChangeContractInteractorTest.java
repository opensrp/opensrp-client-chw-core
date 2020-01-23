package org.smartregister.chw.core.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.smartregister.Context;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.contract.FamilyChangeContract;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.family.FamilyLibrary;
import org.smartregister.family.domain.FamilyMetadata;
import org.smartregister.family.util.AppExecutors;
import org.smartregister.reporting.ReportingLibrary;

import java.util.concurrent.Executor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ReportingLibrary.class, FamilyLibrary.class})
public class CoreFamilyChangeContractInteractorTest implements Executor {

    private CoreFamilyChangeContractInteractor interactor;

    @Mock
    private CoreChwApplication coreApplication;

    @Mock
    private FamilyChangeContract.Presenter presenter;

    @Mock
    private FamilyLibrary familyLibrary;

    @Mock
    private Context context;

    @Mock
    private FamilyMetadata metadata;

    @Mock
    private CoreFamilyChangeContractInteractor.Flavor flavor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        AppExecutors appExecutors = new AppExecutors(this, this, this);
        interactor = Mockito.spy(new CoreFamilyChangeContractInteractor(appExecutors) {
            @Override
            protected void setCoreChwApplication() {
                this.coreChwApplication = coreApplication;
            }

            @Override
            protected void setFlavour() {
                this.flavor = flavor;
            }
        });
    }


    @Test
    public void testGetAdultMembersExcludeHOF() {
        PowerMockito.mockStatic(FamilyLibrary.class);

        Whitebox.setInternalState(interactor, "flavor", flavor);
        CommonRepository commonRepository = Mockito.mock(CommonRepository.class);
        CommonPersonObject personObject = Mockito.mock(CommonPersonObject.class);
        Mockito.doReturn(personObject).when(commonRepository).findByBaseEntityId(Mockito.anyString());

        Mockito.doReturn(context).when(familyLibrary).context();
        Mockito.doReturn(commonRepository).when(context).commonrepository(Mockito.any());

        metadata.familyRegister = Mockito.mock(FamilyMetadata.FamilyRegister.class);
        metadata.familyMemberRegister = Mockito.mock(FamilyMetadata.FamilyMemberRegister.class);
        Mockito.doReturn(metadata).when(familyLibrary).metadata();

        PowerMockito.when(FamilyLibrary.getInstance()).thenReturn(familyLibrary);

        interactor.getAdultMembersExcludeHOF("123445", presenter);
        Mockito.verify(presenter).renderAdultMembersExcludeHOF(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void testGetAdultMembersExcludePCG() {
        PowerMockito.mockStatic(FamilyLibrary.class);

        Whitebox.setInternalState(interactor, "flavor", flavor);
        CommonRepository commonRepository = Mockito.mock(CommonRepository.class);
        CommonPersonObject personObject = Mockito.mock(CommonPersonObject.class);
        Mockito.doReturn(personObject).when(commonRepository).findByBaseEntityId(Mockito.anyString());

        Mockito.doReturn(context).when(familyLibrary).context();
        Mockito.doReturn(commonRepository).when(context).commonrepository(Mockito.any());

        metadata.familyRegister = Mockito.mock(FamilyMetadata.FamilyRegister.class);
        metadata.familyMemberRegister = Mockito.mock(FamilyMetadata.FamilyMemberRegister.class);
        Mockito.doReturn(metadata).when(familyLibrary).metadata();

        PowerMockito.when(FamilyLibrary.getInstance()).thenReturn(familyLibrary);

        interactor.getAdultMembersExcludePCG("123445", presenter);
        Mockito.verify(presenter).renderAdultMembersExcludePCG(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
