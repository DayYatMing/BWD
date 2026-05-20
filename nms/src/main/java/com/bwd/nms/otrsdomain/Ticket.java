package com.bwd.nms.otrsdomain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(value = { "UnlockTimeout","CustomerUserID", "ServiceID", "TimeUnit", "RealTillTimeNotUsed", "Responsible",
    "TypeID", "OwnerID", "EscalationTime", "SLAID", "ArchiveFlag", "EscalationSolutionTime","EscalationUpdateTime",
    "LockID", "ResponsibleID", "QueueID" ,"GroupID" , "StateID" , "StateType" , "CreateBy" , "EscalationResponseTime" , "UntilTime" , "Lock",
    "Changed" ,"ChangeBy" })
public class Ticket implements Serializable {


	private static final long serialVersionUID = 1L;

	@JsonProperty("Title")
    private String title;

    @JsonProperty("Queue")
    private String queue;

    @JsonProperty("State")
    private String state;

    @JsonProperty("PriorityID")
    private String priorityID;

    @JsonProperty("CustomerUser")
    private String customerUser;

    @JsonProperty("CustomerID")
    private String customerID;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Service")
    private String service;


    @JsonProperty("Bulk")
    private String bulk;


	public String getBulk() {
		return bulk;
	}

	public void setBulk(String bulk) {
		this.bulk = bulk;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }


}
