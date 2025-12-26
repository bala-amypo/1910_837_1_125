package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class RecipeIngredient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;
    @ManyToOne @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
    private Double quantityRequired;
}