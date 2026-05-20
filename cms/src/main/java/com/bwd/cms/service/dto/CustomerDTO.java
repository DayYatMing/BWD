package com.bwd.cms.service.dto;

import java.io.Serial;

public class CustomerDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    private Long id;
    private String user;
    private String contact;
    private String address;
    private String entityId;
    private String name;
    private String shortName;

    public CustomerDTO(Long id, String user, String contact, String address, String entityId, String name, String shortName) {
        this.id = id;
        this.user = user;
        this.contact = contact;
        this.address = address;
        this.entityId = entityId;
        this.name = name;
        this.shortName = shortName;
    }
}
