package com.example.demo.sevices;

import com.example.demo.entity.Category;

import java.util.List;

public interface CategoryService  {
    String createCategory(String name);

    List<Category> getAllCategories();
}
