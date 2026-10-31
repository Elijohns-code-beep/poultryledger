package com.poultryledger.service;

import com.poultryledger.entity.ChickGroup;
import com.poultryledger.entity.Sitting;
import com.poultryledger.repository.ChickGroupRepository;
import com.poultryledger.repository.SittingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class SittingService {
    private final SittingRepository sittingRepository;
    private final ChickGroupRepository groupRepository;
    private static final DateTimeFormatter CODE_FORMAT = DateTimeFormatter.ofPattern("dMMMyy", Locale.ENGLISH);
    public SittingService(SittingRepository sittingRepository, ChickGroupRepository groupRepository) { this.sittingRepository=sittingRepository; this.groupRepository=groupRepository; }
    public LocalDate expectedHatchDate(LocalDate dateGiven) { return dateGiven.plusDays(21); }
    public String baseCode(LocalDate date) { return date.format(CODE_FORMAT).toUpperCase(Locale.ENGLISH); }
    public String uniqueCode(LocalDate date, Long sittingId) {
        String base=baseCode(date); String candidate=base; int suffix=2;
        boolean sittingConflict;
        do {
            final String candidateForCheck = candidate;
            sittingConflict = sittingRepository.findAll().stream().anyMatch(s -> candidateForCheck.equalsIgnoreCase(s.getChickGroupCode()) && (sittingId == null || !s.getId().equals(sittingId)));
            if (sittingConflict) candidate=base+"-"+(suffix++);
        } while (sittingConflict);
        boolean groupConflict;
        do {
            final String candidateForCheck = candidate;
            groupConflict = groupRepository.findByCodeNameIgnoreCase(candidateForCheck).filter(g -> g.getSitting()==null || sittingId==null || g.getSitting().getId()==null || !g.getSitting().getId().equals(sittingId)).isPresent();
            if (groupConflict) candidate=base+"-"+(suffix++);
        } while (groupConflict);
        return candidate;
    }
    @Transactional
    public Sitting save(Sitting sitting) {
        sitting.setExpectedHatchDate(expectedHatchDate(sitting.getDateGiven()));
        if (sitting.getChicksHatched() != null && sitting.getChicksHatched() > 0 && sitting.getActualHatchDate() == null) throw new IllegalArgumentException("Enter the actual hatch date when chicks hatched is recorded.");
        LocalDate codeDate = sitting.getChicksHatched() != null && sitting.getChicksHatched() > 0 && sitting.getActualHatchDate() != null ? sitting.getActualHatchDate() : sitting.getExpectedHatchDate();
        sitting.setChickGroupCode(uniqueCode(codeDate, sitting.getId()));
        Sitting saved=sittingRepository.save(sitting);
        if (saved.getChicksHatched() != null && saved.getChicksHatched() > 0) {
            ChickGroup group=saved.getChickGroup();
            if (group == null) group=new ChickGroup(saved.getChickGroupCode(), saved.getChicksHatched());
            group.setCodeName(saved.getChickGroupCode()); group.setStartingCount(saved.getChicksHatched()); group.setSitting(saved); groupRepository.save(group); saved.setChickGroup(group); sittingRepository.save(saved);
        }
        return saved;
    }
}
