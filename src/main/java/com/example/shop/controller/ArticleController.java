package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;
import com.example.shop.model.Category;
import com.example.shop.repository.ArticleRepos;
import com.example.shop.repository.CategoryRepos;
import com.example.shop.repository.ShopRepos;
import com.example.shop.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@CrossOrigin(origins = "*")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles(@RequestHeader HttpHeaders header, Pageable pageable) {
        return new ResponseEntity<List<Article>>(articleService.getAllArticles(), HttpStatus.OK);
    }



    @GetMapping("/articles/{artId}")
    public ResponseEntity<Article> getArticlesById(@RequestHeader HttpHeaders header, @PathVariable (value = "artId") Long artId, Pageable pageable) {

        return new ResponseEntity<>(articleService.getArticlesById(artId), HttpStatus.OK);
    }

    @PostMapping("/articles")
    public ResponseEntity createArticle(@Valid @RequestBody Article article, @RequestHeader HttpHeaders header) {
        articleService.saveOrUpdate(article);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/categories/{catId}/articles")
    public ResponseEntity<Article> addArticle(@PathVariable(value = "catId") Long catId, @RequestBody Article articleRequest) {

        return new ResponseEntity<>(articleService.addArticle(articleRequest, catId), HttpStatus.CREATED);
    }





    @PutMapping("/articles/{articleId}")
    public ResponseEntity updateArticle(@RequestHeader HttpHeaders header, @PathVariable Long articleId, @Valid @RequestBody Article articleRequest) {

        articleService.update(articleRequest, articleId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity deleteArticle(@RequestHeader HttpHeaders header, @PathVariable Long articleId) {

        articleService.delete(articleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/articles/categories/{catId}")
    public ResponseEntity<List<Article>> getAllArticlesByCatId(@RequestHeader HttpHeaders header, @PathVariable (value = "catId") Long catId,
                                                Pageable pageable) {
        return new ResponseEntity<List<Article>>(articleService.getAllArticlesByCatId(catId), HttpStatus.OK);
    }


    @GetMapping("/articles/shops/{shopId}")
    public ResponseEntity<List<Article>> getAllArticlesByShopId(@RequestHeader HttpHeaders header, @PathVariable (value = "shopId") Long shopId,
                                               Pageable pageable) {
        return new ResponseEntity<List<Article>>(articleService.getAllArticlesByShopId(shopId), HttpStatus.OK);
    }



}
