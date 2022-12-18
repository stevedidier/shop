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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ArticleController {

    @Autowired
    private CategoryRepos categoryRepos;

    @Autowired
    private ShopRepos shopRepos;

    @Autowired
    private ArticleRepos articleRepos;

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles(@RequestHeader HttpHeaders header, Pageable pageable) {
        return new ResponseEntity<List<Article>>(articleRepos.findAll(pageable).stream().toList(), HttpStatus.OK);
    }

    @GetMapping("/articles/{artId}")
    public ResponseEntity<Article> getAllArticlesId(@RequestHeader HttpHeaders header, @PathVariable (value = "artId") Long artId, Pageable pageable) {
        return new ResponseEntity<Article>(articleRepos.findById(artId, pageable).stream().toList().get(0), HttpStatus.OK);
    }

    @PostMapping("/articles/categories/{catId}/shops/{shopId}")
    public ResponseEntity createArticle(@RequestHeader HttpHeaders header, @PathVariable Long catId, @PathVariable Long shopId, @Valid @RequestBody Article article) {
        return categoryRepos.findById(catId).map(category -> {
            article.setCategory(category);

            return shopRepos.findById(shopId).map(shop -> {
                article.setShop(shop);
                articleRepos.save(article);
                return ResponseEntity.ok().build();
            }).orElseThrow(() -> new ResourceNotFoundException("CatId " + shopId + " not found"));

        }).orElseThrow(() -> new ResourceNotFoundException("CatId " + catId + " not found"));

    }

    @PutMapping("/articles/{articleId}")
    public ResponseEntity updateArticle(@RequestHeader HttpHeaders header, @PathVariable Long articleId, @Valid @RequestBody Article articleRequest) {
        return articleRepos.findById(articleId).map(article -> {
            article.setDesignation(articleRequest.getDesignation());
            article.setMarque(articleRequest.getMarque());
            article.setCategory(articleRequest.getCategory());
            article.setqStock(articleRequest.getqStock());
            articleRepos.save(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("ArticleId " + articleId + " not found"));
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity deleteArticle(@RequestHeader HttpHeaders header, @PathVariable Long articleId) {
        return articleRepos.findById(articleId).map(article -> {
            articleRepos.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("ArticleId " + articleId + " not found"));
    }

    @GetMapping("/articles/categories/{catId}")
    public Page<Article> getAllArticlesByCatId(@RequestHeader HttpHeaders header, @PathVariable (value = "catId") Long catId,
                                                Pageable pageable) {
        return articleRepos.findByCategoryId(catId, pageable);
    }


    @GetMapping("/articles/shops/{shopId}")
    public ResponseEntity<List<Article>> getAllArticlesByShopId(@RequestHeader HttpHeaders header, @PathVariable (value = "shopId") Long shopId,
                                               Pageable pageable) {
        return new ResponseEntity<List<Article>>(articleRepos.findByShopId(shopId, pageable).stream().toList(), HttpStatus.OK);
    }



}
