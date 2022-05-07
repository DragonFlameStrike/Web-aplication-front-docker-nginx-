package com.jarsoft.webapp.adverts.testtask.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BannerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdBanner;
    private String name;
    private Long price;

    public BannerEntity() {
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
}
