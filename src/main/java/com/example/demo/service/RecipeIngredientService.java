package com.example.demo.service;
import com.example.demo.entity.RecipeIngredient;
import java.util.List;

public interface RecipeIngredientService {
    RecipeIngredient addIngredientToMenuItem(RecipeIngredient ri);
    RecipeIngredient updateRecipeIngredient(Long id, Double quantity);
    List<RecipeIngredient> getIngredientsByMenuItem(Long menuItemId);
    void removeIngredientFromRecipe(Long id);
    Double getTotalQuantityOfIngredient(Long ingredientId);
}