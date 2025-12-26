package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    public CategoryServiceImpl(CategoryRepository repository) { this.repository = repository; }

    public Category createCategory(Category c) {
        if(repository.findByNameIgnoreCase(c.getName()).isPresent()) throw new BadRequestException("Duplicate name");
        return repository.save(c);
    }
    public Category updateCategory(Long id, Category c) {
        Category existing = getCategoryById(id);
        existing.setName(c.getName());
        existing.setDescription(c.getDescription());
        return repository.save(existing);
    }
    public Category getCategoryById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found")); }
    public List<Category> getAllCategories() { return repository.findAll(); }
    public void deactivateCategory(Long id) {
        Category c = getCategoryById(id);
        c.setActive(false);
        repository.save(c);
    }
}