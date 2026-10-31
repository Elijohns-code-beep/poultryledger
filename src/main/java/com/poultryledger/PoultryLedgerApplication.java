package com.poultryledger;

import com.poultryledger.entity.ChannelPrice;
import com.poultryledger.entity.HenType;
import com.poultryledger.repository.ChannelPriceRepository;
import com.poultryledger.repository.HenTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class PoultryLedgerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PoultryLedgerApplication.class, args);
    }

    @Bean
    CommandLineRunner seedDefaults(HenTypeRepository henTypeRepository, ChannelPriceRepository channelPriceRepository) {
        return args -> {
            if (henTypeRepository.count() == 0) {
                henTypeRepository.save(new HenType("Kienyeji", 0));
                henTypeRepository.save(new HenType("Improved Kienyeji", 0));
            }
            seedPrice(channelPriceRepository, "Shop", new BigDecimal("20.00"));
            seedPrice(channelPriceRepository, "Buyer", new BigDecimal("16.00"));
            seedPrice(channelPriceRepository, "Home", new BigDecimal("16.00"));
        };
    }

    private void seedPrice(ChannelPriceRepository repository, String channel, BigDecimal price) {
        if (repository.findByChannelIgnoreCase(channel).isEmpty()) {
            repository.save(new ChannelPrice(channel, price));
        }
    }
}
