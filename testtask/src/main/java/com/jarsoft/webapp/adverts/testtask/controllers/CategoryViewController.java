package com.jarsoft.webapp.adverts.testtask.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <h1>This Controller return html page, which caught by app.js and edited</h1>
 */
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

    @RequestMapping(value = "/{ignoredCid}")
    public String editPage(@PathVariable String ignoredCid) {
        return "layout/categoryEmptyView";
    }
    @RequestMapping(value = "/{ignoredCid}", method = RequestMethod.POST)
    public String updatePage(@PathVariable String ignoredCid) {
        return "layout/categoryEmptyView";
    }

    @RequestMapping(value = "/{ignoredCid}", method = RequestMethod.DELETE)
    public String deletePage(@PathVariable String ignoredCid) {
        return "layout/categoryEmptyView";
    }

}
