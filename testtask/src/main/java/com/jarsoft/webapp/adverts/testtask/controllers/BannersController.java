package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.exception.BadNameException;
import com.jarsoft.webapp.adverts.testtask.exception.NotUniqueNameException;
import com.jarsoft.webapp.adverts.testtask.exception.ResourceNotFoundException;
import com.jarsoft.webapp.adverts.testtask.repositories.BannerRepository;
import com.jarsoft.webapp.adverts.testtask.security.SqlInjectionSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController()
@RequestMapping("/api")
public class BannersController {
    @Autowired
    private BannerRepository bannerRepository;

    @GetMapping("/search/{searchValue}")
    public List<BannerEntity> viewCertainBanners(@PathVariable String searchValue) throws BadNameException {
        if(SqlInjectionSecurity.check(searchValue)) throw new BadNameException();
        Iterable<BannerEntity> banners = bannerRepository.findAllNotDeletedBySearch(searchValue.toLowerCase());
        return StreamSupport.stream(banners.spliterator(), false)
                .collect(Collectors.toList());
    }
    @GetMapping("/search/")
    public List<BannerEntity> viewAllBanners(HttpServletRequest request) {
        System.out.println(request.getRemoteAddr()); // IP ADDRESS
        System.out.println(request.getHeader("User-Agent"));
        Iterable<BannerEntity> banners = bannerRepository.findAllNotDeleted();
        return StreamSupport.stream(banners.spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/{bid}")
    public BannerEntity show(@PathVariable("bid") Long bid) {
        BannerEntity currBanner = bannerRepository.findById(bid).orElseThrow();
        return currBanner;
    }


    @PostMapping("/{bid}")
    public ResponseEntity<BannerEntity> updateBanner(@PathVariable Long bid,
                                                     @Valid @RequestBody BannerEntity bannerDetails) throws NotUniqueNameException, BadNameException {

        if(SqlInjectionSecurity.check(bannerDetails.getName())) throw new BadNameException();
        Iterable<BannerEntity> banners = bannerRepository.findAllNotDeletedByName(bannerDetails.getName());
        for (BannerEntity banner: banners) {
            if(!Objects.equals(banner.getIdBanner(), bid)) {
                if (banner.getName().equals(bannerDetails.getName())) {  //You should re-control result, because query used LOWERCASE context
                    throw new NotUniqueNameException();
                }
            }
        }

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
    public BannerEntity createBanner(@Valid @RequestBody BannerEntity newBanner) throws NotUniqueNameException, BadNameException {
        if(SqlInjectionSecurity.check(newBanner.getName())) throw new BadNameException();
        Iterable<BannerEntity> banners = bannerRepository.findAllNotDeletedByName(newBanner.getName());
        for (BannerEntity banner: banners) {
            if (banner.getName().equals(newBanner.getName())) {  //You should re-control result, because query used LOWERCASE context
                throw new NotUniqueNameException();
            }
        }
        return bannerRepository.save(newBanner);
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
