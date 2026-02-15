package com.example.demo.sevices.impl;

import com.example.demo.entity.Category;
import com.example.demo.entity.PdfFile;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.sevices.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public String createCategory(String name) {
        if(categoryRepository.findByName(name).isPresent()){
            throw new RuntimeException("Category already exists");
        }
      Category category=new Category();
        category.setName(name);
        category.setCreatedAt(LocalDateTime.now());
        categoryRepository.save(category);
        return "Category created successfully";
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
