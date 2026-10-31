package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sales")
public class Sale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Column(nullable = false)
    private LocalDate date = LocalDate.now();
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "hen_type_id", nullable = false)
    @NotNull
    private HenType henType;
    @NotNull @Min(0) @Column(nullable = false)
    private Integer quantityEggs;
    @NotBlank @Column(nullable = false)
    private String soldTo;
    private String buyerName;
    @NotNull @DecimalMin(value = "0.00") @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal pricePerEgg;

    public Sale() {}
    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public HenType getHenType() { return henType; }
    public void setHenType(HenType henType) { this.henType = henType; }
    public Integer getQuantityEggs() { return quantityEggs; }
    public void setQuantityEggs(Integer quantityEggs) { this.quantityEggs = quantityEggs; }
    public String getSoldTo() { return soldTo; }
    public void setSoldTo(String soldTo) { this.soldTo = soldTo; }
    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }
    public BigDecimal getPricePerEgg() { return pricePerEgg; }
    public void setPricePerEgg(BigDecimal pricePerEgg) { this.pricePerEgg = pricePerEgg; }
}
