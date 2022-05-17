package com.jarsoft.webapp.adverts.testtask.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller()
@RequestMapping("/root/categories")
public class CategoryViewController {
    @RequestMapping(value = "/")
    public String startPage() {
        return "layout/categoryEmptyView";
    }
    @RequestMapping(value = "/create")
    public String createPage() {
        return "layout/categoryEmptyView";
    }

    @RequestMapping(value = "/{cid}")
    public String editPage() {
        return "layout/categoryEmptyView";
    }
    @RequestMapping(value = "/{cid}", method = RequestMethod.POST)
    public String updatePage() {
        return "layout/categoryEmptyView";
    }

    @RequestMapping(value = "/{cid}", method = RequestMethod.DELETE)
    public String deletePage() {
        return "layout/categoryEmptyView";
    }

}
