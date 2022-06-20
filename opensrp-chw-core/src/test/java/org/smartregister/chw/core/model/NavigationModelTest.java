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


import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "org.smartregister.chw.core.model.NavigationModel")

public class NavigationModelTest {

    @Mock
    private NavigationModel.Flavor flavor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void assertGetInstanceIsNotNull() {
        NavigationModel navigationModel = NavigationModel.getInstance();
        mockStatic(NavigationModel.class);
        when(NavigationModel.getInstance()).thenReturn(navigationModel);
        Assert.assertEquals(navigationModel, NavigationModel.getInstance());
    }


    @Test
    public void verifyGetNavigationItemsIsDelegatedToFlavorGetNavigationItems() {
        NavigationModel navigationModel = NavigationModel.getInstance();
        navigationModel.setNavigationFlavor(flavor);
        navigationModel.getNavigationItems();
        Mockito.verify(flavor).getNavigationItems();
    }

    @Test
    public void assertGetCurrentuserIsNotNull() {
        NavigationModel navigationModel = NavigationModel.getInstance();
        Assert.assertNotNull(navigationModel.getCurrentUser());
    }

}
