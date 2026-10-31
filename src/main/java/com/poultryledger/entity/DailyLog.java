package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily_logs", uniqueConstraints = @UniqueConstraint(name = "uk_daily_log_date", columnNames = "date"))
public class DailyLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Column(name = "date", nullable = false, unique = true)
    private LocalDate date = LocalDate.now();
    @NotNull @Min(0) @Column(nullable = false)
    private Integer kienyejiEggs = 0;
    @NotNull @Min(0) @Column(nullable = false)
    private Integer improvedKienyejiEggs = 0;
    @Valid
    @OneToMany(mappedBy = "dailyLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedUsage> feedUsages = new ArrayList<>();

    public DailyLog() {}
    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Integer getKienyejiEggs() { return kienyejiEggs; }
    public void setKienyejiEggs(Integer kienyejiEggs) { this.kienyejiEggs = kienyejiEggs; }
    public Integer getImprovedKienyejiEggs() { return improvedKienyejiEggs; }
    public void setImprovedKienyejiEggs(Integer improvedKienyejiEggs) { this.improvedKienyejiEggs = improvedKienyejiEggs; }
    public List<FeedUsage> getFeedUsages() { return feedUsages; }
    public void setFeedUsages(List<FeedUsage> feedUsages) { this.feedUsages = feedUsages; }
}
