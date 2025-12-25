package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class ProfitCalculationRecord {

    @Id
    @GeneratedValue
    private Long id;

    private Double profitMargin;

    @ManyToOne
    private MenuItem menuItem;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getProfitMargin() { return profitMargin; }
    public void setProfitMargin(Double profitMargin) { this.profitMargin = profitMargin; }

    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
}
