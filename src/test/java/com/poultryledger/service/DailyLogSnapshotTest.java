package com.poultryledger.service;

import com.poultryledger.entity.*;
import com.poultryledger.repository.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DailyLogSnapshotTest {
    @Test void newMixtureUsageCopiesCurrentCostPerKgToRow() {
        FeedMixture mixture = new FeedMixture();
        mixture.setCodeName("layers1");
        mixture.addIngredient(new FeedIngredient("Maize", "Store", new BigDecimal("10"), new BigDecimal("500")));
        DailyLogRepository logs = mock(DailyLogRepository.class);
        FeedMixtureRepository mixtures = mock(FeedMixtureRepository.class);
        when(mixtures.findByCodeNameIgnoreCase("layers1")).thenReturn(Optional.of(mixture));
        when(logs.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        DailyLogService service = new DailyLogService(logs, mixtures);
        DailyLog log = new DailyLog(); log.setDate(LocalDate.of(2026, 8, 1));
        FeedUsage usage = new FeedUsage(); usage.setFeedCode("layers1"); usage.setQuantityKg(new BigDecimal("2")); log.getFeedUsages().add(usage);
        service.save(log);
        assertEquals(new BigDecimal("50.00"), usage.getUnitCostAtTime());
        mixture.getIngredients().get(0).setPrice(new BigDecimal("900"));
        assertEquals(new BigDecimal("50.00"), usage.getUnitCostAtTime());
    }
}
