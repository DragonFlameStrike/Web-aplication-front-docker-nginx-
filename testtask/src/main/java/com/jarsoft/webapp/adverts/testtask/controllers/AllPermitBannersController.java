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
import java.util.stream.StreamSupport;


/**
 * <h1>This RestController exists to catch queries from all users</h1>
 *
 * param "http://window.location.hostname:8080/bid?cat=cat1&cat=cat2&..."<p>
 * throws HttpStatus.SC_NO_CONTENT<p>
 * return BannerEntity
 */
@RestController()
@RequestMapping("/bid")
public class AllPermitBannersController {
    public static final Long ONE_DAY = 86400000L;
    private BannerRepository bannerRepository;
    @Autowired
    public void setBannerRepository(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }
    private LogRepository logRepository;
    @Autowired
    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * <h1>This function catch query, filters all banners by params and create LogEntity for log table</h1>
     *
     * @param request to get IP Addr
     * @param response to get User-Agent
     * @param cat categories
     * @return BannerEntity or HttpStatus.SC_NO_CONTENT
     */
    @GetMapping()
    public Optional<BannerEntity> viewBannerWithCategory(HttpServletRequest request, HttpServletResponse response, @RequestParam List<String> cat) {
        Optional<BannerEntity> finalBanner = Optional.empty();
        Iterable<BannerEntity> banners = bannerRepository.findAll();
        // Filter banners by categories and deleted fields
        List<BannerEntity> BannersWithCategories = StreamSupport.stream(banners.spliterator(), false)
                .filter(banner -> banner.getCategories().stream()
                        .anyMatch(category -> cat.stream().anyMatch(str -> str.trim().equals(category.getIdRequest()))) && !banner.getDeleted()).toList();



        Long time = new Date().getTime();
        Iterable<LogEntity> logsIterable = logRepository.findAllByIp(request.getRemoteAddr(),request.getHeader("User-Agent"));
        // Filter logs by time
        List<LogEntity> logs = StreamSupport.stream(logsIterable.spliterator(), false).toList()
                .stream().filter(logEntity -> !logEntity.getNoContentReason())
                .filter(logEntity -> time-logEntity.getTime() < ONE_DAY).toList();
        // Filter banners by logs
        BannersWithCategories=BannersWithCategories.stream().filter(banner -> logs.stream().noneMatch(log -> log.getIdBanner().equals(banner.getIdBanner()))).toList();

        // Create new log to insert into log table
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
