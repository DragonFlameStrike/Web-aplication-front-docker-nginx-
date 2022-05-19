package com.jarsoft.webapp.adverts.testtask.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <h1>This class return html page, which caught by app.js and edited</h1>
 */
@Controller()
@RequestMapping(value = "/root")
public class BannerViewController {
    @RequestMapping(value = "/")
    public String startPage() {
        return "layout/bannerEmptyView";
    }
    @RequestMapping(value = "/create")
    public String createPage() {
        return "layout/bannerEmptyView";
    }

    @RequestMapping(value = "/{ignoredBid}")
    public String editPage(@PathVariable String ignoredBid) {
        return "layout/bannerEmptyView";
    }
    @RequestMapping(value = "/{ignoredBid}", method = RequestMethod.POST)
    public String updatePage(@PathVariable String ignoredBid) {
        return "layout/bannerEmptyView";
    }

    @RequestMapping(value = "/{ignoredBid}", method = RequestMethod.DELETE)
    public String deletePage(@PathVariable String ignoredBid) {
        return "layout/bannerEmptyView";
    }

}
