package com.jarsoft.webapp.adverts.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


// Create Table CategoryEntity in MySQL Database
@Entity

public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdCategory;
    @NotEmpty
    private String name;
    @NotEmpty
    private String IdRequest;

    @ManyToMany(mappedBy = "categories")

    List<BannerEntity> banners = new ArrayList<>();

    public CategoryEntity() {
    }


    public CategoryEntity(String name, String idRequest) {
        this.name=name;
        this.IdRequest=idRequest;
    }

    public List<BannerEntity> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerEntity> banners) {
        this.banners = banners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(Long idCategory) {
        IdCategory = idCategory;
    }

    public String getIdRequest() {
        return IdRequest;
    }

    public void setIdRequest(String idRequest) {
        IdRequest = idRequest;
    }
}
