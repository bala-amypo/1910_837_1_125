package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.entity.MenuItem;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.MenuItemService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

    public MenuItem createMenuItem(MenuItem item) {
        if (item.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) 
            throw new BadRequestException("Selling price must be > 0");
        if (menuItemRepository.findByNameIgnoreCase(item.getName()).isPresent())
            throw new BadRequestException("Name exists");
        
        validateCategories(item.getCategories());
        return menuItemRepository.save(item);
    }

    public MenuItem updateMenuItem(Long id, MenuItem updated) {
        MenuItem existing = menuItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        if (updated.getActive() && !recipeIngredientRepository.existsByMenuItemId(id)) {
            throw new BadRequestException("Cannot be active without recipe ingredients");
        }
        validateCategories(updated.getCategories());
        existing.setName(updated.getName());
        existing.setSellingPrice(updated.getSellingPrice());
        existing.setActive(updated.getActive());
        existing.setCategories(updated.getCategories());
        return menuItemRepository.save(existing);
    }

    private void validateCategories(Set<Category> categories) {
        if (categories == null) return;
        for (Category c : categories) {
            Category found = categoryRepository.findById(c.getId()).orElseThrow(() -> new ResourceNotFoundException("Category missing"));
            if (!found.getActive()) throw new BadRequestException("Category inactive");
        }
    }

    public MenuItem getMenuItemById(Long id) { return menuItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found")); }
    public List<MenuItem> getAllMenuItems() { return menuItemRepository.findAll(); }
    public void deactivateMenuItem(Long id) {
        MenuItem item = getMenuItemById(id);
        item.setActive(false);
        menuItemRepository.save(item);
    }
}