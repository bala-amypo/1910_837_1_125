package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.MenuItemService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final CategoryRepository categoryRepository;

    public MenuItemServiceImpl(MenuItemRepository m, RecipeIngredientRepository r, CategoryRepository c) {
        this.menuItemRepository = m;
        this.recipeIngredientRepository = r;
        this.categoryRepository = c;
    }

    @Override
    public MenuItem createMenuItem(MenuItem item) {
        if (item.getSellingPrice().compareTo(BigDecimal.ZERO) < 0) throw new BadRequestException("Price must be positive");
        if (menuItemRepository.findByNameIgnoreCase(item.getName()).isPresent()) throw new BadRequestException("Duplicate name");
        
        for (Category cat : item.getCategories()) {
            Category found = categoryRepository.findById(cat.getId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            if (!found.getActive()) throw new BadRequestException("Category is inactive");
        }
        return menuItemRepository.save(item);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem updated) {
        MenuItem existing = menuItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        if (updated.getActive() != null && updated.getActive() && !recipeIngredientRepository.existsByMenuItemId(id)) {
            throw new BadRequestException("Cannot activate without ingredients");
        }
        existing.setName(updated.getName());
        existing.setSellingPrice(updated.getSellingPrice());
        existing.setActive(updated.getActive());
        return menuItemRepository.save(existing);
    }

    public MenuItem getMenuItemById(Long id) { return menuItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found")); }
    public List<MenuItem> getAllMenuItems() { return menuItemRepository.findAll(); }
    public void deactivateMenuItem(Long id) { MenuItem m = getMenuItemById(id); m.setActive(false); menuItemRepository.save(m); }
}