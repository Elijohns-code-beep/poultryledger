package com.poultryledger.repository;
import com.poultryledger.entity.ChickGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.*;
public interface ChickGroupRepository extends JpaRepository<ChickGroup, Long> {
    Optional<ChickGroup> findByCodeNameIgnoreCase(String codeName);
    @Query("select distinct g from ChickGroup g left join fetch g.events order by g.id desc") List<ChickGroup> findAllWithEvents();
    @Query("select distinct g from ChickGroup g left join fetch g.events where g.id = :id") Optional<ChickGroup> findByIdWithEvents(Long id);
    List<ChickGroup> findAllByOrderByIdDesc();
}
