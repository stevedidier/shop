package com.example.shop.service;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Horaire;
import com.example.shop.model.Shop;
import com.example.shop.repository.HoraireRepos;
import com.example.shop.repository.ShopRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoraireService {

    @Autowired
    private HoraireRepos horaireRepos;

    @Autowired
    private ShopRepos shopRepos;

    public List<Horaire> getAllHoraires(){
        return horaireRepos.findAll();
    }

    public Horaire getHorairesById(Long horaireId){

        Horaire horaire = horaireRepos.findById(horaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Horaire with id = " + horaireId));

        return horaire;
    }

    public List<Horaire> getHorairesByShopId(Long shopId){

        return horaireRepos.findByShopId(shopId);

    }

    public List<Horaire> getHoraireByShopIdByNumWeek(Long shopId,  int weekNum){

        return horaireRepos.findByShopIdAndWeekNum(shopId, weekNum);

    }

    public void saveOrUpdate(Horaire horaire, Long shopId){

        long id = shopRepos.findById(shopId).map(shop -> {
            horaire.setShop(shop);
            horaireRepos.save(horaire);
            return horaire.getId();
        }).orElseThrow(() -> new ResourceNotFoundException("ShopId " + shopId + " not found"));

    }

    public void update(Horaire horaireRequest, Long horaireId){

        long i = horaireRepos.findById(horaireId).map(horaire -> {
            horaire.setBeginTime(horaireRequest.getBeginTime());
            horaire.setEndTime(horaireRequest.getEndTime());

            horaireRepos.save(horaire);
            return horaire.getId();
        }).orElseThrow(() -> new ResourceNotFoundException("HoraireId " + horaireId + " not found"));
    }

    public void delete(Long horaireId){

        int i = horaireRepos.findById(horaireId).map(horaire -> {
            horaireRepos.delete(horaire);
            return 0;
        }).orElseThrow(() -> new ResourceNotFoundException("HoraireId " + horaireId + " not found"));
    }



}
