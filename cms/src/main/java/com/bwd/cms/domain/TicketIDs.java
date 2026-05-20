/**
 *
 */
package com.bwd.cms.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akshat.tandon
 *
 */
public class TicketIDs {

    TicketManagement[] ticketManagement;

    @JsonProperty("Ticket")
    public TicketManagement[] getTicketManagement() {
        return ticketManagement;
    }

    public void setTicketManagement(TicketManagement[] ticketManagement) {
        this.ticketManagement = ticketManagement;
    }
}
