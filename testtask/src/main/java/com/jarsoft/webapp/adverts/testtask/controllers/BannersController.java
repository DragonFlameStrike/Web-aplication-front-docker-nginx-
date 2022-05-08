package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.entity.CategoryEntity;
import com.jarsoft.webapp.adverts.testtask.repositories.BannerRepository;
import com.jarsoft.webapp.adverts.testtask.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping("/api")
public class BannersController {
    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    /**
     * <h1> Application main page with banners</h1>
     * @param request for get IP and User AGent Info
     * @return bannersList
     */
    @GetMapping("/")
    public List<BannerEntity> mainView(HttpServletRequest request) {
        System.out.println(request.getRemoteAddr()); // IP ADDRESS
        System.out.println(request.getHeader("User-Agent")); // USER AGENT
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        return Streamable.of(banners).toList();
    }

    /**
     * <h1> Application page with banners and edit list for current</h1>
     * @param bid for define current banner
     * @return currBanner
     */
    @GetMapping("/{bid}")
    public BannerEntity show(@PathVariable("bid") Long bid, Model model) {
        BannerEntity currBanner = bannerRepository.findById(bid).orElseThrow();
//        Iterable<CategoryEntity> categories = categoryRepository.findAll();
//        model.addAttribute("categories", categories);
//        Iterable<CategoryEntity> currCategories = currBanner.getCategories();
//        model.addAttribute("currCategories", currCategories);
        return currBanner;
    }

    /**
     * <h1> Post Request to change data in current banner</h1>
     * @param bid for define current banner
     * @param action field from buttons Save/Delete
     * @param name put in BannerEntity.name
     * @param price put in BannerEntity.price
     * @param text put in BannerEntity.text
     * @param categories
     * @return banner-edit.html
     */
    @PostMapping("/{bid}")
    public String updateBanner(
            @PathVariable(value = "bid") long bid,
            @RequestParam String action,
            @RequestParam String name,
            @RequestParam Long price,
            @RequestParam String text,
            @RequestParam List<CategoryEntity> categories){
        BannerEntity banner = bannerRepository.findById(bid).orElseThrow();
        if(action.equals("Save")){
            banner.setName(name);
            banner.setPrice(price);
            banner.setText(text);
            banner.setCategories(categories);
            bannerRepository.save(banner);
        }
        else{
            bannerRepository.delete(banner);
        }
        return "redirect:/";
    }


    /**
     * <h1>  Application page with banners and create list</h1>
     * @param model for insert data in html
     * @return banner-create.html
     */
    @GetMapping("/banner/create")
    public String create(Model model) {
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        model.addAttribute("banners",banners);
        model.addAttribute("categories",categories);
        return "layout/banner-create";
    }
    /**
     * <h1> Post Request to create data in table BannerEntity</h1>
     * @param name put in BannerEntity.name
     * @param price put in BannerEntity.price
     * @param text put in BannerEntity.text
     * @param categories
     * @return bannerEmptyView.html
     */
    @PostMapping("/banner/create")
    public String createBanner(
                             @RequestParam String name,
                             @RequestParam Long price,
                             @RequestParam String text,
                             @RequestParam List<CategoryEntity> categories){
        BannerEntity banner = new BannerEntity(name,price,text,categories);
        bannerRepository.save(banner);
        return "redirect:/";
    }
}
