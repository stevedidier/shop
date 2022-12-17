package com.example.shop.repository;

import com.example.shop.model.Article;
import com.example.shop.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepos extends JpaRepository<Article, Long> {

    Page<Article> findByCategoryId(Long CatId, Pageable pageable);
    Page<Article> findById(Long artId, Pageable pageable);

    Page<Article> findByShopId(Long shopId, Pageable pageable);

}
