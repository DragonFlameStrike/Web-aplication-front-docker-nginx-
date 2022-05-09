package com.jarsoft.webapp.adverts.testtask.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
public class MainController {

    @RequestMapping(value = "/")
    public String startPage() {
        return "layout/bannerEmptyView";
    }

    @RequestMapping(value = "/{bid}")
    public String editPage(Model model, @PathVariable("bid") int bid) {
        model.addAttribute("bid",bid);
        return "layout/bannerEmptyView";
    }
    @RequestMapping(value = "/create")
    public String createPage() {
        return "layout/bannerEmptyView";
    }

}
