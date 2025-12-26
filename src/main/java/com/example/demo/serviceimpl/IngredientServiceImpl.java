package com.example.demo.service.impl;
import com.example.demo.entity.Ingredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.IngredientService;
import java.math.BigDecimal;
import java.util.List;

public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository repository;
    public IngredientServiceImpl(IngredientRepository repository) { this.repository = repository; }

    public Ingredient createIngredient(Ingredient ing) {
        if (repository.findByNameIgnoreCase(ing.getName()).isPresent()) throw new BadRequestException("Duplicate");
        if (ing.getCostPerUnit().compareTo(BigDecimal.ZERO) <= 0) throw new BadRequestException("Cost per unit invalid");
        ing.setActive(true);
        return repository.save(ing);
    }
    public Ingredient updateIngredient(Long id, Ingredient updated) {
        Ingredient ex = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        ex.setName(updated.getName()); ex.setUnit(updated.getUnit()); ex.setCostPerUnit(updated.getCostPerUnit());
        return repository.save(ex);
    }
    public Ingredient getIngredientById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found")); }
    public List<Ingredient> getAllIngredients() { return repository.findAll(); }
    public void deactivateIngredient(Long id) { Ingredient i = getIngredientById(id); i.setActive(false); repository.save(i); }
}