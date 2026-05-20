package com.bwd.grafanaboot.persistence.DAO;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "configuration")
public class ServSources implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public String getCollectionDuration() {
        return collectionDuration;
    }

    public void setCollectionDuration(String collectionDuration) {
        this.collectionDuration = collectionDuration;
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

    public String getNodeDisplayName() {
        return nodeDisplayName;
    }

    public void setNodeDisplayName(String nodeDisplayName) {
        this.nodeDisplayName = nodeDisplayName;
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

    @Column(name = "serviceid")
    private String serviceId;

    @Column(name = "sourcename")
    private String sourceName;

    @Column(name = "nodeid")
    private String nodeId;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "label_name")
    private String labelName;

    @Column(name = "collection_duration")
    private String collectionDuration;

    @Column(name = "route_direction")
    private String routeDirection;

    @Column(name = "segment_direction")
    private String segmentDirection;

    @Column(name = "nodedisplayname")
    private String nodeDisplayName;

    @Column(name = "disable_collection")
    private String disableCollection;

    @Column(name = "visible_to_customer")
    private String visibleToCustomer;

    @Column(name = "frequency")
    private String frequency;

}
