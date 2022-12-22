package com.example.shop.service;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;
import com.example.shop.model.Category;
import com.example.shop.model.Shop;
import com.example.shop.repository.ArticleRepos;
import com.example.shop.repository.CategoryRepos;
import com.example.shop.repository.HoraireRepos;
import com.example.shop.repository.ShopRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class ShopService {
    @Autowired
    private ShopRepos shopRepos;

    @Autowired
    private ArticleRepos articleRepos;

    @Autowired
    private CategoryRepos categoryRepos;


    @Autowired
    private HoraireRepos horaireRepos;

    public List<Shop> getAllShops(){

        List<Shop> shops =  shopRepos.findAll();


        for (Shop shop: shops ) {

            shop.setArticleCount(articleRepos.findShopArticlesByShopsId(shop.getId()).size());

            String statut = shop.getIsVacation() ? "Vacation" : "Work";
            shop.setStatut(statut);


            List<Category> categoryList = new ArrayList<>();

            List<Article> articles = articleRepos.findShopArticlesByShopsId(shop.getId());
            for (Article article:articles) {

                categoryList.addAll(categoryRepos.findCategoriesByCatArticlesId(article.getId()));

            }
            Set<Category> categories = new HashSet<>(categoryList);
            shop.setCatCount(categories.size());



        }
        return shops;
    }


    public Shop  getShopsById(Long shopId){

        Shop shop = shopRepos.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Shop with id = " + shopId));

        List<Article> articles = articleRepos.findShopArticlesByShopsId(shopId);

        if(! articles.isEmpty()){
            shop.setArticles(articles);
        }


        return shop;

    }

    public void saveOrUpdate(Shop shop){
        shopRepos.save(shop);
    }

    public void update(Shop shopRequest, Long shopId){
        String nom = shopRequest.getNom();
        boolean isVacation = shopRequest.getIsVacation();

       long i = shopRepos.findById(shopId).map(shop -> {
            shop.setNom(nom);
            shop.setIsVacation(isVacation);
            shopRepos.save(shop);
            return shop.getId();
        }).orElseThrow(() -> new ResourceNotFoundException("ShopId " + shopId + " not found"));
    }

    public Shop addShop(Shop shopRequest, Long artId){

        Shop shop = articleRepos.findById(artId).map(article -> {
            long shopId = shopRequest.getId();

            // shop is existed
            if (shopId != 0L) {
                Shop _shop = shopRepos.findById(shopId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Shop with id = " + shopId));

                // Begin control : Only one shop for one article
                List<Shop> shops = shopRepos.findShopsByShopArticlesId(article.getId());

                // Association is make only for thoses articles that shopList is empty
                if (shops.isEmpty()){
                    article.getShops().add(_shop);
                    articleRepos.save(article);
                }
                // End Control

                return _shop;
            }

            // add and create new Category
            article.getShops().add(shopRequest);
            return shopRepos.save(shopRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Category with id = " + artId));

        return shop;
    }

    public void delete(Long shopId){
        int i = shopRepos.findById(shopId).map(shop -> {
            shopRepos.delete(shop);
            return 0;
        }).orElseThrow(() -> new ResourceNotFoundException("ShopId " + shopId + " not found"));
    }

}
