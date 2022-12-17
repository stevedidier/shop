package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Category;
import com.example.shop.repository.CategoryRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepos categoryRepos;

    @GetMapping("/categories")
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepos.findAll(pageable);
    }

    @GetMapping("/categories/{catId}")
    public Page<Category> getAllCategoryId(@PathVariable (value = "catId") Long catId, Pageable pageable) {
        return categoryRepos.findById(catId, pageable);
    }

    @PostMapping("/categories")
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryRepos.save(category);
    }

    @PutMapping("/categories/{catId}")
    public Category updateCategory(@PathVariable Long catId, @Valid @RequestBody Category categoryRequest) {
        String title = categoryRequest.getTitle();
        double tva = categoryRequest.getTva();

        return categoryRepos.findById(catId).map(category -> {
            category.setTitle(title);
            category.setTva(tva);
            return categoryRepos.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("CatId " + catId + " not found"));
    }


    @DeleteMapping("/categories/{catId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long catId) {
        return categoryRepos.findById(catId).map(category -> {
            categoryRepos.delete(category);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("CatId " + catId + " not found"));
    }
}
