package com.example.demo.controllers;

import com.example.demo.entity.Category;
import com.example.demo.sevices.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestParam String name){
        return ResponseEntity.ok(categoryService.createCategory(name));
    }

    @GetMapping("/all")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();

    }
}
