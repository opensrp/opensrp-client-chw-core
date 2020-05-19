package org.smartregister.chw.core.interactor;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseRobolectricTest;

public class NavigationInteractorTest extends BaseRobolectricTest {

    private NavigationInteractor navigationInteractor;

    @Before
    public void setUp(){
        navigationInteractor = NavigationInteractor.getInstance();
        navigationInteractor.setApplication(ApplicationProvider.getApplicationContext());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getRegisterCount() {
        Assert.assertNotNull(navigationInteractor);
    }
}