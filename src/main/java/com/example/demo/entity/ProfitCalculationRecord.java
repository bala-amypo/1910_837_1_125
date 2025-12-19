package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "profit_calculation_records")
public class ProfitCalculationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private MenuItem menuItem;

    private BigDecimal totalCost;

    private Double profitMargin;

    private LocalDateTime calculatedAt;

    @PrePersist
    void onCreate() {
        this.calculatedAt = LocalDateTime.now();
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }

    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }

    public Double getProfitMargin() { return profitMargin; }
    public void setProfitMargin(Double profitMargin) { this.profitMargin = profitMargin; }

    public LocalDateTime getCalculatedAt() { return calculatedAt; }
}
