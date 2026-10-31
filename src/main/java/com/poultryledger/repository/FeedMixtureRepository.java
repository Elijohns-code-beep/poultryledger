package com.poultryledger.repository;
import com.poultryledger.entity.FeedMixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
public interface FeedMixtureRepository extends JpaRepository<FeedMixture, Long> {
    Optional<FeedMixture> findByCodeNameIgnoreCase(String codeName);
    @Query("select distinct m from FeedMixture m left join fetch m.ingredients order by m.date desc, m.id desc")
    List<FeedMixture> findAllWithIngredients();
    @Query("select distinct m from FeedMixture m left join fetch m.ingredients where m.id = :id")
    Optional<FeedMixture> findByIdWithIngredients(Long id);
}
