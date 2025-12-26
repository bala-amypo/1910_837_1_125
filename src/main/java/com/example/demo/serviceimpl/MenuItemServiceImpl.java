package com.example.demo.service.impl;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.MenuItemService;
import java.math.BigDecimal;
import java.util.List;

public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository mRepo;
    private final RecipeIngredientRepository rRepo;
    private final CategoryRepository cRepo;

    public MenuItemServiceImpl(MenuItemRepository m, RecipeIngredientRepository r, CategoryRepository c) {
        this.mRepo = m; this.rRepo = r; this.cRepo = c;
    }

    public MenuItem createMenuItem(MenuItem item) {
        if (item.getSellingPrice().compareTo(BigDecimal.ZERO) < 0) throw new BadRequestException("Invalid price");
        if (mRepo.findByNameIgnoreCase(item.getName()).isPresent()) throw new BadRequestException("Duplicate");
        for(Category c : item.getCategories()) {
            Category found = cRepo.findById(c.getId()).orElseThrow(() -> new ResourceNotFoundException("not found"));
            if(!found.getActive()) throw new BadRequestException("Inactive category");
        }
        return mRepo.save(item);
    }

    public MenuItem updateMenuItem(Long id, MenuItem updated) {
        MenuItem ex = mRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        if(updated.getActive() && !rRepo.existsByMenuItemId(id)) throw new BadRequestException("No ingredients");
        ex.setName(updated.getName()); ex.setSellingPrice(updated.getSellingPrice()); ex.setActive(updated.getActive());
        return mRepo.save(ex);
    }
    public MenuItem getMenuItemById(Long id) { return mRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found")); }
    public List<MenuItem> getAllMenuItems() { return mRepo.findAll(); }
    public void deactivateMenuItem(Long id) { MenuItem m = getMenuItemById(id); m.setActive(false); mRepo.save(m); }
}