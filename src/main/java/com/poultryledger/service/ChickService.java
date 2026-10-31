package com.poultryledger.service;

import com.poultryledger.entity.*;
import com.poultryledger.repository.ChickEventRepository;
import com.poultryledger.repository.ChickGroupRepository;
import com.poultryledger.repository.HenTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class ChickService {
    private final ChickGroupRepository groupRepository;
    private final ChickEventRepository eventRepository;
    private final HenTypeRepository henTypeRepository;
    private final PopulationService populationService;
    public ChickService(ChickGroupRepository groupRepository, ChickEventRepository eventRepository, HenTypeRepository henTypeRepository, PopulationService populationService) { this.groupRepository=groupRepository; this.eventRepository=eventRepository; this.henTypeRepository=henTypeRepository; this.populationService=populationService; }
    @Transactional(readOnly = true)
    public int chicksAliveNow(ChickGroup group) { return group.getStartingCount() + group.getEvents().stream().mapToInt(ChickEvent::getChange).sum(); }
    @Transactional
    public ChickGroup saveGroup(ChickGroup group) { group.getEvents().forEach(e -> e.setChickGroup(group)); return groupRepository.save(group); }
    @Transactional
    public ChickEvent addEvent(ChickGroup group, ChickEvent event) { if (group.getStatus()==GroupStatus.CLOSED) throw new IllegalArgumentException("Closed chick groups cannot receive new events."); if (chicksAliveNow(group)+event.getChange()<0) throw new IllegalArgumentException("This event would make the group count go below zero."); event.setChickGroup(group); return eventRepository.save(event); }
    @Transactional
    public void graduate(ChickGroup group, Long henTypeId, LocalDate date) { if (group.getStatus()==GroupStatus.CLOSED) throw new IllegalArgumentException("This chick group has already been moved to the adult flock."); int alive=chicksAliveNow(group); if (alive<=0) throw new IllegalArgumentException("A group with zero chicks cannot be moved."); HenType type=henTypeRepository.findById(henTypeId).orElseThrow(); populationService.saveEvent(new PopulationEvent(date,type,alive,"Graduated from chicks")); group.setStatus(GroupStatus.CLOSED); groupRepository.save(group); }
    public List<ChickGroup> groups() { return groupRepository.findAllWithEvents(); }
}
