package com.example.shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "shops")
public class Shop extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nom;
    @NotNull
    private boolean isVacation;

    @Transient
    private List<Article> articles ;
    @Transient
    private List<Horaire> horaires ;

    public boolean getIsVacation() {
        return isVacation;
    }

    public void setIsVacation(boolean isVacation) {
        this.isVacation = isVacation;
    }



}
