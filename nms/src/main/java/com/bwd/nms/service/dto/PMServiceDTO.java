package com.bwd.nms.service.dto;

import com.bwd.nms.mediationdomain.PMService;
import java.io.Serializable;
import java.time.LocalDate;

public class PMServiceDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getVisibleToCustomer() {
        return visibleToCustomer;
    }

    public void setVisibleToCustomer(String visibleToCustomer) {
        this.visibleToCustomer = visibleToCustomer;
    }

    public String getMasked() {
        return masked;
    }

    public void setMasked(String masked) {
        this.masked = masked;
    }

    private String serviceId;

    private String customerId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String bandwidth;

    private String active;

    private String visibleToCustomer;

    private String masked;

    public PMServiceDTO(PMService pmService) {
        this.id = pmService.getId();
        this.serviceId = pmService.getServiceId();
        this.customerId = pmService.getCustomerId();
        this.startDate = pmService.getStartDate();
        this.endDate = pmService.getEndDate();
        this.bandwidth = pmService.getBandwidth();
        this.active = pmService.getActive();
        this.visibleToCustomer = pmService.getVisibleToCustomer();
        this.masked = pmService.getMasked();
    }

    public PMServiceDTO() {}
}
