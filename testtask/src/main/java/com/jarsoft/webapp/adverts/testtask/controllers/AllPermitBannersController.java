package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.entity.LogEntity;
import com.jarsoft.webapp.adverts.testtask.repositories.BannerRepository;
import com.jarsoft.webapp.adverts.testtask.repositories.LogRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController()
@RequestMapping("/bid")
public class AllPermitBannersController {
    public static final Long ONE_DAY = 86400000L;
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private LogRepository logRepository;

    @GetMapping("")
    public Optional<BannerEntity> viewBannerWithCategory(HttpServletRequest request, HttpServletResponse response, @RequestParam List<String> cat) {
        Optional<BannerEntity> finalBanner = Optional.empty();
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        List<BannerEntity> BannersWithCategories = StreamSupport.stream(banners.spliterator(), false)
                .filter(banner -> banner.getCategories().stream()
                        .anyMatch(category -> cat.stream().anyMatch(str -> str.trim().equals(category.getIdRequest()))) && !banner.getDeleted()).toList();



        Long time = new Date().getTime();
        Iterable<LogEntity> logsIterable = logRepository.findAllByIp(request.getRemoteAddr(),request.getHeader("User-Agent"));
        List<LogEntity> logs = StreamSupport.stream(logsIterable.spliterator(), false).collect(Collectors.toList())
                .stream().filter(logEntity -> !logEntity.getNoContentReason())
                .filter(logEntity -> time-logEntity.getTime() < ONE_DAY).toList();
        BannersWithCategories=BannersWithCategories.stream().filter(banner -> logs.stream().noneMatch(log -> log.getIdBanner().equals(banner.getIdBanner()))).toList();

        LogEntity log = new LogEntity();
        if(BannersWithCategories.isEmpty()) {
            response.setStatus( HttpStatus.SC_NO_CONTENT);
            log.setIpAddress(request.getRemoteAddr());
            log.setUserAgent(request.getHeader("User-Agent"));
            log.setTime(new Date().getTime());
            log.setCategories(StringUtils.join(cat, ","));
            log.setNoContentReason(true);
        }
        else{
            finalBanner = BannersWithCategories.stream().max(Comparator.comparing(BannerEntity::getPrice));
            log.setIpAddress(request.getRemoteAddr());
            log.setUserAgent(request.getHeader("User-Agent"));
            log.setTime(new Date().getTime());
            log.setCategories(StringUtils.join(cat, ","));
            log.setNoContentReason(false);
            log.setIdBanner(finalBanner.get().getIdBanner());
            log.setPrice(finalBanner.get().getPrice());
        }
        logRepository.save(log);
        return finalBanner;
    }
}
