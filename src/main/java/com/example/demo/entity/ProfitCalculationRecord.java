package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Data
public class ProfitCalculationRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne private MenuItem menuItem;
    private BigDecimal totalCost;
    private Double profitMargin;
    private LocalDateTime calculatedAt = LocalDateTime.now();
}