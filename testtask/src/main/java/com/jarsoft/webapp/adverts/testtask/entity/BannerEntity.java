package com.jarsoft.webapp.adverts.testtask.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


// Create Table BannerEntity in MySQL Database
@Entity
public class BannerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banner",nullable = false)
    private Long IdBanner;
    private String name;
    private Long price;
    private String text;
    @ManyToMany
    @JoinTable(
            name = "bannersandcategories",
            joinColumns = @JoinColumn(name = "IdBanner"),
            inverseJoinColumns = @JoinColumn(name = "IdCategory"))

    @JsonIgnoreProperties("banners")
    private List<CategoryEntity> categories = new ArrayList<>();

    public BannerEntity() {
    }

    public BannerEntity(String name, Long price, String text, List<CategoryEntity> categories) {
        super();
        this.name = name;
        this.price = price;
        this.text = text;
        this.categories.addAll(categories);
    }

    public Long getIdBanner() {
        return IdBanner;
    }

    public void setIdBanner(Long idBanner) {
        IdBanner = idBanner;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories=categories;
    }
}
