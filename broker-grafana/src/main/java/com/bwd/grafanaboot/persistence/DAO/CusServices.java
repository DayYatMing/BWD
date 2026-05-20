package com.bwd.grafanaboot.persistence.DAO;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "service")
public class CusServices implements Serializable {
    private static final long serialVersionUID = 1L;


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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(String bandWidth) {
        this.bandWidth = bandWidth;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serviceid")
    private String serviceId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "startdate")
    private String startDate;

    @Column(name = "enddate")
    private String endDate;

    @Column(name = "bandwidth")
    private String bandWidth;

    @Column(name = "active")
    private String active;

    @Column(name = "visible_to_customer")
    private String visibleToCustomer;

    @Column(name = "masked")
    private String masked;

}
