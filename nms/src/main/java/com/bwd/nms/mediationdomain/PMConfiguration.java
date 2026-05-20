package com.bwd.nms.mediationdomain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("configuration")
public class PMConfiguration implements Serializable {

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

    @Id
    private Long id;

    @Column("serviceid")
    private String serviceId;

    @Column("sourcename")
    private String sourceName;

    @Column("nodeid")
    private String nodeId;

    @Column("vendor")
    private String vendor;

    @Column("label_name")
    private String labelName;

    @Column("route_direction")
    private String routeDirection;

    @Column("segment_direction")
    private String segmentDirection;

    @Column("disable_collection")
    private String disableCollection;

    @Column("visible_to_customer")
    private String visibleToCustomer;

    @Column("frequency")
    private String frequency;
}
