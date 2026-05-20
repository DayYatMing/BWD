package com.bwd.nms.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("ticket")
public class Ticket {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    private Long id;

    private String ticketid;
    private String service;
    private String event_date;
    private String ticketnumber;
    private String status;
    private String statusmessage;
    private String title;

    public String getTicketId() { return ticketid; }
    public void setTicketId(String ticketid) { this.ticketid = ticketid; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String  getEventDate() { return event_date; }
    public void setEventDate(String event_date) { this.event_date = event_date; }

    public String getTicketNumber() { return ticketnumber; }
    public void setTicketNumber(String ticketnumber) { this.ticketnumber = ticketnumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStatusMessage() { return statusmessage; }
    public void setStatusMessage(String statusmessage) { this.statusmessage = statusmessage; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Ticket() {}

}
