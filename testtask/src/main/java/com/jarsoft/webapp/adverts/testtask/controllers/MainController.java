package com.jarsoft.webapp.adverts.testtask.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainController {

    @GetMapping("/")
    public String mainView(HttpServletRequest request) {
        System.out.println(request.getRemoteAddr()); // IP ADDRESS
        System.out.println(request.getHeader("User-Agent")); // USER AGENT
        return "layout/emptyView";
    }
    @GetMapping("/categories")
    public String categoriesView(){
        return "layout/categories";
    }
}
