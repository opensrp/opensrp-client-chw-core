package org.smartregister.chw.core.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HomeVisitIndicatorInfoTest {
    private final HomeVisitIndicatorInfo homeVisitIndicatorInfo = new HomeVisitIndicatorInfo();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private Date date;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        date = sdf.parse("2021-02-08");
    }
    @Test
    public void setBaseEntityIdAndGetBaseEntityIdTest (){
        homeVisitIndicatorInfo.setBaseEntityId("wertyuioi");
        assertEquals(homeVisitIndicatorInfo.getBaseEntityId(), "wertyuioi");
    }

    @Test
    public void setServiceAndGetServiceTest(){
      homeVisitIndicatorInfo.setService("oiu4567");
      assertEquals(homeVisitIndicatorInfo.getService(), "oiu4567");
    }

    @Test
    public void setServiceDateAndGetServiceDateTest() {
        homeVisitIndicatorInfo.setServiceDate(date);
        assertEquals(homeVisitIndicatorInfo.getServiceDate(), date);
    }

    @Test
    public void setServiceUpdateDateAndGetServiceUpdateDateTest() {
        homeVisitIndicatorInfo.setServiceUpdateDate(date);
        assertEquals(homeVisitIndicatorInfo.getServiceUpdateDate(), date);
    }

    @Test
    public void isServiceGivenAndSetServiceGivenTest() {
        homeVisitIndicatorInfo.setServiceGiven(true);
        assertTrue(homeVisitIndicatorInfo.isServiceGiven());
    }
    @Test
    public void setValueAndGetValueTest() {
        homeVisitIndicatorInfo.setValue("qwertyu12345");
        assertEquals(homeVisitIndicatorInfo.getValue(), "qwertyu12345");
    }
    @Test
    public void setLastHomeVisitDateAndGetLastHomeVisitDateTest() {
        homeVisitIndicatorInfo.setLastHomeVisitDate(date);
        assertEquals(homeVisitIndicatorInfo.getLastHomeVisitDate(), date);
    }
    @Test
    public void setUpdatedAtAndGetUpdatedAtTest() {
        homeVisitIndicatorInfo.setUpdatedAt(date);
        assertEquals(homeVisitIndicatorInfo.getUpdatedAt(), date);
    }
    @Test
    public void setCreatedAtAndGetCreatedAt() {
        homeVisitIndicatorInfo.setCreatedAt(date);
        assertEquals(homeVisitIndicatorInfo.getCreatedAt(), date);
    }

}
