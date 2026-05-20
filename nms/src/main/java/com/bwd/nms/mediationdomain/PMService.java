package com.bwd.nms.mediationdomain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("service")
public class PMService implements Serializable {

    @Id
    private Long id;

    @Column("serviceid")
    private String serviceId;

    @Column("customer_id")
    private String customerId;

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

    @Column("startdate")
    private LocalDate startDate;

    @Column("enddate")
    private LocalDate endDate;

    @Column("bandwidth")
    private String bandwidth;

    @Column("active")
    private String active;

    @Column("visible_to_customer")
    private String visibleToCustomer;

    @Column("masked")
    private String masked;

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
}
