package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.MenuItemService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository mRepo;
    private final RecipeIngredientRepository rRepo;
    private final CategoryRepository cRepo;

    public MenuItemServiceImpl(MenuItemRepository m, RecipeIngredientRepository r, CategoryRepository c) {
        this.mRepo = m;
        this.rRepo = r;
        this.cRepo = c;
    }

    @Override
    public MenuItem createMenuItem(MenuItem item) {
        if (item.getSellingPrice() != null && item.getSellingPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Invalid price");
        }
        if (mRepo.findByNameIgnoreCase(item.getName()).isPresent()) {
            throw new BadRequestException("Name exists");
        }
        
        // Validate categories are active before creating
        if (item.getCategories() != null) {
            for (Category cat : item.getCategories()) {
                Category found = cRepo.findById(cat.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                if (!found.getActive()) throw new BadRequestException("Category inactive");
            }
        }
        return mRepo.save(item);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem updated) {
        MenuItem ex = mRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        
        // Rule: Cannot be activated unless it has recipe ingredients
        if (updated.getActive() != null && updated.getActive() && !rRepo.existsByMenuItemId(id)) {
            throw new BadRequestException("Cannot activate without ingredients");
        }

        // Copy basic fields
        ex.setName(updated.getName());
        ex.setSellingPrice(updated.getSellingPrice());
        ex.setActive(updated.getActive());
        
        // FIX: Copy description (required by tests)
        ex.setDescription(updated.getDescription());

        // FIX FOR testAssignCategoryDuringUpdateMenuItem:
        // Update the categories collection
        if (updated.getCategories() != null) {
            // Validate that the categories being assigned are active
            for (Category cat : updated.getCategories()) {
                Category found = cRepo.findById(cat.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                if (!found.getActive()) throw new BadRequestException("Category inactive");
            }
            ex.setCategories(updated.getCategories());
        }

        return mRepo.save(ex);
    }

    @Override
    public MenuItem getMenuItemById(Long id) { 
        return mRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found")); 
    }

    @Override
    public List<MenuItem> getAllMenuItems() { 
        return mRepo.findAll(); 
    }

    @Override
    public void deactivateMenuItem(Long id) { 
        MenuItem m = getMenuItemById(id); 
        m.setActive(false); 
        mRepo.save(m); 
    }
}