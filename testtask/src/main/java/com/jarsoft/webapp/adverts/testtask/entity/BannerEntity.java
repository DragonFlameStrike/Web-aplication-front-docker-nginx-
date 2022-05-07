package com.jarsoft.webapp.adverts.testtask.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


// Create Table BannerEntity in MySQL Database
@Entity
public class BannerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdBanner;
    private String name;
    private Long price;
    private String text;
    private String categories;

    public BannerEntity() {
    }

    public BannerEntity(String name, Long price, String text, String categories) {
        this.name = name;
        this.price = price;
        this.text = text;
        this.categories = categories;
    }

    public Long getIdBanner() {
        return IdBanner;
    }

    public void setIdBanner(Long idBanner) {
        IdBanner = idBanner;
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
