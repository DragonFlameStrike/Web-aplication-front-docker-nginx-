package com.jarsoft.webapp.adverts.testtask.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller()
public class MainController {

    @RequestMapping(value = "/")
    public String startPage() {
        return "layout/bannerEmptyView";
    }

    @RequestMapping(value = "/create")
    public String createPage() {
        return "layout/bannerEmptyView";
    }

    @RequestMapping(value = "/{bid}")
    public String editPage() {
        return "layout/bannerEmptyView";
    }
    @RequestMapping(value = "/{bid}", method = RequestMethod.POST)
    public String updatePage() {
        return "layout/bannerEmptyView";
    }

    @RequestMapping(value = "/{bid}", method = RequestMethod.DELETE)
    public String deletePage() {
        return "layout/bannerEmptyView";
    }

}
