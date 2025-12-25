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

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category createCategory(Category category) {
        if (repository.findByNameIgnoreCase(category.getName()).isPresent()) {
            throw new BadRequestException("Category already exists");
        }
        return repository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deactivateCategory(Long id) {
        Category category = getCategoryById(id);
        category.setActive(false);
        repository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }
}
