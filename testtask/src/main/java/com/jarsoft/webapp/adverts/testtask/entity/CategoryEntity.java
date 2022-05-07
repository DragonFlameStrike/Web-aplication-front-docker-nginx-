package com.jarsoft.webapp.adverts.testtask.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

// Create Table CategoryEntity in MySQL Database
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdCategory;
    private String name;
    private String IdRequest;
    public CategoryEntity() {
    }

    public CategoryEntity(String name, String idRequest) {
        this.name=name;
        this.IdRequest=idRequest;
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
