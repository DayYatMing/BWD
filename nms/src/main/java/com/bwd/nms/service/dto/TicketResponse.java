package com.bwd.nms.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TicketResponse implements Serializable {

	private static final long serialVersionUID = 1L;


    @JsonProperty("ArticleID")
    private String articleID;

    @JsonProperty("TicketID")
    private String ticketID;


    @JsonProperty("TicketNumber")
    private String ticketNumber;


	public String getArticleID() {
		return articleID;
	}


	public void setArticleID(String articleID) {
		this.articleID = articleID;
	}


	public String getTicketID() {
		return ticketID;
	}


	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}


	public String getTicketNumber() {
		return ticketNumber;
	}


	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}


}
