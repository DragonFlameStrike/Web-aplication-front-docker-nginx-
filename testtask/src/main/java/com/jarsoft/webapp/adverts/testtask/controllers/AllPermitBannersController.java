package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.exception.BadNameException;
import com.jarsoft.webapp.adverts.testtask.repositories.BannerRepository;
import com.jarsoft.webapp.adverts.testtask.security.SqlInjectionSecurity;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("/bid")
public class AllPermitBannersController {
    @Autowired
    private BannerRepository bannerRepository;

    @GetMapping("")
    public BannerEntity viewBannerWithCategory(HttpServletRequest request,@RequestParam List<String> cat) {
        System.out.println(request.getRemoteAddr()); // IP ADDRESS
        System.out.println(request.getHeader("User-Agent"));
        System.out.println(cat);
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        List<BannerEntity> output = StreamSupport.stream(banners.spliterator(), false)
                .filter(banner -> banner.getCategories().stream()
                        .anyMatch(category -> cat.stream().anyMatch(str -> str.trim().equals(category.getIdRequest()))) && !banner.getDeleted()).toList();

        return output.stream()
                .max(Comparator.comparing(BannerEntity::getPrice)).orElseThrow(NoSuchElementException::new);
    }
}
