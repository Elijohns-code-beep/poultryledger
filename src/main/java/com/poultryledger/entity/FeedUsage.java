package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "feed_usages")
public class FeedUsage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String feedCode;
    private String otherFeedName;
    @NotNull @DecimalMin(value = "0.00") @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal quantityKg;
    @DecimalMin(value = "0.00") @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitCostAtTime;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "daily_log_id", nullable = false)
    private DailyLog dailyLog;

    public FeedUsage() {}
    public Long getId() { return id; }
    public String getFeedCode() { return feedCode; }
    public void setFeedCode(String feedCode) { this.feedCode = feedCode; }
    public String getOtherFeedName() { return otherFeedName; }
    public void setOtherFeedName(String otherFeedName) { this.otherFeedName = otherFeedName; }
    public BigDecimal getQuantityKg() { return quantityKg; }
    public void setQuantityKg(BigDecimal quantityKg) { this.quantityKg = quantityKg; }
    public BigDecimal getUnitCostAtTime() { return unitCostAtTime; }
    public void setUnitCostAtTime(BigDecimal unitCostAtTime) { this.unitCostAtTime = unitCostAtTime; }
    public DailyLog getDailyLog() { return dailyLog; }
    public void setDailyLog(DailyLog dailyLog) { this.dailyLog = dailyLog; }
}
