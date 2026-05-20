package com.bwd.nms.mediationdomain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "service")
public class MCPServiceIdSync implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "serviceid")
    private String serviceId;

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

}
