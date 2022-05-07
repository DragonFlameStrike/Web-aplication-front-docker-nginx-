package com.jarsoft.webapp.adverts.testtask.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class CategoriesController {

    @GetMapping("/categories")
    public String categoriesView(){
        return "layout/categories";
    }
}
