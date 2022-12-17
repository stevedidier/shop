package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;
import com.example.shop.model.Horaire;
import com.example.shop.repository.ArticleRepos;
import com.example.shop.repository.CategoryRepos;
import com.example.shop.repository.HoraireRepos;
import com.example.shop.repository.ShopRepos;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HoraireController {


    @Autowired
    private HoraireRepos horaireRepos;

    @Autowired
    private ShopRepos shopRepos;

    @GetMapping("/horaires")
    public Page<Horaire> getAllHoraires(Pageable pageable) {
        return horaireRepos.findAll(pageable);
    }

    @GetMapping("/horaire/{horaireId}")
    public Page<Horaire> getAllHoraireId(@PathVariable(value = "horaireId") Long horaireId, Pageable pageable) {
        return horaireRepos.findById(horaireId, pageable);
    }

    @GetMapping("/horaires/shops/{shopId}")
    public Page<Horaire> getAllHoraireByShopId(@PathVariable (value = "shopId") Long shopId,
                                               Pageable pageable) {
        return horaireRepos.findByShopId(shopId, pageable);
    }

    @PostMapping("/horaires/shops/{shopId}")
    public Horaire createHoraire(@PathVariable Long shopId, @Valid @RequestBody Horaire horaire) {
        return shopRepos.findById(shopId).map(shop -> {
            horaire.setShop(shop);
            return horaireRepos.save(horaire);
        }).orElseThrow(() -> new ResourceNotFoundException("ShopId " + shopId + " not found"));

    }

    @PutMapping("/horaires/{horaireId}")
    public Horaire updateHoraire(@PathVariable Long horaireId, @Valid @RequestBody Horaire horaireRequest) {
        return horaireRepos.findById(horaireId).map(horaire -> {
            horaire.setBeginTime(horaireRequest.getBeginTime());
            horaire.setEndTime(horaireRequest.getEndTime());

            return horaireRepos.save(horaire);
        }).orElseThrow(() -> new ResourceNotFoundException("HoraireId " + horaireId + " not found"));
    }

    @DeleteMapping("/horaires/{horaireId}")
    public ResponseEntity<?> deleteHoraire(@PathVariable Long horaireId) {
        return horaireRepos.findById(horaireId).map(horaire -> {
            horaireRepos.delete(horaire);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("HoraireId " + horaireId + " not found"));
    }
}
