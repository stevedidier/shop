package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Category;
import com.example.shop.model.Horaire;
import com.example.shop.repository.CategoryRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryRepos categoryRepos;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(@RequestHeader HttpHeaders header, Pageable pageable) {
        return new ResponseEntity<List<Category>>(categoryRepos.findAll(pageable).stream().toList(), HttpStatus.OK);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<Category> getAllCategoryId(@RequestHeader HttpHeaders header, @PathVariable (value = "catId") Long catId, Pageable pageable) {
        return new ResponseEntity<Category>(categoryRepos.findById(catId, pageable).stream().toList().get(0), HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity createCategory(@RequestHeader HttpHeaders header, @Valid @RequestBody Category category) {
        categoryRepos.save(category);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/categories/{catId}")
    public ResponseEntity updateCategory(@RequestHeader HttpHeaders header, @PathVariable Long catId, @Valid @RequestBody Category categoryRequest) {
        String title = categoryRequest.getTitle();
        double tva = categoryRequest.getTva();

        return categoryRepos.findById(catId).map(category -> {
            category.setTitle(title);
            category.setTva(tva);
            categoryRepos.save(category);
            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new ResourceNotFoundException("CatId " + catId + " not found"));
    }


    @DeleteMapping("/categories/{catId}")
    public ResponseEntity deleteCategory(@RequestHeader HttpHeaders header, @PathVariable Long catId) {
        return categoryRepos.findById(catId).map(category -> {
            categoryRepos.delete(category);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("CatId " + catId + " not found"));
    }
}
