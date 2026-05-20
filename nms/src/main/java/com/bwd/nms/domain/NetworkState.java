package com.bwd.nms.domain;

import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

@Table("networkstate")
public class NetworkState implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "updatedby")
    private String updatedby;

    @Column(name= "networkimage")
    private String networkimage;

    @Column(name = "dateupdated")
    Instant dateupdated;

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

    public String getNetworkimage() {
        return networkimage;
    }

    public void setNetworkimage(String networkimage) {
        this.networkimage = networkimage;
    }

    public Instant getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Instant dateupdated) {
        this.dateupdated = dateupdated;
    }
}
