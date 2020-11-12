package org.smartregister.chw.core.sync;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.domain.Client;
import org.smartregister.domain.Event;
import org.smartregister.domain.db.EventClient;
import org.smartregister.domain.jsonmapping.ClientClassification;
import org.smartregister.domain.jsonmapping.Table;
import org.smartregister.immunization.domain.Vaccine;
import org.smartregister.immunization.repository.VaccineRepository;

import java.util.ArrayList;
import java.util.List;

public class CoreClientProcessorTest extends BaseUnitTest {

    @Mock
    private ClientClassification classification;

    @Mock
    private Table vaccineTable;

    @Mock
    private Table serviceTable;

    @Mock
    private Context context;

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

    @Test
    public void testProcessClient() throws Exception {
        CoreClientProcessor processor = Mockito.spy(new CoreClientProcessor(context));
        Mockito.doNothing().when(processor).processEvents(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        List<EventClient> eventClients = new ArrayList<>();
        Event event = new Event();
        event.setEventType("Remove Family");
        EventClient eventClient = new EventClient(event, Mockito.mock(Client.class));
        eventClients.add(eventClient);
        eventClients.add(eventClient);

        processor.processClient(eventClients);
        Mockito.verify(processor, Mockito.times(2)).processEvents(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }
}
