package com.bwd.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(
    value = {
        "UnlockTimeout",
        "CustomerUserID",
        "ServiceID",
        "TimeUnit",
        "RealTillTimeNotUsed",
        "Responsible",
        "TypeID",
        "OwnerID",
        "EscalationTime",
        "SLAID",
        "ArchiveFlag",
        "EscalationSolutionTime",
        "EscalationUpdateTime",
        "LockID",
        "ResponsibleID",
        "QueueID",
        "GroupID",
        "StateID",
        "StateType",
        "CreateBy",
        "EscalationResponseTime",
        "UntilTime",
        "Lock",
        "Changed",
        "ChangeBy",
    }
)
@JsonInclude(Include.NON_NULL)
public class TicketManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonProperty("TicketNumber")
    private String ticketNumber;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Owner")
    private String owner;

    @JsonProperty("Queue")
    private String queue;

    @JsonProperty("PriorityID")
    private String priorityID;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("State")
    private String state;

    @JsonProperty("CustomerUser")
    private String customerUser;

    @JsonProperty("Created")
    private String created;

    @JsonProperty("Changed")
    private String changed;

    @JsonProperty("Age")
    private int age;

    @JsonProperty("TicketID")
    private long ticketID;

    @JsonProperty("Subject")
    String subject;

    @JsonProperty("Body")
    String body;

    @JsonProperty("ContentType")
    String contentType;

    @JsonProperty("Priority")
    String priority;

    @JsonProperty("CustomerID")
    String customerID;

    @JsonProperty("Service")
    String service;

    @JsonProperty("open")
    int open = 0;

    @JsonProperty("closed")
    int closed = 0;

    @JsonProperty("Login")
    String login;

    @JsonProperty("From")
    String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getConvertedAge() {
        if (getAge() == 0) return "0";
        String convertedAge = getAge() / 24 / 60 / 60 + "d " + ((getAge() / 60 / 60) % 24) + "h " + ((getAge() / 60) % 60) + " m";
        return convertedAge;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("Article")
    Article[] articleArr;

    public Article[] getArticleArr() {
        return articleArr;
    }

    public void setArticleArr(Article[] articleArr) {
        this.articleArr = articleArr;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentType() {
        if (contentType == null) contentType = "text/plain; charset=utf8";
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPriorityID() {
        return priorityID;
    }

    public void setPriorityID(String priorityID) {
        this.priorityID = priorityID;
    }

    public String getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(String customerUser) {
        this.customerUser = customerUser;
    }

    public long getTicketID() {
        return ticketID;
    }

    public void setTicketID(long ticketID) {
        this.ticketID = ticketID;
        this.setId(ticketID);
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        getConvertedAge();
    }

    public TicketManagement() {}

    public TicketManagement(long id) {
        setId(id);
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        if (id == null) id = getTicketID();
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketManagement ticketManagement = (TicketManagement) o;
        if (ticketManagement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticketManagement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TicketManagement{" + "id=" + getId() + "}";
    }
}
