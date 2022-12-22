package com.example.shop.service;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;
import com.example.shop.model.Shop;
import com.example.shop.repository.ArticleRepos;
import com.example.shop.repository.CategoryRepos;
import com.example.shop.repository.ShopRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private CategoryRepos categoryRepos;

    @Autowired
    private ShopRepos shopRepos;

    @Autowired
    private ArticleRepos articleRepos;

    public List<Article> getAllArticles(){
        return articleRepos.findAll();
    }

    public Article getArticlesById(Long artId){

        Article article = articleRepos.findById(artId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + artId));

        article.setCategoryList(categoryRepos.findCategoriesByCatArticlesId(artId));

        List<Shop> shops = shopRepos.findShopsByShopArticlesId(artId);

        if(!shops.isEmpty()){
            article.setShop(shopRepos.findShopsByShopArticlesId(artId).get(0));
        }


        return article;
    }

    public void saveOrUpdate(Article article){
        articleRepos.save(article);
    }

    public Article addArticle(Article articleRequest, Long catId){

        Article article = categoryRepos.findById(catId).map(category -> {
            long articleId = articleRequest.getId();

            // tag is existed
            if (articleId != 0L) {
                Article _article = articleRepos.findById(articleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Article with id = " + articleId));
                category.getCatArticles().add(_article);
                categoryRepos.save(category);
                return _article;
            }

            // add and create new Tag
            category.addArticle(articleRequest);
            return articleRepos.save(articleRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Category with id = " + catId));

        return article;
    }

    public void update(Article articleRequest, Long articleId){

        long i = articleRepos.findById(articleId).map(article -> {
            article.setDesignation(articleRequest.getDesignation());
            article.setMarque(articleRequest.getMarque());

            article.setqStock(articleRequest.getqStock());
            articleRepos.save(article);
            return article.getId();
        }).orElseThrow(() -> new ResourceNotFoundException("ArticleId " + articleId + " not found"));
    }

    public void delete(Long articleId){

        int i = articleRepos.findById(articleId).map(article -> {
            articleRepos.delete(article);
            return 0;
        }).orElseThrow(() -> new ResourceNotFoundException("ArticleId " + articleId + " not found"));
    }

    public List<Article> getAllArticlesByCatId(Long catId){
        return articleRepos.findCatArticlesByCategoriesId(catId);
    }

    public List<Article> getAllArticlesByShopId(Long shopId){
        return articleRepos.findShopArticlesByShopsId(shopId);
    }
}
