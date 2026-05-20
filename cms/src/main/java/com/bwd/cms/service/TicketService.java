package com.bwd.cms.service;

import com.bwd.cms.domain.TicketIDs;
import com.bwd.cms.domain.TicketManagement;
import com.bwd.cms.web.util.OtrsHttpUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<TicketManagement> getTickets(String brokerURL, String key, String cusID, String stateType) throws IOException {
        if (stateType.equalsIgnoreCase("all")) stateType = "";

        String updatedUrl = brokerURL + "/otrs/Ticket?" + "CustomerID=" + cusID + "&StateType=" + stateType;

        String jsonResponse = OtrsHttpUtil.makeGETRequest(updatedUrl, key, null);
        if (jsonResponse == null || jsonResponse.isBlank()) {
            return new ArrayList<>();
        }

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        TicketIDs tickets = mapper.readValue(jsonResponse, TicketIDs.class);

        List<TicketManagement> ticketManagements = new ArrayList<>();
        if (tickets.getTicketManagement() != null) {
            for (TicketManagement ticketManagement : tickets.getTicketManagement()) {
                if (!ticketManagement.getTitle().toLowerCase().contains("internal :")) {
                    ticketManagements.add(ticketManagement);
                }
            }
        }

        return ticketManagements;
    }

    public List<TicketManagement> getRatio(String brokerURL, String key, String cusID) throws IOException {
        String updatedUrl = brokerURL + "/otrs/Ticket?" + "CustomerID=" + cusID + "&StateType=";

        String jsonResponse = OtrsHttpUtil.makeGETRequest(updatedUrl, key, null);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        TicketIDs tickets = mapper.readValue(jsonResponse, TicketIDs.class);
        int closed = 0;
        int open = 0;

        List<TicketManagement> ticketManagements = new ArrayList<>();
        if (tickets.getTicketManagement() != null) {
            for (TicketManagement ticketManagement : tickets.getTicketManagement()) {
                if (!ticketManagement.getTitle().toLowerCase().contains("internal :")) {
                    ticketManagements.add(ticketManagement);
                    if (ticketManagement.getState().toLowerCase().contains("close")) closed += 1;
                    else open += 1;
                }
            }
            ticketManagements.get(0).setOpen(open);
            ticketManagements.get(0).setClosed(closed);
        }

        return ticketManagements;
    }

    public TicketManagement getTicket(String brokerURL, String key, String id) throws IOException {
        String updatedUrl = brokerURL + "/otrs/Ticket/" + id;
        TicketManagement ticketManagement;
        String jsonResponse = OtrsHttpUtil.makeGETRequest(updatedUrl, key, null);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        ticketManagement = mapper.readValue(jsonResponse, TicketManagement.class);

        return ticketManagement;
    }

    public TicketManagement updateTicket(TicketManagement ticketManagement, String brokerURL, String key) throws IOException {
        String updatedUrl = brokerURL + "/otrs/Ticket/" + ticketManagement.getId();
        ticketManagement.setState("open");
        ticketManagement.setService(null);
        ticketManagement.setCustomerID(ticketManagement.getLogin());
        ticketManagement.setFrom(ticketManagement.getCustomerUser());
        String response = OtrsHttpUtil.makePOSTRequest(updatedUrl, key, mapper.writeValueAsString(ticketManagement), "application/json");
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        ticketManagement = mapper.readValue(response, TicketManagement.class);

        return ticketManagement;
    }

    public TicketManagement createTicket(TicketManagement ticketManagement, String brokerURL, String key) throws IOException {
        String updatedUrl = brokerURL + "/otrs/Ticket";
        System.out.println(updatedUrl);
        ticketManagement.setState("new");
        ticketManagement.setService(null);
        ticketManagement.setCustomerID(ticketManagement.getLogin());
        ticketManagement.setFrom(ticketManagement.getCustomerUser());
        ticketManagement.setQueue("NOC-Mailbox");
        ticketManagement.setTitle(ticketManagement.getSubject());
        String response = OtrsHttpUtil.makePOSTRequest(updatedUrl, key, mapper.writeValueAsString(ticketManagement), "application/json");
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        ticketManagement = mapper.readValue(response, TicketManagement.class);

        return ticketManagement;
    }
}
