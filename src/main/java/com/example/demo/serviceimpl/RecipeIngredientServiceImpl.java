package com.example.demo.service.impl;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {
    private final RecipeIngredientRepository riRepo;
    private final IngredientRepository iRepo;
    private final MenuItemRepository mRepo;

    public RecipeIngredientServiceImpl(RecipeIngredientRepository riRepo, IngredientRepository iRepo, MenuItemRepository mRepo) {
        this.riRepo = riRepo;
        this.iRepo = iRepo;
        this.mRepo = mRepo;
    }

    @Override
    public RecipeIngredient addIngredientToMenuItem(RecipeIngredient ri) {
        iRepo.findById(ri.getIngredient().getId()).orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        mRepo.findById(ri.getMenuItem().getId()).orElseThrow(() -> new ResourceNotFoundException("Menu Item not found"));
        
        if (ri.getQuantityRequired() == null || ri.getQuantityRequired() <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }
        return riRepo.save(ri);
    }

    @Override
    public RecipeIngredient updateRecipeIngredient(Long id, Double quantity) {
        RecipeIngredient ri = riRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        ri.setQuantityRequired(quantity);
        return riRepo.save(ri);
    }

    @Override
    public List<RecipeIngredient> getIngredientsByMenuItem(Long menuItemId) {
        return riRepo.findByMenuItemId(menuItemId);
    }

    @Override
    public void removeIngredientFromRecipe(Long id) {
        riRepo.deleteById(id);
    }

    @Override
    public Double getTotalQuantityOfIngredient(Long ingredientId) {
        Double total = riRepo.getTotalQuantityByIngredientId(ingredientId);
        return total != null ? total : 0.0;
    }
}