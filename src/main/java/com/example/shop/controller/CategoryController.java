package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;
import com.example.shop.model.Category;
import com.example.shop.model.Horaire;
import com.example.shop.repository.ArticleRepos;
import com.example.shop.repository.CategoryRepos;
import com.example.shop.service.CategoryService;
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
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(@RequestHeader HttpHeaders header, Pageable pageable) {
        return new ResponseEntity<List<Category>>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<Category> getCategoriesId(@RequestHeader HttpHeaders header, @PathVariable (value = "catId") Long catId, Pageable pageable) {

        return new ResponseEntity<>(categoryService.getCategoriesId(catId), HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity createCategory(@RequestHeader HttpHeaders header, @Valid @RequestBody Category category) {
        categoryService.saveOrUpdate(category);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/categories/{catId}")
    public ResponseEntity updateCategory(@RequestHeader HttpHeaders header, @PathVariable Long catId, @Valid @RequestBody Category categoryRequest) {
        categoryService.update(categoryRequest,catId );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/articles/{artId}/categories")
    public ResponseEntity<Category> addCategory(@PathVariable(value = "artId") Long artId, @RequestBody Category categoryRequest) {

        return new ResponseEntity<>(categoryService.addCategory(categoryRequest, artId), HttpStatus.CREATED);
    }


    @DeleteMapping("/categories/{catId}")
    public ResponseEntity deleteCategory(@RequestHeader HttpHeaders header, @PathVariable Long catId) {

        categoryService.delete(catId);
        return ResponseEntity.ok().build();
    }
}
