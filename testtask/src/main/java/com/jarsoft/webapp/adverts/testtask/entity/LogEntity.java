package com.jarsoft.webapp.adverts.testtask.entity;

import javax.persistence.*;

@Entity
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log",nullable = false)
    private Long idLog;
    private String ipAddress;
    private String userAgent;
    private Long time;
    private Long idBanner;
    private String categories;
    private Long price;
    private Boolean noContentReason = false;

    public LogEntity() {
    }


    public LogEntity(String ipAddress, String userAgent, Long time, Long idBanner, Boolean noContentReason) {
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.time = time;
        this.idBanner = idBanner;
        this.noContentReason = noContentReason;
    }

    public LogEntity(String ipAddress, String userAgent, Long idBanner, Boolean noContentReason) {
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.idBanner = idBanner;
        this.noContentReason = noContentReason;
    }

    public Long getIdLog() {
        return idLog;
    }

    public void setIdLog(Long idLog) {
        this.idLog = idLog;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(Long idBanner) {
        this.idBanner = idBanner;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getNoContentReason() {
        return noContentReason;
    }

    public void setNoContentReason(Boolean noContentReason) {
        this.noContentReason = noContentReason;
    }
}
