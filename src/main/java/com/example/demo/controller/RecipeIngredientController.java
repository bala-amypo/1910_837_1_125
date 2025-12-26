package com.example.demo.controller;
import com.example.demo.entity.RecipeIngredient;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {
    private final RecipeIngredientService service;
    public RecipeIngredientController(RecipeIngredientService service) { this.service = service; }
    @PostMapping public ResponseEntity<RecipeIngredient> addIngredient(@RequestBody RecipeIngredient ri) { return ResponseEntity.ok(service.addIngredientToMenuItem(ri)); }
    @GetMapping("/menu-item/{menuItemId}") public ResponseEntity<List<RecipeIngredient>> getByMenu(@PathVariable Long menuItemId) { return ResponseEntity.ok(service.getIngredientsByMenuItem(menuItemId)); }
    @GetMapping("/ingredient/{id}/total-quantity") public ResponseEntity<Double> getTotal(@PathVariable Long id) { return ResponseEntity.ok(service.getTotalQuantityOfIngredient(id)); }
}