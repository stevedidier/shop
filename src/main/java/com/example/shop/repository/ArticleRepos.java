package com.example.shop.repository;

import com.example.shop.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepos extends JpaRepository<Article, Long> {

    List<Article> findCatArticlesByCategoriesId(Long CatId);



    List<Article> findShopArticlesByShopsId(Long shopId);


}
