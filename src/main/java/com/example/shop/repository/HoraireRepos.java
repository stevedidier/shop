package com.example.shop.repository;

import com.example.shop.model.Article;
import com.example.shop.model.Horaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraireRepos extends JpaRepository<Horaire, Long> {
    Page<Horaire> findByShopId(Long shopId, Pageable pageable);
    Page<Horaire> findById(Long HoraireId, Pageable pageable);
}
