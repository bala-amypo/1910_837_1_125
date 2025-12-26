package com.example.demo.controller;

import com.example.demo.entity.Ingredient;
import com.example.demo.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService service;
    public IngredientController(IngredientService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ing) {
        return new ResponseEntity<>(service.createIngredient(ing), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(service.getAllIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getIngredientById(id));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateIngredient(@PathVariable Long id) {
        service.deactivateIngredient(id);
        return ResponseEntity.ok().build();
    }
}