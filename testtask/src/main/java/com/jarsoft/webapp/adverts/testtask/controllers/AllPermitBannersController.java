package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.repositories.BannerRepository;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("/bid")
public class AllPermitBannersController {
    @Autowired
    private BannerRepository bannerRepository;

    @GetMapping("")
    public Optional<BannerEntity> viewBannerWithCategory(HttpServletRequest request, HttpServletResponse response, @RequestParam List<String> cat) {
        System.out.println(request.getRemoteAddr()); // IP ADDRESS
        System.out.println(request.getHeader("User-Agent"));
        System.out.println(cat);
        Optional<BannerEntity> finilBanner = null;
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        List<BannerEntity> BannersWithCategories = StreamSupport.stream(banners.spliterator(), false)
                .filter(banner -> banner.getCategories().stream()
                        .anyMatch(category -> cat.stream().anyMatch(str -> str.trim().equals(category.getIdRequest()))) && !banner.getDeleted()).toList();


        if(BannersWithCategories.isEmpty()) response.setStatus( HttpStatus.SC_NO_CONTENT);
        else{
            finilBanner = BannersWithCategories.stream().max(Comparator.comparing(BannerEntity::getPrice));
        }
        return finilBanner;
    }
}
