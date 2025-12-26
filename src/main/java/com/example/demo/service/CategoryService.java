package com.example.demo.service;
import com.example.demo.entity.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory(Category c);
    Category updateCategory(Long id, Category c);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void deactivateCategory(Long id);
}