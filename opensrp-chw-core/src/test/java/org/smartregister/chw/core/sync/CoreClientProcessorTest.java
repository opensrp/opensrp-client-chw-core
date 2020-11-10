package org.smartregister.chw.core.sync;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.immunization.domain.Vaccine;
import org.smartregister.immunization.repository.VaccineRepository;

public class CoreClientProcessorTest extends BaseUnitTest {

    @Mock
    private VaccineRepository vaccineRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddVaccine() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("MEASLES 1");

        CoreClientProcessor.addVaccine(vaccineRepository, vaccine);
        Mockito.verify(vaccineRepository).add(vaccine);
        Mockito.verify(vaccineRepository).updateFtsSearch(Mockito.any());
    }
}
