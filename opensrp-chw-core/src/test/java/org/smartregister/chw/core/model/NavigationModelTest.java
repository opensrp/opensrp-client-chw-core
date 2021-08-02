package org.smartregister.chw.core.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.utils.Assert;

public class NavigationModelTest {
    @Mock
    NavigationModel navigationModel;

    @Mock
    NavigationModel.Flavor flavor;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void assertGetInstanceIsNotNull(){
        Assert.notNull(NavigationModel.getInstance());
    }

    @Test
    public void assertFlavorIsNotNull(){

        Assert.notNull(navigationModel.);
    }
}
