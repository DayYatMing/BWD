package com.bwd.nms.mediationdomain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("service_source")
public class PMSource implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Id
    private Long id;

    @Column("customer")
    private String customer;

    @Column("customer_sid")
    private String customerSid;

    @Column("pm_source")
    private String pmSource;

    @Column("visible_to_customer")
    private String visibleToCustomer;

    @Column("route_end")
    private String routeEnd;

    @Column("segment_end")
    private String segmentEnd;

    @Column("source")
    private String source;

    @Column("node_id")
    private String nodeId;

    @Column("route")
    private String route;

    @Column("segment")
    private String segment;
}
