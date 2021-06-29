package org.smartregister.chw.core.utils;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.smartregister.domain.Alert;
import org.smartregister.domain.AlertStatus;
import org.smartregister.immunization.ImmunizationLibrary;
import org.smartregister.immunization.db.VaccineRepo;
import org.smartregister.immunization.domain.Vaccine;
import org.smartregister.immunization.domain.VaccineSchedule;
import org.smartregister.immunization.domain.VaccineWrapper;
import org.smartregister.immunization.util.VaccineCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImmunizationLibrary.class)
public class VisitVaccineUtilTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private Map<String, VaccineCache> vaccineMapMock;

    @Before
    public void setUp(){
        mockStatic(ImmunizationLibrary.class);
    }

    @Test
    public void testGetAllVaccinesGetsAllWomanAndChild() {
        when(vaccineMapMock.get(anyString()))
                .thenReturn(new VaccineCache());
        when(ImmunizationLibrary.getVaccineCacheMap())
                .thenReturn(vaccineMapMock);

        VisitVaccineUtil.getAllVaccines();
        verify(vaccineMapMock).get(eq("woman"));
        verify(vaccineMapMock).get(eq("child"));
    }

    @Test
    public void testWrapVaccine(){
        VaccineRepo.Vaccine vaccine = VaccineRepo.Vaccine.bcg;
        Alert alert = mock(Alert.class);
        VaccineWrapper wrapped = VisitVaccineUtil.wrapVaccine(vaccine, alert);
        Assert.assertEquals(wrapped.getVaccine(), vaccine);
        Assert.assertEquals(wrapped.getName(), vaccine.display());
        Assert.assertEquals(wrapped.getDefaultName(), vaccine.display());
        Assert.assertEquals(wrapped.getAlert(), alert);
    }

    @Test
    public void testGroupByTypeReturnsGrouped(){
        Alert alert = spy(new Alert("case id", "case scheduled name", "visit code", AlertStatus.upcoming, "startDate", "expiryDate"));
        String type = "case scheduled nam";
        List<Alert> alerts  = Collections.singletonList(alert);
        Map<String, List<Alert>> grouped = VisitVaccineUtil.groupByType(alerts);
        Assert.assertTrue(grouped.containsKey(type));
    }

    @Test
    public void testGetInMemoryAlertsGetsOfflineAlerts(){
        VaccineSchedule vaccineSchedule = mock(VaccineSchedule.class);
        HashMap<String, VaccineSchedule> tmpMap = new HashMap<>();
        tmpMap.put("inner", vaccineSchedule);
        String vaccineCategory = "outer";
        HashMap<String, HashMap<String, VaccineSchedule>> vaccineSchedules = new HashMap<>();
        vaccineSchedules.put(vaccineCategory, tmpMap);
        String baseEntityId = "5e90a36d-1b33-4fbd-a357-2f9410df44c6";
        DateTime dob = DateTime.now();
        List<Vaccine> issuedVaccines = new ArrayList<>();

        VisitVaccineUtil.getInMemoryAlerts(vaccineSchedules, baseEntityId, dob, vaccineCategory, issuedVaccines);
        verify(vaccineSchedule, atLeastOnce()).getOfflineAlert(eq(baseEntityId), eq(dob.toDate()), eq(issuedVaccines));
    }
}
