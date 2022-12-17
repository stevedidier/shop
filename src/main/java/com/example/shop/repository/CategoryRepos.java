package com.example.shop.repository;
import com.example.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface CategoryRepos extends JpaRepository<Category, Long> {
    Page<Category> findById(Long catId, Pageable pageable);
}
