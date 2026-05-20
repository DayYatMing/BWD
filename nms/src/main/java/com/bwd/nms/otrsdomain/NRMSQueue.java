package com.bwd.nms.otrsdomain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ticket")
public class NRMSQueue {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "queue"  ,  insertable = false , updatable = false)
    private String queue;

    @Column(name = "new"  ,  insertable = false , updatable = false)
    private String news;

    @Column(name = "open"  ,  insertable = false , updatable = false)
    private String open;

    @Column(name = "pending_reminder"  ,  insertable = false , updatable = false)
    private String pendingreminder;

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getPendingreminder() {
        return pendingreminder;
    }

    public void setPendingreminder(String pendingreminder) {
        this.pendingreminder = pendingreminder;
    }
}
