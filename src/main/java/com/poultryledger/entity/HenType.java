package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "hen_types", uniqueConstraints = @UniqueConstraint(name = "uk_hen_type_name", columnNames = "name"))
public class HenType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;
    @Min(0)
    @Column(nullable = false)
    private Integer startingPopulation = 0;

    public HenType() {}
    public HenType(String name, Integer startingPopulation) { this.name = name; this.startingPopulation = startingPopulation; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getStartingPopulation() { return startingPopulation; }
    public void setStartingPopulation(Integer startingPopulation) { this.startingPopulation = startingPopulation; }
}
