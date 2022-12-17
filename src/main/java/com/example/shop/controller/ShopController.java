package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;

import com.example.shop.model.Horaire;
import com.example.shop.model.Shop;
import com.example.shop.repository.ArticleRepos;

import com.example.shop.repository.HoraireRepos;
import com.example.shop.repository.ShopRepos;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShopController {

    @Autowired
    private ShopRepos shopRepos;

    @Autowired
    private ArticleRepos articleRepos;


    @Autowired
    private HoraireRepos horaireRepos;

    @GetMapping("/shops")
    public Page<Shop> getAllShops(Pageable pageable) {
        return shopRepos.findAll(pageable);
    }

    @GetMapping("/shops/{shopId}")
    public Page<Shop> getAllArticlesById(@PathVariable(value = "shopId") Long shopId, Pageable pageable) {
        // les variable shops, article et horaire  sont immutables
        List<Shop> shops =  shopRepos.findById(shopId, pageable).stream().toList();

        List<Article> articles =  articleRepos.findByShopId(shopId, pageable).stream().toList();

        List<Horaire> horaires = horaireRepos.findByShopId(shopId, pageable).stream().toList();

        // debut du clonage de la variable shops dans shops1

        Shop shop = shops.get(0);

        Shop shop1 = new Shop();
        shop1.setId(shop.getId());
        shop1.setNom(shop.getNom());
        shop1.setIsVacation(shop.getIsVacation());
        shop1.setArticles(articles);
        shop1.setHoraires(horaires);


        List<Shop> shops1 = new ArrayList<Shop>();
        shops1.add(shop1);

        // fin du clonage

        // System.out.println(shops1.get(0).getArticles().get(0).getDesignation());

        // Convertir la list shops1 en Page<Shop>

        final Page<Shop> shopPage = new PageImpl<>(shops1);
        //shop.setArticles(articles);


        return shopPage;
    }


    @PostMapping("/shops")
    public Shop createShop(@Valid @RequestBody Shop shop) {
        return shopRepos.save(shop);
    }

    @PutMapping("/shops/{shopId}")
    public Shop updateShop(@PathVariable Long shopId, @Valid @RequestBody Shop shopRequest) {
        String nom = shopRequest.getNom();
        boolean isVacation = shopRequest.getIsVacation();

        return shopRepos.findById(shopId).map(shop -> {
            shop.setNom(nom);
            shop.setIsVacation(isVacation);
            return shopRepos.save(shop);
        }).orElseThrow(() -> new ResourceNotFoundException("ShopId " + shopId + " not found"));
    }

    @DeleteMapping("/shops/{shopId}")
    public ResponseEntity<?> deleteShop(@PathVariable Long shopId) {
        return shopRepos.findById(shopId).map(shop -> {
            shopRepos.delete(shop);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("ShopId " + shopId + " not found"));
    }

}
