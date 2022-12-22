package com.example.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "shop_articles",
            joinColumns = { @JoinColumn(name = "shop_id") },
            inverseJoinColumns = { @JoinColumn(name = "article_id") })
    @JsonIgnore
    private Set<Article> shopArticles = new HashSet<>();
    @Transient
    private List<Article> articles = new ArrayList<>();

    @Transient
    private String statut ;

    @Transient
    private int  articleCount ;
    @Transient
    private int  catCount ;

    public Shop() {

    }

    public Shop(String nom, boolean isVacation) {
        this.nom = nom;
        this.isVacation = isVacation;

    }

    public void addArticle(Article article) {

        Shop shop = article.getShops().stream().filter(s -> s.getId() == this.id).findFirst().orElse(null);

        if (shop == null) {
            this.shopArticles.add(article);
            article.getShops().add(this);
        }

    }

    public void removeArticle(long articleId) {
        Article article = this.shopArticles.stream().filter(t -> t.getId() == articleId).findFirst().orElse(null);
        if (article != null) {
            this.shopArticles.remove(article);
        }
    }
    @Transient
    private List<Horaire> horaires ;

    public boolean getIsVacation() {
        return isVacation;
    }

    public void setIsVacation(boolean isVacation) {
        this.isVacation = isVacation;
    }



}
