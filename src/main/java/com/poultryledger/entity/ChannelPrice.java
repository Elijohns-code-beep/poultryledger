package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "channel_prices", uniqueConstraints = @UniqueConstraint(name = "uk_channel_price_channel", columnNames = "channel"))
public class ChannelPrice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String channel;
    @NotNull @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    public ChannelPrice() {}
    public ChannelPrice(String channel, BigDecimal price) { this.channel = channel; this.price = price; }
    public Long getId() { return id; }
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
