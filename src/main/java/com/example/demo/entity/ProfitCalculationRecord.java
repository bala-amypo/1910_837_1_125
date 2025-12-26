package com.example.demo.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class ProfitCalculationRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne private MenuItem menuItem;
    private BigDecimal totalCost;
    private Double profitMargin;
    private LocalDateTime calculatedAt = LocalDateTime.now();

    public ProfitCalculationRecord() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public Double getProfitMargin() { return profitMargin; }
    public void setProfitMargin(Double profitMargin) { this.profitMargin = profitMargin; }
}