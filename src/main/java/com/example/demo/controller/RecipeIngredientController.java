package com.example.demo.controller;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService service;

    public RecipeIngredientController(RecipeIngredientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RecipeIngredient> addIngredientToMenuItem(
            @RequestBody RecipeIngredient recipeIngredient) {
        return ResponseEntity.ok(service.addIngredientToMenuItem(recipeIngredient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredient> updateQuantity(
            @PathVariable Long id,
            @RequestParam Double quantity) {
        return ResponseEntity.ok(service.updateRecipeIngredient(id, quantity));
    }

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<RecipeIngredient>> getIngredientsByMenuItem(
            @PathVariable Long menuItemId) {
        return ResponseEntity.ok(service.getIngredientsByMenuItem(menuItemId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeIngredientFromRecipe(@PathVariable Long id) {
        service.removeIngredientFromRecipe(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ingredient/{ingredientId}/total-quantity")
    public ResponseEntity<Double> getTotalQuantityOfIngredient(
            @PathVariable Long ingredientId) {
        return ResponseEntity.ok(service.getTotalQuantityOfIngredient(ingredientId));
    }
}
