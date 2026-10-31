package com.poultryledger.repository;
import com.poultryledger.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("select s from Sale s join fetch s.henType order by s.date desc, s.id desc") List<Sale> findAllWithHenType();
    List<Sale> findAllByOrderByDateDescIdDesc();
}
