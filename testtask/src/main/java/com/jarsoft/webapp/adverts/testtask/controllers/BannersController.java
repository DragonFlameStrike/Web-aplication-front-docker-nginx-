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

    @GetMapping("/")
    public String mainView(HttpServletRequest request,Model model) {
        System.out.println(request.getRemoteAddr()); // IP ADDRESS
        System.out.println(request.getHeader("User-Agent")); // USER AGENT
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        model.addAttribute("banners",banners);
        return "layout/emptyView";
    }

    @GetMapping("/{bid}")
    public String show(@PathVariable("bid") Long bid, Model model) {
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        model.addAttribute("banners",banners);
        Optional<BannerEntity> currBanner = bannerRepository.findById(bid);
        model.addAttribute("currBanner",currBanner);
        return "layout/banner-edit";
    }
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

    @GetMapping("/banner/create")
    public String create(Model model) {
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        model.addAttribute("banners",banners);
        return "layout/banner-create";
    }
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
