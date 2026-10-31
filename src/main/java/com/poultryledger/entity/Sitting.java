package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "sittings")
public class Sitting {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Column(nullable = false)
    private LocalDate dateGiven = LocalDate.now();
    @NotNull @Min(0) @Column(nullable = false)
    private Integer eggsGiven;
    private Integer chicksHatched;
    @Column(nullable = false)
    private LocalDate expectedHatchDate;
    @Column(nullable = false, unique = true)
    private String chickGroupCode;
    @OneToOne(mappedBy = "sitting")
    private ChickGroup chickGroup;

    public Sitting() {}
    public Long getId() { return id; }
    public LocalDate getDateGiven() { return dateGiven; }
    public void setDateGiven(LocalDate dateGiven) { this.dateGiven = dateGiven; }
    public Integer getEggsGiven() { return eggsGiven; }
    public void setEggsGiven(Integer eggsGiven) { this.eggsGiven = eggsGiven; }
    public Integer getChicksHatched() { return chicksHatched; }
    public void setChicksHatched(Integer chicksHatched) { this.chicksHatched = chicksHatched; }
    public LocalDate getExpectedHatchDate() { return expectedHatchDate; }
    public void setExpectedHatchDate(LocalDate expectedHatchDate) { this.expectedHatchDate = expectedHatchDate; }
    public String getChickGroupCode() { return chickGroupCode; }
    public void setChickGroupCode(String chickGroupCode) { this.chickGroupCode = chickGroupCode; }
    public ChickGroup getChickGroup() { return chickGroup; }
    public void setChickGroup(ChickGroup chickGroup) { this.chickGroup = chickGroup; }
}
