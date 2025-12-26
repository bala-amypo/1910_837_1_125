package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProfitCalculationServiceImpl implements ProfitCalculationService {
    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final ProfitCalculationRecordRepository profitCalculationRecordRepository;

    public ProfitCalculationServiceImpl(MenuItemRepository m, RecipeIngredientRepository r, 
                                      IngredientRepository i, ProfitCalculationRecordRepository pr) {
        this.menuItemRepository = m;
        this.recipeIngredientRepository = r;
        this.ingredientRepository = i;
        this.profitCalculationRecordRepository = pr;
    }

    @Override
    public ProfitCalculationRecord calculateProfit(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found"));
        
        List<RecipeIngredient> ingredients = recipeIngredientRepository.findByMenuItemId(id);
        if (ingredients.isEmpty()) {
            throw new BadRequestException("Cost cannot be computed: No ingredients");
        }

        BigDecimal totalCost = BigDecimal.ZERO;
        for (RecipeIngredient ri : ingredients) {
            BigDecimal cost = ri.getIngredient().getCostPerUnit()
                    .multiply(BigDecimal.valueOf(ri.getQuantityRequired()));
            totalCost = totalCost.add(cost);
        }

        ProfitCalculationRecord record = new ProfitCalculationRecord();
        record.setMenuItem(item);
        record.setTotalCost(totalCost);
        record.setProfitMargin(item.getSellingPrice().subtract(totalCost).doubleValue());
        
        return profitCalculationRecordRepository.save(record);
    }

    public List<ProfitCalculationRecord> findRecordsWithMarginBetween(Double min, Double max) { return List.of(); }
    public ProfitCalculationRecord getCalculationById(Long id) { return profitCalculationRecordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found")); }
    public List<ProfitCalculationRecord> getCalculationsForMenuItem(Long id) { return profitCalculationRecordRepository.findByMenuItemId(id); }
    public List<ProfitCalculationRecord> getAllCalculations() { return profitCalculationRecordRepository.findAll(); }
}