package org.smartregister.chw.core.utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.smartregister.immunization.ImmunizationLibrary;
import org.smartregister.immunization.util.VaccineCache;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

}