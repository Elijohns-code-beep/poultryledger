package com.poultryledger.repository;
import com.poultryledger.entity.ChannelPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ChannelPriceRepository extends JpaRepository<ChannelPrice, Long> { Optional<ChannelPrice> findByChannelIgnoreCase(String channel); }
