package com.example.shop.service;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;
import com.example.shop.model.Category;
import com.example.shop.repository.ArticleRepos;
import com.example.shop.repository.CategoryRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepos categoryRepos;
    @Autowired
    private ArticleRepos articleRepos;

    public List<Category> getAllCategories(){

        return categoryRepos.findAll();
    }

    public Category getCategoriesId(Long catId){

        Category category = categoryRepos.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Category with id = " + catId));

        List<Article> articles = articleRepos.findCatArticlesByCategoriesId(catId);
        if(!articles.isEmpty()){
            category.setArticles(articles);
        }


        return category;
    }

    public void saveOrUpdate(Category category){
        categoryRepos.save(category);
    }

    public void update(Category categoryRequest, Long catId){

        String title = categoryRequest.getTitle();
        double tva = categoryRequest.getTva();

    long id = categoryRepos.findById(catId).map(category -> {
            category.setTitle(title);
            category.setTva(tva);
            categoryRepos.save(category);
            return category.getId();

        }).orElseThrow(() -> new ResourceNotFoundException("CatId " + catId + " not found"));
    }

    public Category addCategory(Category categoryRequest, Long artId){
        Category category = articleRepos.findById(artId).map(article -> {
            long catId = categoryRequest.getId();

            // tag is existed
            if (catId != 0L) {
                Category _category = categoryRepos.findById(catId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Category with id = " + catId));
                article.getCategories().add(_category);
                articleRepos.save(article);
                return _category;
            }

            // add and create new Category
            article.getCategories().add(categoryRequest);
            return categoryRepos.save(categoryRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Category with id = " + artId));

        return category;
    }

    public void delete(Long catId){

        int i = categoryRepos.findById(catId).map(category -> {
            categoryRepos.delete(category);
            return 0;
        }).orElseThrow(() -> new ResourceNotFoundException("CatId " + catId + " not found"));
    }
}
