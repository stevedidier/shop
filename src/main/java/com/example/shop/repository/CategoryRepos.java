package com.example.shop.repository;
import com.example.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface CategoryRepos extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByCatArticlesId(Long articleId);

}
