package com.example.demo.repository;

import com.example.demo.entity.ProfitCalculationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfitCalculationRecordRepository extends JpaRepository<ProfitCalculationRecord, Long> {

    Optional<ProfitCalculationRecord> findById(Long id);

    List<ProfitCalculationRecord> findByMenuItemId(Long menuItemId);

    List<ProfitCalculationRecord> findByProfitMarginGreaterThanEqual(Double profitMargin);
}
