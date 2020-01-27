package org.smartregister.chw.core.presenter;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.contract.CoreChildProfileContract;

public class CoreChildProfilePresenterTest {

    @Mock
    private CoreChildProfileContract.View view;

    @Mock
    private CoreChildProfileContract.Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}
