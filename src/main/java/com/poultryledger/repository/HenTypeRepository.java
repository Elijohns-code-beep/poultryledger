package com.poultryledger.repository;

import com.poultryledger.entity.HenType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HenTypeRepository extends JpaRepository<HenType, Long> {
    Optional<HenType> findByNameIgnoreCase(String name);
}
