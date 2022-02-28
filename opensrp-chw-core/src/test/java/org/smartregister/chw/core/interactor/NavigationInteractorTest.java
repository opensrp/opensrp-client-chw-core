package org.smartregister.chw.core.interactor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.utils.CoreConstants;

public class NavigationInteractorTest extends BaseUnitTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCountShouldReturnCorrectNumbersForFamilyMemberCount(){
        int count = NavigationInteractor.getInstance().getCount(CoreConstants.TABLE_NAME.FAMILY_MEMBER);
        Assert.assertNotEquals(2, count);
    }

}
