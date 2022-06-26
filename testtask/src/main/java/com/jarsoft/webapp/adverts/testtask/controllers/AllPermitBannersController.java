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
    @Autowired
    public void setLogRepository(LogRepository logRepository) {
        LogOperations.logRepository = logRepository;
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
        Iterable<LogEntity> logsIterable = LogOperations.getLogsByIpAndUA(request);
        List<LogEntity> logs = LogOperations.filterLogsByTime(logsIterable,time);
        // Filter banners by logs
        BannersWithCategories=BannersWithCategories.stream().filter(banner -> logs.stream().noneMatch(log -> log.getIdBanner().equals(banner.getIdBanner()))).toList();

        boolean successQuery = !BannersWithCategories.isEmpty();
        if(successQuery) {
            finalBanner = BannersWithCategories.stream().max(Comparator.comparing(BannerEntity::getPrice));
            LogOperations.createSuccessLogEntity(request, cat,finalBanner.get().getIdBanner(),finalBanner.get().getPrice());
        }
        else{
            response.setStatus( HttpStatus.SC_NO_CONTENT);
            LogOperations.createNotSuccessLogEntity(request, cat);
        }
        return finalBanner;
    }
    static class LogOperations {
        private static LogRepository logRepository;
        public static Iterable<LogEntity> getLogsByIpAndUA(HttpServletRequest request){
            return logRepository.findAllByIp(request.getRemoteAddr(),request.getHeader("User-Agent"));
        }
        public static List<LogEntity> filterLogsByTime(Iterable<LogEntity> logsIterable,Long time){
            return StreamSupport.stream(logsIterable.spliterator(), false).toList()
                    .stream().filter(logEntity -> !logEntity.getNoContentReason())
                    .filter(logEntity -> time-logEntity.getTime() < ONE_DAY).toList();
        }
        public static void createSuccessLogEntity(HttpServletRequest request,List<String> categoriesInQuery,Long idBanner,Long price){
            LogEntity log = new LogEntity();
            log.setIpAddress(request.getRemoteAddr());
            log.setUserAgent(request.getHeader("User-Agent"));
            log.setTime(new Date().getTime());
            log.setCategories(StringUtils.join(categoriesInQuery, ","));
            log.setNoContentReason(false);
            log.setIdBanner(idBanner);
            log.setPrice(price);
            logRepository.save(log);
        }
        public static void createNotSuccessLogEntity(HttpServletRequest request,List<String> categoriesInQuery){
            LogEntity log = new LogEntity();
            log.setIpAddress(request.getRemoteAddr());
            log.setUserAgent(request.getHeader("User-Agent"));
            log.setTime(new Date().getTime());
            log.setCategories(StringUtils.join(categoriesInQuery, ","));
            log.setNoContentReason(true);
            logRepository.save(log);
        }
    }
}

