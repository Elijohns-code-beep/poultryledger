package com.poultryledger.repository;
import com.poultryledger.entity.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.*;
public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
    Optional<DailyLog> findByDate(LocalDate date);
    @Query("select distinct d from DailyLog d left join fetch d.feedUsages where d.date = :date") Optional<DailyLog> findByDateWithFeedUsages(LocalDate date);
    @Query("select distinct d from DailyLog d left join fetch d.feedUsages order by d.date desc") List<DailyLog> findAllWithFeedUsages();
    List<DailyLog> findAllByOrderByDateDesc();
}
