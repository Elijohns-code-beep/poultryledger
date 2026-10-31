package com.poultryledger.service;

import com.poultryledger.entity.Sale;
import com.poultryledger.repository.ChannelPriceRepository;
import com.poultryledger.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class SaleServiceTest {
    @Test void saleTotalMultipliesEggsByStoredPrice() {
        Sale sale = new Sale(); sale.setQuantityEggs(12); sale.setPricePerEgg(new BigDecimal("16.00"));
        SaleService service = new SaleService(mock(SaleRepository.class), mock(ChannelPriceRepository.class));
        assertEquals(new BigDecimal("192.00"), service.totalAmount(sale));
    }
}
