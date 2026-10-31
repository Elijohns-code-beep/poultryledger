package com.poultryledger.repository;
import com.poultryledger.entity.Sitting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SittingRepository extends JpaRepository<Sitting, Long> { List<Sitting> findAllByOrderByDateGivenDesc(); }
