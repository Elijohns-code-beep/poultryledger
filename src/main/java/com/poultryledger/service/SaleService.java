package com.poultryledger.service;

import com.poultryledger.entity.ChannelPrice;
import com.poultryledger.entity.Sale;
import com.poultryledger.repository.ChannelPriceRepository;
import com.poultryledger.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ChannelPriceRepository priceRepository;
    public SaleService(SaleRepository saleRepository, ChannelPriceRepository priceRepository) { this.saleRepository=saleRepository; this.priceRepository=priceRepository; }
    public BigDecimal totalAmount(Sale sale) { return sale.getPricePerEgg().multiply(BigDecimal.valueOf(sale.getQuantityEggs())).setScale(2, RoundingMode.HALF_UP); }
    public BigDecimal defaultPrice(String channel) { return priceRepository.findByChannelIgnoreCase(channel).map(ChannelPrice::getPrice).orElse(BigDecimal.ZERO.setScale(2)); }
    @Transactional public Sale save(Sale sale) { if ("Other".equalsIgnoreCase(sale.getSoldTo())) { if (sale.getBuyerName()==null || sale.getBuyerName().isBlank()) throw new IllegalArgumentException("Enter who the Other sale was sold to."); } else sale.setBuyerName(null); if (sale.getPricePerEgg()==null) sale.setPricePerEgg(defaultPrice(sale.getSoldTo())); return saleRepository.save(sale); }
    public List<Sale> list() { return saleRepository.findAllWithHenType(); }
}
