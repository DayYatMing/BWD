package com.bwd.nms.service.dto;

import com.bwd.nms.mediationdomain.PMSource;
import java.io.Serializable;

public class PMSourceDTO implements Serializable {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerSid() {
        return customerSid;
    }

    public void setCustomerSid(String customerSid) {
        this.customerSid = customerSid;
    }

    public String getPmSource() {
        return pmSource;
    }

    public void setPmSource(String pmSource) {
        this.pmSource = pmSource;
    }

    public String getVisibleToCustomer() {
        return visibleToCustomer;
    }

    public void setVisibleToCustomer(String visibleToCustomer) {
        this.visibleToCustomer = visibleToCustomer;
    }

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

    public String getSegmentEnd() {
        return segmentEnd;
    }

    public void setSegmentEnd(String segmentEnd) {
        this.segmentEnd = segmentEnd;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    private Long id;
    private String customer;
    private String customerSid;
    private String pmSource;
    private String visibleToCustomer;
    private String routeEnd;
    private String segmentEnd;
    private String source;
    private String nodeId;
    private String route;
    private String segment;

    public PMSourceDTO(PMSource pmSource) {
        this.id = pmSource.getId();
        this.customer = pmSource.getCustomer();
        this.customerSid = pmSource.getCustomerSid();
        this.pmSource = pmSource.getPmSource();
        this.visibleToCustomer = pmSource.getVisibleToCustomer();
        this.routeEnd = pmSource.getRouteEnd();
        this.segmentEnd = pmSource.getSegmentEnd();
        this.source = pmSource.getSource();
        this.nodeId = pmSource.getNodeId();
        this.route = pmSource.getRoute();
        this.segment = pmSource.getSegment();
    }

    public PMSourceDTO() {}
}
