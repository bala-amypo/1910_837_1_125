package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class RecipeIngredient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne 
    private MenuItem menuItem;
    
    @ManyToOne 
    private Ingredient ingredient;
    
    private Double quantityRequired;

    public RecipeIngredient() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    // The test case calls setQuantity (line 389), but logic uses quantityRequired
    public Double getQuantityRequired() { return quantityRequired; }
    public void setQuantityRequired(Double quantityRequired) { this.quantityRequired = quantityRequired; }
    
    // Alias method to satisfy the test case compilation
    public void setQuantity(double quantity) { this.quantityRequired = quantity; }
}