package org.smartregister.chw.core.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CommunityResponderModelTest {

    private CommunityResponderModel communityResponderModel;

    private String responderName;
    private String responderPhoneNumber;
    private String responderLocation;
    private String id;

    @Before
    public void setUp() {
        communityResponderModel = Mockito.spy(CommunityResponderModel.class);
        responderName = "tester";
        responderPhoneNumber = "0726366371";
        responderLocation = "Location";
        id = "2345678";
    }
    @Test
    public void getIdTest(){
        communityResponderModel.setId(id);
        Assert.assertEquals(communityResponderModel.getId(), id);
    }
    @Test
    public void getResponderNameTest() {
        communityResponderModel.setResponderName(responderName);
        Assert.assertEquals(communityResponderModel.getResponderName(), responderName);
    }
    @Test
    public void getResponderPhoneNumberTest() {
        communityResponderModel.setResponderPhoneNumber(responderPhoneNumber);
        Assert.assertEquals(communityResponderModel.getResponderPhoneNumber(), responderPhoneNumber);
    }
    @Test
    public void getResponderLocationTest() {
        communityResponderModel.setResponderLocation(responderLocation);
        Assert.assertEquals(communityResponderModel.getResponderLocation(), responderLocation);
    }

}
