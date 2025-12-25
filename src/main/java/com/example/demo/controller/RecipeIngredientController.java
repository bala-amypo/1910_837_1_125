package com.example.demo.controller;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService service;

    public RecipeIngredientController(RecipeIngredientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RecipeIngredient> addIngredientToMenuItem(
            @RequestBody RecipeIngredient recipeIngredient) {
        return new ResponseEntity<>(
                service.addIngredientToMenuItem(recipeIngredient),
                HttpStatus.CREATED);
    }

    @GetMapping("/ingredient/{ingredientId}/quantity")
    public ResponseEntity<Double> getTotalQuantity(@PathVariable Long ingredientId) {
        return ResponseEntity.ok(service.getTotalQuantityOfIngredient(ingredientId));
    }
}
