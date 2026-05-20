package com.bwd.nms.otrsdomain;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class NRMSTicket {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "create_time"  ,  insertable = false , updatable = false)
    private String createtime;

    @Column(name = "update_time"  ,  insertable = false , updatable = false)
    private String updatetime;

    @Column(name = "days"  ,  insertable = false , updatable = false)
    private String days;

    @Column(name = "hours"  ,  insertable = false , updatable = false)
    private String hours;

    @Column(name = "minutes"  ,  insertable = false , updatable = false)
    private String minutes;

    @Column(name = "tn"  ,  insertable = false , updatable = false)
    private String tn;

    @Column(name = "title"  ,  insertable = false , updatable = false)
    private String title;

    @Column(name = "customeruser"  ,  insertable = false , updatable = false)
    private String customeruser;

    @Column(name = "queue"  ,  insertable = false , updatable = false)
    private String queue;

    @Column(name = "tickettype"  ,  insertable = false , updatable = false)
    private String tickettype;

    @Column(name = "user"  ,  insertable = false , updatable = false)
    private String user;

    @Column(name = "priority"  ,  insertable = false , updatable = false)
    private String priority;

    @Column(name = "state"  ,  insertable = false , updatable = false)
    private String state;

    @Column(name = "ticketlock"  ,  insertable = false , updatable = false)
    private String ticketlock;

    @Column(name = "serviceid"  ,  insertable = false , updatable = false)
    private String serviceid;

    @Column(name = "customer"  ,  insertable = false , updatable = false)
    private String customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomeruser() {
        return customeruser;
    }

    public void setCustomeruser(String customeruser) {
        this.customeruser = customeruser;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getTickettype() {
        return tickettype;
    }

    public void setTickettype(String tickettype) {
        this.tickettype = tickettype;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTicketlock() {
        return ticketlock;
    }

    public void setTicketlock(String ticketlock) {
        this.ticketlock = ticketlock;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
