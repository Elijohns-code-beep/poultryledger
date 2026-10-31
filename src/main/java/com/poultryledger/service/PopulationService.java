package com.poultryledger.service;

import com.poultryledger.entity.HenType;
import com.poultryledger.entity.PopulationEvent;
import com.poultryledger.repository.HenTypeRepository;
import com.poultryledger.repository.PopulationEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PopulationService {
    private final HenTypeRepository henTypeRepository;
    private final PopulationEventRepository eventRepository;
    public PopulationService(HenTypeRepository henTypeRepository, PopulationEventRepository eventRepository) { this.henTypeRepository=henTypeRepository; this.eventRepository=eventRepository; }
    public int currentPopulation(HenType type) { return type.getStartingPopulation() + eventRepository.findByHenTypeIdOrderByDateAscIdAsc(type.getId()).stream().mapToInt(PopulationEvent::getChange).sum(); }
    public int totalPopulation(List<HenType> types) { return types.stream().mapToInt(this::currentPopulation).sum(); }
    @Transactional
    public PopulationEvent saveEvent(PopulationEvent event) {
        int current=currentPopulation(event.getHenType());
        if (event.getId() != null) { PopulationEvent old=eventRepository.findById(event.getId()).orElse(null); if (old != null) current -= old.getChange(); }
        if (current + event.getChange() < 0) throw new IllegalArgumentException("This event would make the hen type total go below zero.");
        return eventRepository.save(event);
    }
    public List<PopulationEvent> events() { return eventRepository.findAllWithHenType(); }
    public int runningTotalAfter(PopulationEvent target) { int total=target.getHenType().getStartingPopulation(); for (PopulationEvent event : eventRepository.findByHenTypeIdOrderByDateAscIdAsc(target.getHenType().getId())) { total += event.getChange(); if (event.getId().equals(target.getId())) break; } return total; }
}
