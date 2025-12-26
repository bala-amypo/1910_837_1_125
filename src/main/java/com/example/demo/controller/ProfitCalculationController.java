package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/profit")
public class ProfitCalculationController {
    private final ProfitCalculationService service;

    public ProfitCalculationController(ProfitCalculationService service) {
        this.service = service;
    }

    @PostMapping("/calculate/{menuItemId}")
    public ResponseEntity<ProfitCalculationRecord> calculateProfit(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(service.calculateProfit(menuItemId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfitCalculationRecord> getCalculationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCalculationById(id));
    }

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<ProfitCalculationRecord>> getHistoryByMenuItem(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(service.getCalculationsForMenuItem(menuItemId));
    }

    @GetMapping
    public ResponseEntity<List<ProfitCalculationRecord>> getAllCalculations() {
        return ResponseEntity.ok(service.getAllCalculations());
    }
}