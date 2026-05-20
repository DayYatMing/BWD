package com.bwd.nms.domain;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table("timeline")
public class Timeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column("object")
    private String object;

    @Column("operation")
    private String operation;
//
//    @ManyToOne()
//    private User user;

    @Column("user_id")
    private Long user_id;

    @Column("old_value")
    private String oldValue;

    @Column("new_value")
    private String newValue;

    @Column("event_date")
    @CreatedDate
    @JsonIgnore
    private Instant eventDate = Instant.now();


    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

//    public User getUser() {
//        return user;
//    }
//
//
//    public void setUser(User user) {
//        this.user = user;
//    }


    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }



    @Override
    public String toString() {
        return "Timeline{" +
            "object='" + object + '\'' +
            ", operation='" + operation + '\'' +
            ", userId=" + user_id +
            ", oldValue='" + oldValue + '\'' +
            ", newValue='" + newValue + '\'' +
            ", eventDate=" + eventDate +
            '}';
    }
}
