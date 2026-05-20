package com.bwd.nms.service.dto;

import com.bwd.nms.domain.NetworkState;

import java.io.Serializable;
import java.time.Instant;

public class NetworkStateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String updatedby;

    private Instant dateupdated;

    private String networkimage;

    public NetworkStateDTO(){}

    public NetworkStateDTO(NetworkState networkState){
        if(networkState != null) {
            this.id = networkState.getId();
            this.updatedby = networkState.getUpdatedby();
            this.dateupdated = networkState.getDateupdated();
            this.networkimage = networkState.getNetworkimage();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Instant getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Instant dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getNetworkimage() {
        return networkimage;
    }

    public void setNetworkimage(String networkimage) {
        this.networkimage = networkimage;
    }
}
