package com.example.shop.repository;

import com.example.shop.model.Article;
import com.example.shop.model.Category;
import com.example.shop.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepos extends JpaRepository<Shop, Long> {

    List<Shop> findShopsByShopArticlesId(Long articleId);




}
