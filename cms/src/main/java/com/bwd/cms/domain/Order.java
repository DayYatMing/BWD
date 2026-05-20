package com.bwd.cms.domain;

import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public byte[] getCircuit() {
        return circuit;
    }

    public void setCircuit(byte[] circuit) {
        this.circuit = circuit;
    }

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("status")
    private String status;

    @Column("circuit")
    private byte[] circuit;

    @Column("entity_id")
    private String entityId;

    @Column("duration")
    private int duration;
}
