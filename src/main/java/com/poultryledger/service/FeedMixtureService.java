package com.poultryledger.service;

import com.poultryledger.entity.FeedIngredient;
import com.poultryledger.entity.FeedMixture;
import com.poultryledger.repository.FeedMixtureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FeedMixtureService {
    private final FeedMixtureRepository repository;
    public FeedMixtureService(FeedMixtureRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public BigDecimal totalQuantity(FeedMixture mixture) { return mixture.getIngredients().stream().map(FeedIngredient::getQuantityKg).filter(v -> v != null).reduce(BigDecimal.ZERO, BigDecimal::add); }
    @Transactional(readOnly = true)
    public BigDecimal totalPrice(FeedMixture mixture) { return mixture.getIngredients().stream().map(FeedIngredient::getPrice).filter(v -> v != null).reduce(BigDecimal.ZERO, BigDecimal::add); }
    @Transactional(readOnly = true)
    public BigDecimal costPerKg(FeedMixture mixture) { BigDecimal qty=mixture.getIngredients().stream().map(FeedIngredient::getQuantityKg).filter(v -> v != null).reduce(BigDecimal.ZERO, BigDecimal::add); BigDecimal price=mixture.getIngredients().stream().map(FeedIngredient::getPrice).filter(v -> v != null).reduce(BigDecimal.ZERO, BigDecimal::add); return qty.signum()==0 ? BigDecimal.ZERO.setScale(2) : price.divide(qty, 2, RoundingMode.HALF_UP); }
    @Transactional
    public FeedMixture save(FeedMixture mixture) {
        if (mixture.getIngredients() == null) mixture.setIngredients(new java.util.ArrayList<>());
        mixture.getIngredients().removeIf(i -> i.getName() == null || i.getName().isBlank());
        mixture.getIngredients().forEach(i -> i.setMixture(mixture));
        repository.findByCodeNameIgnoreCase(mixture.getCodeName()).ifPresent(existing -> { if (mixture.getId() == null || !existing.getId().equals(mixture.getId())) throw new IllegalArgumentException("Feed mixture code already exists."); });
        if (totalQuantity(mixture).signum() <= 0) throw new IllegalArgumentException("Add at least one ingredient with quantity greater than zero.");
        return repository.save(mixture);
    }
}
