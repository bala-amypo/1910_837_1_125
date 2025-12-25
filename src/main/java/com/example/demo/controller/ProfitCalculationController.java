package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profits")
public class ProfitCalculationController {

    private final ProfitCalculationService service;

    public ProfitCalculationController(ProfitCalculationService service) {
        this.service = service;
    }

    @PostMapping("/menu-item/{menuItemId}")
    public ResponseEntity<ProfitCalculationRecord> calculateProfit(
            @PathVariable Long menuItemId) {
        return ResponseEntity.ok(service.calculateProfit(menuItemId));
    }

    @GetMapping
    public ResponseEntity<List<ProfitCalculationRecord>> getAllCalculations() {
        return ResponseEntity.ok(service.getAllCalculations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfitCalculationRecord> getCalculationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCalculationById(id));
    }

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<ProfitCalculationRecord>> getByMenuItem(
            @PathVariable Long menuItemId) {
        return ResponseEntity.ok(service.getCalculationsForMenuItem(menuItemId));
    }
}
