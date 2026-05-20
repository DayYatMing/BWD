/**
 *
 */
package com.bwd.nms.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author ICS-AKSTAN
 *
 */
@Embeddable
public class TicketIdentity implements Serializable{

	private static final long serialVersionUID = 1L;

	 @Column(name= "service" , length = 50)
	 private String service;

	@CreatedDate
	 @Column(name = "event_date", nullable = false)
	 private String eventdate = Instant.now().toString();

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getEventdate() {
		return eventdate;
	}

	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}

	public TicketIdentity() {}

}
