package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProfitCalculationServiceImpl implements ProfitCalculationService {
    private final MenuItemRepository mRepo;
    private final RecipeIngredientRepository rRepo;
    private final ProfitCalculationRecordRepository prRepo;

    public ProfitCalculationServiceImpl(MenuItemRepository m, RecipeIngredientRepository r, 
                                      IngredientRepository i, ProfitCalculationRecordRepository pr) {
        this.mRepo = m; this.rRepo = r; this.prRepo = pr;
    }

    @Override
    public ProfitCalculationRecord calculateProfit(Long id) {
        MenuItem item = mRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        List<RecipeIngredient> ingredients = rRepo.findByMenuItemId(id);
        if (ingredients.isEmpty()) throw new BadRequestException("Cost cannot be computed: No ingredients");

        BigDecimal totalCost = BigDecimal.ZERO;
        for (RecipeIngredient ri : ingredients) {
            BigDecimal cost = ri.getIngredient().getCostPerUnit().multiply(BigDecimal.valueOf(ri.getQuantityRequired()));
            totalCost = totalCost.add(cost);
        }

        ProfitCalculationRecord record = new ProfitCalculationRecord();
        record.setMenuItem(item);
        record.setTotalCost(totalCost);
        record.setProfitMargin(item.getSellingPrice().subtract(totalCost).doubleValue());
        return prRepo.save(record);
    }

    public List<ProfitCalculationRecord> findRecordsWithMarginBetween(Double min, Double max) { return List.of(); }
    public ProfitCalculationRecord getCalculationById(Long id) { return prRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found")); }
    public List<ProfitCalculationRecord> getCalculationsForMenuItem(Long id) { return prRepo.findByMenuItemId(id); }
    public List<ProfitCalculationRecord> getAllCalculations() { return prRepo.findAll(); }
}