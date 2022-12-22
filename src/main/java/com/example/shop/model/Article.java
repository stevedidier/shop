package com.example.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Builder;
import lombok.Builder;
import lombok.Data;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Data
@Entity
@Table(name = "articles")
public class Article extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String designation;

    @NotNull
    private String marque;



    @NotNull
    private double prixUnit;

    @Lob
    @Column(name = "image", columnDefinition="OID")
    private byte[] image;


    @NotNull
    private double qStock;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "category_articles",
            joinColumns = { @JoinColumn(name = "article_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") })

    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    @Transient
    private List<Category> categoryList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "shop_articles",
            joinColumns = { @JoinColumn(name = "article_id") },
            inverseJoinColumns = { @JoinColumn(name = "shop_id") })

    @JsonIgnore
    private Set<Shop> shops = new HashSet<>();

    @Transient
    private Shop shop ;

    public Long getId() {
        return id;
    }



    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public double getPrixUnit() {
        return prixUnit;
    }

    public void setPrixUnit(double prixUnit) {
        this.prixUnit = prixUnit;
    }

    public double getqStock() {
        return qStock;
    }

    public void setqStock(double qStock) {
        this.qStock = qStock;
    }


}
