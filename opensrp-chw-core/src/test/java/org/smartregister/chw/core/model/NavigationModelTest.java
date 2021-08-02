package org.smartregister.chw.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "org.smartregister.chw.core.model.NavigationModel")

public class NavigationModelTest {

    NavigationModel navigationModel = NavigationModel.getInstance();

    @Mock
    NavigationModel.Flavor flavor;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void assertGetInstanceIsNotNull(){
        mockStatic(NavigationModel.class);
        when(NavigationModel.getInstance()).thenReturn(navigationModel);
        Assert.assertEquals(navigationModel, NavigationModel.getInstance());
    }


    @Test
    public void verifyGetNavigationItemsIsDelegatedToFlavorGetNavigationItems(){
        navigationModel.setNavigationFlavor(flavor);
        List<NavigationOption> modles= navigationModel.getNavigationItems();
        System.out.println("size "+modles.size());
        Mockito.verify(flavor).getNavigationItems();
    }

    @Test
    public void assertGetCurrentuserIsNotNull(){
        Assert.assertNotNull(navigationModel.getCurrentUser());
    }

}
