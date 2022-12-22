package com.example.shop.controller;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Article;
import com.example.shop.model.Horaire;
import com.example.shop.model.Shop;
import com.example.shop.repository.ArticleRepos;
import com.example.shop.repository.CategoryRepos;
import com.example.shop.repository.HoraireRepos;
import com.example.shop.repository.ShopRepos;
import com.example.shop.service.HoraireService;
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
    private HoraireService horaireService;

    @GetMapping("/horaires")
    public ResponseEntity<List<Horaire>> getAllHoraires(@RequestHeader HttpHeaders header, Pageable pageable) {
        return new ResponseEntity<List<Horaire>>(horaireService.getAllHoraires(), HttpStatus.OK);
    }

    @GetMapping("/horaires/{horaireId}")
    public ResponseEntity<Horaire> getHorairesById(@RequestHeader HttpHeaders header, @PathVariable(value = "horaireId") Long horaireId, Pageable pageable) {
        return new ResponseEntity<Horaire>(horaireService.getHorairesById(horaireId), HttpStatus.OK);
    }

    @GetMapping("/horaires/shops/{shopId}")
    public ResponseEntity<List<Horaire>> getAllHoraireByShopId(@RequestHeader HttpHeaders header, @PathVariable (value = "shopId") Long shopId,
                                               Pageable pageable) {
        return new ResponseEntity<List<Horaire>>(horaireService.getHorairesByShopId(shopId), HttpStatus.OK);
    }

    @GetMapping("/horaires/shops/{shopId}/{numWeek}")
    public ResponseEntity<List<Horaire>> getHoraireByShopIdByNumWeek(@RequestHeader HttpHeaders header, @PathVariable (value = "shopId") Long shopId, @PathVariable (value = "numWeek") Integer numWeek,
                                                               Pageable pageable) {
        return new ResponseEntity<List<Horaire>>(horaireService.getHoraireByShopIdByNumWeek(shopId, numWeek), HttpStatus.OK);
    }

    @PostMapping("/horaires/shops/{shopId}")
    public ResponseEntity createHoraire(@RequestHeader HttpHeaders header, @PathVariable Long shopId, @Valid @RequestBody Horaire horaire) {

        horaireService.saveOrUpdate(horaire, shopId);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/horaires/{horaireId}")
    public ResponseEntity updateHoraire(@RequestHeader HttpHeaders header, @PathVariable Long horaireId, @Valid @RequestBody Horaire horaireRequest) {

        horaireService.update(horaireRequest, horaireId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/horaires/{horaireId}")
    public ResponseEntity deleteHoraire(@RequestHeader HttpHeaders header, @PathVariable Long horaireId) {

        horaireService.delete(horaireId);

        return ResponseEntity.ok().build();
    }
}
