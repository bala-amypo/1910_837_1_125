package com.example.demo.service.impl;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.MenuItem;
import com.example.demo.entity.RecipeIngredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository repository;
    private final IngredientRepository ingredientRepository;
    private final MenuItemRepository menuItemRepository;

    public RecipeIngredientServiceImpl(RecipeIngredientRepository repository,
                                       IngredientRepository ingredientRepository,
                                       MenuItemRepository menuItemRepository) {
        this.repository = repository;
        this.ingredientRepository = ingredientRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public RecipeIngredient addIngredientToMenuItem(RecipeIngredient ri) {

        if (ri.getQuantity() <= 0) {
            throw new BadRequestException("Invalid quantity");
        }

        Ingredient ingredient = ingredientRepository.findById(ri.getIngredient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

        MenuItem menuItem = menuItemRepository.findById(ri.getMenuItem().getId())
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));

        ri.setIngredient(ingredient);
        ri.setMenuItem(menuItem);

        return repository.save(ri);
    }

    @Override
    public Double getTotalQuantityOfIngredient(Long ingredientId) {
        return repository.getTotalQuantityByIngredientId(ingredientId);
    }
}
