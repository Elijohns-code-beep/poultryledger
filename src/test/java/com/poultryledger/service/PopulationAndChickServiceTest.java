package com.poultryledger.service;

import com.poultryledger.entity.*;
import com.poultryledger.repository.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PopulationAndChickServiceTest {
    @Test void chicksAliveNowAddsPositiveAndNegativeEvents() {
        ChickGroup group = new ChickGroup("1AUG26", 25);
        ChickEvent died = new ChickEvent(); died.setChange(-2); group.getEvents().add(died);
        ChickEvent sold = new ChickEvent(); sold.setChange(-3); group.getEvents().add(sold);
        ChickService service = new ChickService(mock(ChickGroupRepository.class), mock(ChickEventRepository.class), mock(HenTypeRepository.class), mock(PopulationService.class));
        assertEquals(20, service.chicksAliveNow(group));
    }

    @Test void populationTotalSumsEachHenTypeCurrentPopulation() {
        HenType kienyeji = mock(HenType.class); when(kienyeji.getId()).thenReturn(1L); when(kienyeji.getStartingPopulation()).thenReturn(10);
        HenType improved = mock(HenType.class); when(improved.getId()).thenReturn(2L); when(improved.getStartingPopulation()).thenReturn(5);
        PopulationEvent event = new PopulationEvent(); event.setHenType(kienyeji); event.setChange(3);
        PopulationEventRepository events = mock(PopulationEventRepository.class);
        when(events.findByHenTypeIdOrderByDateAscIdAsc(1L)).thenReturn(List.of(event));
        when(events.findByHenTypeIdOrderByDateAscIdAsc(2L)).thenReturn(List.of());
        HenTypeRepository types = mock(HenTypeRepository.class);
        PopulationService service = new PopulationService(types, events);
        // The test focuses on the service's additive rule; repository IDs are not assigned by the entity.
        assertEquals(18, service.totalPopulation(List.of(kienyeji, improved)));
    }
}
