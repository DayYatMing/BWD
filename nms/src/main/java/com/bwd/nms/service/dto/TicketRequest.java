
package com.bwd.nms.service.dto;

import com.bwd.nms.otrsdomain.Ticket;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TicketRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("Ticket")
    Ticket ticket;

	@JsonProperty("Article")
	Article article;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}
