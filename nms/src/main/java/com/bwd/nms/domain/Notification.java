package com.bwd.nms.domain;

import java.io.Serializable;
import java.time.Instant;

public class Notification implements Serializable {

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    private static final long serialVersionUID = 1L;

    private String feature;

    private String operation;

    private String login;

    private String oldvalue;

    private String newvalue;

    private Instant eventdate;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    public Instant getEventdate() {
        return eventdate;
    }

    public void setEventdate(Instant eventdate) {
        this.eventdate = eventdate;
    }

    public Notification(Timeline timeline, String login) {
        this.feature   = timeline.getObject();
        this.operation = timeline.getOperation();
        this.login = login;
        this.oldvalue  = timeline.getOldValue();
        this.newvalue  = timeline.getNewValue();
        this.eventdate = timeline.getEventDate();
    }

    public Notification() {}

}
