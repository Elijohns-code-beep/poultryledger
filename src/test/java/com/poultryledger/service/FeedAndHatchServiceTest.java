package com.poultryledger.service;

import com.poultryledger.entity.FeedIngredient;
import com.poultryledger.entity.FeedMixture;
import com.poultryledger.repository.ChickGroupRepository;
import com.poultryledger.repository.SittingRepository;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class FeedAndHatchServiceTest {
    @Test void costPerKgSumsIngredients() {
        FeedMixture mixture = new FeedMixture();
        mixture.addIngredient(new FeedIngredient("Maize", "Store", new BigDecimal("10"), new BigDecimal("500")));
        mixture.addIngredient(new FeedIngredient("Soya", "Store", new BigDecimal("5"), new BigDecimal("400")));
        FeedMixtureService service = new FeedMixtureService(mock(com.poultryledger.repository.FeedMixtureRepository.class));
        assertEquals(new BigDecimal("60.00"), service.costPerKg(mixture));
    }

    @Test void expectedHatchDateIsTwentyOneDaysLater() {
        SittingService service = new SittingService(mock(SittingRepository.class), mock(ChickGroupRepository.class));
        assertEquals(LocalDate.of(2026, 8, 22), service.expectedHatchDate(LocalDate.of(2026, 8, 1)));
    }

    @Test void chickGroupCodeUsesUppercaseDayMonthYear() {
        SittingService service = new SittingService(mock(SittingRepository.class), mock(ChickGroupRepository.class));
        assertEquals("1AUG26", service.baseCode(LocalDate.of(2026, 8, 1)));
    }
}
