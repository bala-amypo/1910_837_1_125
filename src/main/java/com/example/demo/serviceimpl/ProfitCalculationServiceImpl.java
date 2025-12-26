package com.example.demo.service.impl;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.ProfitCalculationService;
import java.math.BigDecimal;
import java.util.List;

public class ProfitCalculationServiceImpl implements ProfitCalculationService {
    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final ProfitCalculationRecordRepository profitCalculationRecordRepository;

    public ProfitCalculationServiceImpl(MenuItemRepository m, RecipeIngredientRepository r, 
                                      IngredientRepository i, ProfitCalculationRecordRepository pr) {
        this.menuItemRepository = m;
        this.recipeIngredientRepository = r;
        this.profitCalculationRecordRepository = pr;
    }

    public ProfitCalculationRecord calculateProfit(Long id) {
        MenuItem item = menuItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        List<RecipeIngredient> ris = recipeIngredientRepository.findByMenuItemId(id);
        if (ris.isEmpty()) throw new BadRequestException("No ingredients");

        BigDecimal cost = ris.stream()
            .map(ri -> ri.getIngredient().getCostPerUnit().multiply(BigDecimal.valueOf(ri.getQuantityRequired())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        ProfitCalculationRecord record = new ProfitCalculationRecord();
        record.setMenuItem(item);
        record.setTotalCost(cost);
        record.setProfitMargin(item.getSellingPrice().subtract(cost).doubleValue());
        return profitCalculationRecordRepository.save(record);
    }

    public List<ProfitCalculationRecord> findRecordsWithMarginBetween(Double min, Double max) { return List.of(); }
    public ProfitCalculationRecord getCalculationById(Long id) { return profitCalculationRecordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found")); }
    public List<ProfitCalculationRecord> getCalculationsForMenuItem(Long id) { return profitCalculationRecordRepository.findByMenuItemId(id); }
    public List<ProfitCalculationRecord> getAllCalculations() { return profitCalculationRecordRepository.findAll(); }
}