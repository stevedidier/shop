package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;

import com.example.shop.model.Category;
import com.example.shop.model.Horaire;
import com.example.shop.model.Shop;
import com.example.shop.repository.ArticleRepos;

import com.example.shop.repository.CategoryRepos;
import com.example.shop.repository.HoraireRepos;
import com.example.shop.repository.ShopRepos;
import com.example.shop.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "*")
public class ShopController {


    @Autowired
    private ShopService shopService;



    @GetMapping("/shops")
    public ResponseEntity<List<Shop>> getAllShops(@RequestHeader HttpHeaders header, Pageable pageable ) {

        return new ResponseEntity<List<Shop>>(shopService.getAllShops(), HttpStatus.OK);
    }

    @GetMapping("/shops/{shopId}")
    public ResponseEntity<Shop> getShopsById(@RequestHeader HttpHeaders header, @PathVariable(value = "shopId") Long shopId, Pageable pageable) {


        return new ResponseEntity<>(shopService.getShopsById(shopId), HttpStatus.OK);

    }


    @PostMapping("/shops")
    public ResponseEntity createShop(@Valid @RequestBody Shop shop,  @RequestHeader HttpHeaders header) {
         shopService.saveOrUpdate(shop);
         return ResponseEntity.ok().build();

    }

    @PutMapping("/shops/{shopId}")
    public ResponseEntity updateShop(@PathVariable Long shopId, @Valid @RequestBody Shop shopRequest, @RequestHeader HttpHeaders header) {

        shopService.update(shopRequest,shopId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/articles/{artId}/shops")
    public ResponseEntity<Shop> addShop(@PathVariable(value = "artId") Long artId, @RequestBody Shop shopRequest) {

        return new ResponseEntity<>(shopService.addShop(shopRequest, artId), HttpStatus.CREATED);
    }

    @DeleteMapping("/shops/{shopId}")
    public ResponseEntity deleteShop(@PathVariable Long shopId, @RequestHeader HttpHeaders header) {
        shopService.delete(shopId);
        return ResponseEntity.ok().build();
    }

}
