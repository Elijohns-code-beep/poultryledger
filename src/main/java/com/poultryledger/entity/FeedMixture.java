package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feed_mixtures", uniqueConstraints = @UniqueConstraint(name = "uk_feed_mixture_code", columnNames = "code_name"))
public class FeedMixture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Column(nullable = false)
    private LocalDate date = LocalDate.now();
    @NotBlank @Column(name = "code_name", nullable = false, unique = true)
    private String codeName;
    @Valid
    @OneToMany(mappedBy = "mixture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedIngredient> ingredients = new ArrayList<>();

    public FeedMixture() {}
    public FeedMixture(LocalDate date, String codeName) { this.date = date; this.codeName = codeName; }
    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getCodeName() { return codeName; }
    public void setCodeName(String codeName) { this.codeName = codeName; }
    public List<FeedIngredient> getIngredients() { return ingredients; }
    public void setIngredients(List<FeedIngredient> ingredients) { this.ingredients = ingredients; }
    public void addIngredient(FeedIngredient ingredient) { ingredients.add(ingredient); ingredient.setMixture(this); }
}
