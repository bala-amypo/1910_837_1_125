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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final CategoryRepository categoryRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               RecipeIngredientRepository recipeIngredientRepository,
                               CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {

        if (menuItem.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Invalid price");
        }

        if (menuItemRepository.findByNameIgnoreCase(menuItem.getName()).isPresent()) {
            throw new BadRequestException("Duplicate menu item");
        }

        Set<Category> categories = new HashSet<>();
        if (menuItem.getCategories() != null) {
            for (Category c : menuItem.getCategories()) {
                Category cat = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                if (!cat.isActive()) {
                    throw new BadRequestException("Inactive category");
                }
                categories.add(cat);
            }
        }
        menuItem.setCategories(categories);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        if (menuItem.isActive() &&
                !recipeIngredientRepository.existsByMenuItemId(id)) {
            throw new BadRequestException("Menu item cannot be active without recipe ingredients");
        }

        existing.setName(menuItem.getName());
        existing.setDescription(menuItem.getDescription());
        existing.setSellingPrice(menuItem.getSellingPrice());
        existing.setActive(menuItem.isActive());

        if (menuItem.getCategories() != null) {
            Set<Category> categories = new HashSet<>();
            for (Category c : menuItem.getCategories()) {
                Category cat = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                if (!cat.isActive()) {
                    throw new BadRequestException("Inactive category");
                }
                categories.add(cat);
            }
            existing.setCategories(categories);
        }

        return menuItemRepository.save(existing);
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public void deactivateMenuItem(Long id) {
        MenuItem menuItem = getMenuItemById(id);
        menuItem.setActive(false);
        menuItemRepository.save(menuItem);
    }
}
