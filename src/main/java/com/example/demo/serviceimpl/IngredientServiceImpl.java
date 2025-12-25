package com.example.demo.service.impl;

import com.example.demo.entity.Ingredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repository;

    public IngredientServiceImpl(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        if (repository.findByNameIgnoreCase(ingredient.getName()).isPresent()) {
            throw new BadRequestException("Ingredient already exists");
        }
        return repository.save(ingredient);
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        Ingredient existing = getIngredientById(id);
        existing.setCostPerUnit(ingredient.getCostPerUnit());
        return repository.save(existing);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return repository.findAll();
    }
}
