package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "population_events")
public class PopulationEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Column(nullable = false)
    private LocalDate date = LocalDate.now();
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "hen_type_id", nullable = false)
    private HenType henType;
    @NotNull @Column(nullable = false)
    private Integer change;
    private String reason;

    public PopulationEvent() {}
    public PopulationEvent(LocalDate date, HenType henType, Integer change, String reason) { this.date=date; this.henType=henType; this.change=change; this.reason=reason; }
    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public HenType getHenType() { return henType; }
    public void setHenType(HenType henType) { this.henType = henType; }
    public Integer getChange() { return change; }
    public void setChange(Integer change) { this.change = change; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
