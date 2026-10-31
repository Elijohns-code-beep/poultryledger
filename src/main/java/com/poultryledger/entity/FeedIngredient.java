package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "feed_ingredients")
public class FeedIngredient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false)
    private String name;
    private String sourcedFrom;
    @NotNull @DecimalMin(value = "0.00") @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal quantityKg;
    @NotNull @DecimalMin(value = "0.00") @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "mixture_id", nullable = false)
    private FeedMixture mixture;

    public FeedIngredient() {}
    public FeedIngredient(String name, String sourcedFrom, BigDecimal quantityKg, BigDecimal price) { this.name=name; this.sourcedFrom=sourcedFrom; this.quantityKg=quantityKg; this.price=price; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSourcedFrom() { return sourcedFrom; }
    public void setSourcedFrom(String sourcedFrom) { this.sourcedFrom = sourcedFrom; }
    public BigDecimal getQuantityKg() { return quantityKg; }
    public void setQuantityKg(BigDecimal quantityKg) { this.quantityKg = quantityKg; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public FeedMixture getMixture() { return mixture; }
    public void setMixture(FeedMixture mixture) { this.mixture = mixture; }
}
