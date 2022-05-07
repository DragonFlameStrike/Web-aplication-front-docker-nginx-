package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.repositories.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
@RequestMapping()
public class BannersController {
    @Autowired
    private BannerRepository bannerRepository;

    /**
     * <h1> Application main page with banners</h1>
     * @param request for get IP and User AGent Info
     * @param model for insert data in html
     * @return bannerEmptyView.html
     */
    @GetMapping("/")
    public String mainView(HttpServletRequest request,Model model) {
        System.out.println(request.getRemoteAddr()); // IP ADDRESS
        System.out.println(request.getHeader("User-Agent")); // USER AGENT
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        model.addAttribute("banners",banners);
        return "layout/bannerEmptyView";
    }

    /**
     * <h1> Application page with banners and edit list for current</h1>
     * @param bid for define current banner
     * @param model for insert data in html
     * @return banner-edit.html
     */
    @GetMapping("/{bid}")
    public String show(@PathVariable("bid") Long bid, Model model) {
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        model.addAttribute("banners",banners);
        BannerEntity currBanner = bannerRepository.findById(bid).orElseThrow();
        model.addAttribute("currBanner",currBanner);
        return "layout/banner-edit";
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
            @RequestParam String categories){
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
        model.addAttribute("banners",banners);
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
                             @RequestParam String categories){
        BannerEntity banner = new BannerEntity(name,price,text,categories);
        bannerRepository.save(banner);
        return "redirect:/";
    }
}
