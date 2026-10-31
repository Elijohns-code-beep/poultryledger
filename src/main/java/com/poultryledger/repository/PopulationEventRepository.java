package com.poultryledger.repository;
import com.poultryledger.entity.PopulationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface PopulationEventRepository extends JpaRepository<PopulationEvent, Long> {
    @Query("select e from PopulationEvent e join fetch e.henType order by e.date desc, e.id desc") List<PopulationEvent> findAllWithHenType();
    List<PopulationEvent> findAllByOrderByDateDescIdDesc();
    List<PopulationEvent> findByHenTypeIdOrderByDateAscIdAsc(Long henTypeId);
}
