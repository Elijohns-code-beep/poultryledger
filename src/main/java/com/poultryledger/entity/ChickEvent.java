package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "chick_events")
public class ChickEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Column(nullable = false)
    private LocalDate date = LocalDate.now();
    @NotNull @Column(nullable = false)
    private Integer change;
    private String reason;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "chick_group_id", nullable = false)
    private ChickGroup chickGroup;

    public ChickEvent() {}
    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Integer getChange() { return change; }
    public void setChange(Integer change) { this.change = change; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public ChickGroup getChickGroup() { return chickGroup; }
    public void setChickGroup(ChickGroup chickGroup) { this.chickGroup = chickGroup; }
}
