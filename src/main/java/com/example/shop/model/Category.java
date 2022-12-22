package com.example.shop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Data
@Entity
@Table(name = "categories")
public class Category extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    @NotNull
    private double tva;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "category_articles",
            joinColumns = { @JoinColumn(name = "category_id") },
            inverseJoinColumns = { @JoinColumn(name = "article_id") })
    @JsonIgnore
    private Set<Article> catArticles = new HashSet<>();

    @Transient
    private List<Article> articles = new ArrayList<>();

    public Category() {

    }

    public Category(String title, double tva) {
        this.title = title;
        this.tva = tva;

    }

    public void addArticle(Article article) {
        this.catArticles.add(article);
        article.getCategories().add(this);
    }

    public void removeArticle(long articleId) {
        Article article = this.catArticles.stream().filter(t -> t.getId() == articleId).findFirst().orElse(null);
        if (article != null) {
            this.catArticles.remove(article);
        }
    }

    public Long getId() {
        return id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }
}
