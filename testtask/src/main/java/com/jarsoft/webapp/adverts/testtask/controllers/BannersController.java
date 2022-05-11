package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.exception.ResourceNotFoundException;
import com.jarsoft.webapp.adverts.testtask.repositories.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@RestController()
@RequestMapping("/api")
public class BannersController {
    @Autowired
    private BannerRepository bannerRepository;

    @GetMapping("/search/{searchValue}")
    public List<BannerEntity> viewCertainBanners(@PathVariable String searchValue) {
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        List<BannerEntity> filteredBanners = new ArrayList<>();
        for (BannerEntity banner: banners) {
            if(banner.getDeleted() == null || !banner.getDeleted()) {
                String name = banner.getName().toLowerCase();
                searchValue = searchValue.toLowerCase();
                if (name.contains(searchValue)) {
                    filteredBanners.add(banner);
                }
            }
        }
        return filteredBanners;
    }
    @GetMapping("/search/")
    public List<BannerEntity> viewAllBanners(HttpServletRequest request) {
        System.out.println(request.getRemoteAddr()); // IP ADDRESS
        System.out.println(request.getHeader("User-Agent")); // USER AGENT
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        List<BannerEntity> filteredBanners = new ArrayList<>();
        for (BannerEntity banner: banners) {
            if(banner.getDeleted() == null || !banner.getDeleted()) filteredBanners.add(banner);
        }
        return filteredBanners;
    }

    @GetMapping("/{bid}")
    public BannerEntity show(@PathVariable("bid") Long bid) {
        BannerEntity currBanner = bannerRepository.findById(bid).orElseThrow();
        return currBanner;
    }


    @PostMapping("/{bid}")
    public ResponseEntity<BannerEntity> updateBanner(@PathVariable Long bid,
                                                     @Valid @RequestBody BannerEntity bannerDetails) {

        BannerEntity banner = bannerRepository.findById(bid).orElseThrow();
        banner.setName(bannerDetails.getName());
        banner.setPrice(bannerDetails.getPrice());
        banner.setText(bannerDetails.getText());
        banner.setCategories(bannerDetails.getCategories());
        banner.setDeleted(false);
        BannerEntity updatedBannerEntity = bannerRepository.save(banner);

        return ResponseEntity.ok(updatedBannerEntity);
    }

    @GetMapping("/create")
    public String create() {
        return "";
    }


    @PostMapping("/create")
    public BannerEntity createBanner(@Valid @RequestBody BannerEntity banner) {
        return bannerRepository.save(banner);
    }

    @DeleteMapping("/{bid}")
    public ResponseEntity<Boolean> deleteBanner(@PathVariable Long bid){
        BannerEntity banner = bannerRepository.findById(bid)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + bid));
        banner.setDeleted(true);
        bannerRepository.save(banner);
        return ResponseEntity.ok(true);
    }

}
