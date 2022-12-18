package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;
import com.example.shop.model.Horaire;
import com.example.shop.model.Shop;
import com.example.shop.repository.ArticleRepos;
import com.example.shop.repository.CategoryRepos;
import com.example.shop.repository.HoraireRepos;
import com.example.shop.repository.ShopRepos;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class HoraireController {


    @Autowired
    private HoraireRepos horaireRepos;

    @Autowired
    private ShopRepos shopRepos;

    @GetMapping("/horaires")
    public ResponseEntity<List<Horaire>> getAllHoraires(@RequestHeader HttpHeaders header, Pageable pageable) {
        return new ResponseEntity<List<Horaire>>(horaireRepos.findAll(pageable).stream().toList(), HttpStatus.OK);
    }

    @GetMapping("/horaires/{horaireId}")
    public ResponseEntity<Horaire> getAllHoraireById(@RequestHeader HttpHeaders header, @PathVariable(value = "horaireId") Long horaireId, Pageable pageable) {
        return new ResponseEntity<Horaire>(horaireRepos.findById(horaireId, pageable).stream().toList().get(0), HttpStatus.OK);
    }

    @GetMapping("/horaires/shops/{shopId}")
    public ResponseEntity<List<Horaire>> getAllHoraireByShopId(@RequestHeader HttpHeaders header, @PathVariable (value = "shopId") Long shopId,
                                               Pageable pageable) {
        return new ResponseEntity<List<Horaire>>(horaireRepos.findByShopId(shopId, pageable).stream().toList(), HttpStatus.OK);
    }

    @PostMapping("/horaires/shops/{shopId}")
    public ResponseEntity createHoraire(@RequestHeader HttpHeaders header, @PathVariable Long shopId, @Valid @RequestBody Horaire horaire) {
        return shopRepos.findById(shopId).map(shop -> {
            horaire.setShop(shop);
            horaireRepos.save(horaire);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("ShopId " + shopId + " not found"));

    }

    @PutMapping("/horaires/{horaireId}")
    public ResponseEntity updateHoraire(@RequestHeader HttpHeaders header, @PathVariable Long horaireId, @Valid @RequestBody Horaire horaireRequest) {
        return horaireRepos.findById(horaireId).map(horaire -> {
            horaire.setBeginTime(horaireRequest.getBeginTime());
            horaire.setEndTime(horaireRequest.getEndTime());

            horaireRepos.save(horaire);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("HoraireId " + horaireId + " not found"));
    }

    @DeleteMapping("/horaires/{horaireId}")
    public ResponseEntity deleteHoraire(@RequestHeader HttpHeaders header, @PathVariable Long horaireId) {
        return horaireRepos.findById(horaireId).map(horaire -> {
            horaireRepos.delete(horaire);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("HoraireId " + horaireId + " not found"));
    }
}
