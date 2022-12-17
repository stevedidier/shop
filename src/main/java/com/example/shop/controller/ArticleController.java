package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;
import com.example.shop.model.Category;
import com.example.shop.repository.ArticleRepos;
import com.example.shop.repository.CategoryRepos;
import com.example.shop.repository.ShopRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
@RestController
public class ArticleController {

    @Autowired
    private CategoryRepos categoryRepos;

    @Autowired
    private ShopRepos shopRepos;

    @Autowired
    private ArticleRepos articleRepos;

    @GetMapping("/articles")
    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepos.findAll(pageable);
    }

    @GetMapping("/articles/{artId}")
    public Page<Article> getAllArticlesId(@PathVariable (value = "artId") Long artId, Pageable pageable) {
        return articleRepos.findById(artId, pageable);
    }

    @PostMapping("/articles/categories/{catId}/shops/{shopId}")
    public Article createArticle(@PathVariable Long catId, @PathVariable Long shopId, @Valid @RequestBody Article article) {
        return categoryRepos.findById(catId).map(category -> {
            article.setCategory(category);

            return shopRepos.findById(shopId).map(shop -> {
                article.setShop(shop);
                return articleRepos.save(article);
            }).orElseThrow(() -> new ResourceNotFoundException("CatId " + shopId + " not found"));

        }).orElseThrow(() -> new ResourceNotFoundException("CatId " + catId + " not found"));

    }

    @PutMapping("/articles/{articleId}")
    public Article updateArticle(@PathVariable Long articleId, @Valid @RequestBody Article articleRequest) {
        return articleRepos.findById(articleId).map(article -> {
            article.setDesignation(articleRequest.getDesignation());
            article.setMarque(articleRequest.getMarque());
            article.setCategory(articleRequest.getCategory());
            article.setqStock(articleRequest.getqStock());
            return articleRepos.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException("ArticleId " + articleId + " not found"));
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {
        return articleRepos.findById(articleId).map(article -> {
            articleRepos.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("ArticleId " + articleId + " not found"));
    }

    @GetMapping("/articles/categories/{catId}")
    public Page<Article> getAllArticlesByCatId(@PathVariable (value = "catId") Long catId,
                                                Pageable pageable) {
        return articleRepos.findByCategoryId(catId, pageable);
    }


    @GetMapping("/articles/shops/{shopId}")
    public Page<Article> getAllArticlesByShopId(@PathVariable (value = "shopId") Long shopId,
                                               Pageable pageable) {
        return articleRepos.findByShopId(shopId, pageable);
    }



}
