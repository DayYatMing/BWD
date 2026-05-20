package com.bwd.nms.service;

import com.bwd.nms.domain.Ticket;
import com.bwd.nms.domain.TicketIdentity;
import com.bwd.nms.otrsdomain.CustomerUser;
import com.bwd.nms.repository.TicketRepository;
import com.bwd.nms.service.dto.TicketRequest;
import com.bwd.nms.service.dto.TicketStatusReponse;
import com.bwd.nms.service.dto.TicketResponse;
import com.bwd.nms.otrsrepository.*;
import com.bwd.nms.web.util.ExternalHttpUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TicketService {

    private final Logger log = LoggerFactory.getLogger(TicketService.class);

    private final String TICKET_QUEUE = "NOC Incidents";

    private final String TICKET_QUEUE_INTERNAL = "NOC-Internal";

    private final String TICKET_DEFAULT_FROM = "support@bw-digital.com";

    private final String TICKET_CONTENT = "text/html; charset=utf8";

    private final String TICKET_STATE = "open";

    private final String TICKET_PRIORITY = "3";

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerUserService customerUserService;

    private final ObjectMapper mapper = new ObjectMapper();

    public TicketService() {

    }

    public Flux<TicketStatusReponse> findAll() {
        return ticketRepository.findAllByEventDate()
            .filter(Objects::nonNull)
            .map(this::ticketToTicketStatusReponse);
    }

    TicketStatusReponse ticketToTicketStatusReponse(Ticket ticket) {
        TicketStatusReponse res = new TicketStatusReponse();
        res.setEventdate(ticket.getEventDate());
        res.setService(ticket.getService());
        res.setTicketid(ticket.getTicketId());
        res.setStatus(ticket.getStatus());
        res.setTicketnumber(ticket.getTicketNumber());
        res.setTitle(ticket.getTitle());
        res.setStatusmessage(ticket.getStatusMessage());
        return res;
    }

    public String createTicket(TicketRequest ticketRequest, String brokerURL, String brokerServer, String key) throws Exception {
        ticketRequest.getTicket().setState(TICKET_STATE);
        ticketRequest.getTicket().setPriorityID(TICKET_PRIORITY);
        String customerUser = "";

        if (!("::".equals(ticketRequest.getTicket().getService()) || ticketRequest.getTicket().getService().trim().isEmpty())) {
            if (ticketRequest.getTicket().getBulk().equals("SINGLE")) {
                List<CustomerUser> lstCustomerUser = customerUserService.getCustomerId(ticketRequest.getTicket().getCustomerID());

                for(CustomerUser cu : lstCustomerUser) {
                    System.out.println(cu.getLogin());
                    System.out.println(cu.getEmail());
                    System.out.println(cu.getCustomerid());
                }

                if (!lstCustomerUser.isEmpty()) {
                    lstCustomerUser.forEach(customerUser1 -> {
                        if (!customerUser1.getEmail().contains("@bw-digital")) {
                            ticketRequest.getTicket().setCustomerUser(customerUser1.getLogin());
                            if(ticketRequest.getArticle().getTo() != null)
                                ticketRequest.getArticle().setTo(customerUser1.getEmail()+";"+ticketRequest.getArticle().getTo());
                            else
                                ticketRequest.getArticle().setTo(customerUser1.getEmail());
                        }
                    });
                    if ("".equals(ticketRequest.getTicket().getCustomerUser()))
                        ticketRequest.getTicket().setCustomerUser(customerUser);
                    ticketRequest.getArticle().setArticleSend("1");
                    ticketRequest.getArticle().setFrom(TICKET_DEFAULT_FROM);

                }

                ticketRequest.getTicket().setQueue(TICKET_QUEUE);

            } else if (ticketRequest.getTicket().getBulk().equals("BULK")) {
                customerUser = ticketRequest.getTicket().getCustomerID();
                ticketRequest.getTicket().setQueue(TICKET_QUEUE_INTERNAL);
                ticketRequest.getTicket().setCustomerUser(customerUser);
            }

            ticketRequest.getArticle().setContentType(TICKET_CONTENT);
            return sendRequest(ticketRequest, brokerURL + "/otrs/Ticket", brokerServer, key);
        }

        return "Error creating ticket";
    }

    private String sendRequest(TicketRequest ticketRequest, String url, String server, String key) throws IOException {

        Pattern pattern = Pattern.compile("(?i)null");
        Matcher matcher = pattern.matcher(ticketRequest.getArticle().getBody());
        if (matcher.find()) {
            ticketRequest.getArticle().setBody(matcher.replaceAll(""));
        }

        Long requestTimeStamp = System.currentTimeMillis();
        String uniqueID = UUID.randomUUID().toString();
        log.info("Ticket Creation time: {}, ThreadName: {} , Request: {},",requestTimeStamp, uniqueID, mapper.writeValueAsString(ticketRequest));
        TicketResponse ticketResponse;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String response = ExternalHttpUtil.makePOSTRequest(url, key, mapper.writeValueAsString(ticketRequest), "application/json");

        log.info("Ticket Response time: {}, ThreadName: {} , Response: {},",requestTimeStamp, uniqueID, response);

        Ticket ticket = new Ticket();
        TicketIdentity ticketIdentity = new TicketIdentity();
        ticketIdentity.setService(ticketRequest.getTicket().getService());
        ticketIdentity.setEventdate(Instant.now().toString());
        ticket.setService(ticketIdentity.getService());
        ticket.setEventDate(ticketIdentity.getEventdate());
        ticket.setTitle(ticketRequest.getTicket().getTitle());

        if (response != null && (response.contains("Error") || response.contains("faultstring"))) {
            if (response.contains("Service parameter is invalid"))
                ticket.setStatusMessage("Failure: Please check service is mapped to correct user and validity status in ticketing system, please update in system as required");
            else
                ticket.setStatusMessage(response);
            ticket.setStatus("Failure");
            ticketResponse = new TicketResponse();
            ticketResponse.setTicketNumber("Error Creating Ticket");
            CompletableFuture.completedFuture(ticketResponse);
        } else {

            ticketResponse = mapper.readValue(response, TicketResponse.class);
            ticket.setStatus("Success");
            ticket.setTicketId(server + "/otrs/index.pl?Action=AgentTicketZoom;TicketID=" + ticketResponse.getTicketID());
            ticket.setTicketNumber(ticketResponse.getTicketNumber());

            response = "Success";
        }

        ticketRepository.save(ticket)
            .doOnSuccess(saved -> log.info("Ticket saved: {}", saved.getStatus()))
            .doOnError(err -> log.error("Failed to save ticket", err))
            .subscribe();

        return response;
    }
}
