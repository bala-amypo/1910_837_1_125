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

        if (menuItem.getSellingPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Invalid price");
        }

        if (menuItemRepository.findByNameIgnoreCase(menuItem.getName()).isPresent()) {
            throw new BadRequestException("MenuItem already exists");
        }

        validateCategories(menuItem.getCategories());

        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem updated) {

        MenuItem existing = getMenuItemById(id);

        if (updated.getActive() != null && updated.getActive()) {
            if (!recipeIngredientRepository.existsByMenuItemId(id)) {
                throw new BadRequestException("Cannot activate without ingredients");
            }
        }

        validateCategories(updated.getCategories());

        existing.setCategories(updated.getCategories());
        existing.setActive(updated.getActive());

        return menuItemRepository.save(existing);
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));
    }

    @Override
    public void deactivateMenuItem(Long id) {
        MenuItem item = getMenuItemById(id);
        item.setActive(false);
        menuItemRepository.save(item);
    }

    @Override
    public java.util.List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    private void validateCategories(Set<Category> categories) {
        if (categories == null) return;

        for (Category category : categories) {
            Category dbCategory = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            if (!dbCategory.getActive()) {
                throw new BadRequestException("Inactive category");
            }
        }
    }
}
