package com.bwd.cms.service.dto;

import java.io.Serial;
import org.springframework.data.relational.core.mapping.Column;

public class OrderDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public byte[] getCircuit() {
        return circuit;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public int getDuration() {
        return duration;
    }

    private Long id;
    private String name;
    private String status;
    private byte[] circuit;

    @Column("entityId")
    private String entityId;

    @Column("entityName")
    private String entityName;

    private int duration;

    public OrderDTO(Long id, String name, String status, byte[] circuit, String entityId, String entityName, int duration) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.circuit = circuit;
        this.entityId = entityId;
        this.entityName = entityName;
        this.duration = duration;
    }
}
