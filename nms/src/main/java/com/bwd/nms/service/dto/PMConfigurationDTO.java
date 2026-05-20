package com.bwd.nms.service.dto;

import com.bwd.nms.mediationdomain.PMConfiguration;
import java.io.Serializable;

public class PMConfigurationDTO implements Serializable {

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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getRouteDirection() {
        return routeDirection;
    }

    public void setRouteDirection(String routeDirection) {
        this.routeDirection = routeDirection;
    }

    public String getSegmentDirection() {
        return segmentDirection;
    }

    public void setSegmentDirection(String segmentDirection) {
        this.segmentDirection = segmentDirection;
    }

    public String getDisableCollection() {
        return disableCollection;
    }

    public void setDisableCollection(String disableCollection) {
        this.disableCollection = disableCollection;
    }

    public String getVisibleToCustomer() {
        return visibleToCustomer;
    }

    public void setVisibleToCustomer(String visibleToCustomer) {
        this.visibleToCustomer = visibleToCustomer;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getMasked() {
        return masked;
    }

    public void setMasked(Integer masked) {
        this.masked = masked;
    }

    private Long id;
    private String serviceId;
    private String sourceName;
    private String nodeId;
    private String vendor;
    private String labelName;
    private String routeDirection;
    private String segmentDirection;
    private String disableCollection;
    private String visibleToCustomer;
    private String frequency;
    private Integer masked;
    private String customer;

    public PMConfigurationDTO() {}

    public PMConfigurationDTO(PMConfiguration pmConfiguration) {
        this.id = pmConfiguration.getId();
        this.serviceId = pmConfiguration.getServiceId();
        this.sourceName = pmConfiguration.getSourceName();
        this.nodeId = pmConfiguration.getNodeId();
        this.vendor = pmConfiguration.getVendor();
        this.labelName = pmConfiguration.getLabelName();
        this.routeDirection = pmConfiguration.getRouteDirection();
        this.segmentDirection = pmConfiguration.getSegmentDirection();
        this.disableCollection = pmConfiguration.getDisableCollection();
        this.visibleToCustomer = pmConfiguration.getVisibleToCustomer();
        this.frequency = pmConfiguration.getFrequency();
    }
}
