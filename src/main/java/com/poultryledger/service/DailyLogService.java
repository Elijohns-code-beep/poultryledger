package com.poultryledger.service;

import com.poultryledger.entity.DailyLog;
import com.poultryledger.entity.FeedIngredient;
import com.poultryledger.entity.FeedMixture;
import com.poultryledger.entity.FeedUsage;
import com.poultryledger.repository.DailyLogRepository;
import com.poultryledger.repository.FeedMixtureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DailyLogService {
    private final DailyLogRepository logRepository;
    private final FeedMixtureRepository mixtureRepository;

    public DailyLogService(DailyLogRepository logRepository, FeedMixtureRepository mixtureRepository) {
        this.logRepository = logRepository;
        this.mixtureRepository = mixtureRepository;
    }

    public int totalEggs(DailyLog log) {
        return valueOrZero(log.getKienyejiEggs()) + valueOrZero(log.getImprovedKienyejiEggs());
    }

    @Transactional(readOnly = true)
    public BigDecimal totalFeedAmount(DailyLog log) {
        return log.getFeedUsages().stream().map(FeedUsage::getQuantityKg).filter(q -> q != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);
    }

    public BigDecimal feedCost(FeedUsage usage) {
        if (usage.getQuantityKg() == null || usage.getUnitCostAtTime() == null) return BigDecimal.ZERO.setScale(2);
        return usage.getQuantityKg().multiply(usage.getUnitCostAtTime()).setScale(2, RoundingMode.HALF_UP);
    }

    @Transactional(readOnly = true)
    public BigDecimal totalFeedCost(DailyLog log) {
        return log.getFeedUsages().stream().map(this::feedCost).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
    }

    @Transactional
    public DailyLog save(DailyLog incoming) {
        DailyLog old = incoming.getId() == null ? logRepository.findByDate(incoming.getDate()).orElse(null) : logRepository.findById(incoming.getId()).orElse(null);
        if (old != null && incoming.getId() == null) incoming.setFeedUsages(old.getFeedUsages());
        if (incoming.getFeedUsages() != null) {
            for (FeedUsage usage : incoming.getFeedUsages()) {
                if (usage.getQuantityKg() == null) usage.setQuantityKg(BigDecimal.ZERO);
                if ("Other".equalsIgnoreCase(usage.getFeedCode())) {
                    if (usage.getUnitCostAtTime() == null) throw new IllegalArgumentException("Enter a price per kg for Other feed.");
                } else if (usage.getFeedCode() != null && !usage.getFeedCode().isBlank()) {
                    FeedMixture mixture = mixtureRepository.findByCodeNameIgnoreCase(usage.getFeedCode()).orElseThrow(() -> new IllegalArgumentException("Choose an existing mixture or Other."));
                    boolean preserve = usage.getId() != null && usage.getUnitCostAtTime() != null;
                    if (!preserve) usage.setUnitCostAtTime(mixtureCost(mixture));
                }
                usage.setDailyLog(incoming);
            }
        }
        return logRepository.save(incoming);
    }

    private BigDecimal mixtureCost(FeedMixture mixture) {
        BigDecimal qty = mixture.getIngredients().stream().map(FeedIngredient::getQuantityKg).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal price = mixture.getIngredients().stream().map(FeedIngredient::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return qty.signum() == 0 ? BigDecimal.ZERO : price.divide(qty, 2, RoundingMode.HALF_UP);
    }

    public List<DailyLog> list() { return logRepository.findAllWithFeedUsages(); }

    private int valueOrZero(Integer value) { return value == null ? 0 : value; }
}
