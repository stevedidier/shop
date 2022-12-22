package com.example.shop.repository;

import com.example.shop.model.Article;
import com.example.shop.model.Horaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoraireRepos extends JpaRepository<Horaire, Long> {
    List<Horaire> findByShopId(Long shopId);

    List<Horaire> findByShopIdAndWeekNum(Long shopId, int weekNum);
}
