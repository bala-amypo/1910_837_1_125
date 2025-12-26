package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class RecipeIngredient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne private MenuItem menuItem;
    @ManyToOne private Ingredient ingredient;
    private Double quantityRequired;
}