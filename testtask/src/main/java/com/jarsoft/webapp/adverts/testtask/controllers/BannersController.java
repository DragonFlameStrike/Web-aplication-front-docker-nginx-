package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.entity.CategoryEntity;
import com.jarsoft.webapp.adverts.testtask.exception.ResourceNotFoundException;
import com.jarsoft.webapp.adverts.testtask.repositories.BannerRepository;
import com.jarsoft.webapp.adverts.testtask.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping("/api")
public class BannersController {
    @Autowired
    private BannerRepository bannerRepository;

    @GetMapping("/")
    public List<BannerEntity> mainView(HttpServletRequest request) {
        System.out.println(request.getRemoteAddr()); // IP ADDRESS
        System.out.println(request.getHeader("User-Agent")); // USER AGENT
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        return Streamable.of(banners).toList();
    }

    @GetMapping("/{bid}")
    public BannerEntity show(@PathVariable("bid") Long bid, Model model) {
        BannerEntity currBanner = bannerRepository.findById(bid).orElseThrow();
        return currBanner;
    }


    @PostMapping("/{bid}")
    public ResponseEntity<BannerEntity> updateBanner(@PathVariable Long bid, @RequestBody BannerEntity bannerDetails){
          BannerEntity banner = bannerRepository.findById(bid).orElseThrow();
          banner.setName(bannerDetails.getName());
          banner.setPrice(bannerDetails.getPrice());
          banner.setText(bannerDetails.getText());
          banner.setCategories(bannerDetails.getCategories());

          BannerEntity updatedBannerEntity = bannerRepository.save(banner);
        return ResponseEntity.ok(updatedBannerEntity);
    }

    @GetMapping("/create")
    public String create() {
        return "";
    }
    @PostMapping("/create")
    public BannerEntity createBanner(@RequestBody BannerEntity banner) {
        System.out.println(banner);
        return bannerRepository.save(banner);
    }
    @DeleteMapping("/{bid}")
    public ResponseEntity<Boolean> deleteBanner(@PathVariable Long bid){
        BannerEntity banner = bannerRepository.findById(bid)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + bid));
        bannerRepository.delete(banner);
        return ResponseEntity.ok(true);
    }

}
