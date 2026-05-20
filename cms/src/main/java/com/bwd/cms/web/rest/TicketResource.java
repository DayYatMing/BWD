package com.bwd.cms.web.rest;

import com.bwd.cms.domain.TicketManagement;
import com.bwd.cms.service.CustomerService;
import com.bwd.cms.service.TicketService;
import com.bwd.cms.web.util.OtrsHttpUtil;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);

    @Autowired
    Environment env;

    @Autowired
    TicketService ticketService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/ticket/{cusID}&{stateType}")
    public ResponseEntity<List<TicketManagement>> getAllTicketManagements(@PathVariable String cusID, @PathVariable String stateType)
        throws IOException {
        log.info("REST request to get a list of TicketManagements: {} {}", cusID, stateType);
        String brokerURL = env.getProperty("gateway.kong.url");
        String apikey = env.getProperty("gateway.kong.apikey");
        List<TicketManagement> ticketManagements = ticketService.getTickets(brokerURL, apikey, cusID, stateType);

        return new ResponseEntity<>(ticketManagements, HttpStatus.OK);
    }

    @GetMapping("/ticket/ratio/{cusID}")
    public ResponseEntity<TicketManagement> getRatio(@PathVariable String cusID) throws IOException {
        log.info("REST request to get ratio of TicketManagements: {}", cusID);
        String brokerURL = env.getProperty("gateway.kong.url");
        String apikey = env.getProperty("gateway.kong.apikey");
        List<TicketManagement> ticketManagements = ticketService.getRatio(brokerURL, apikey, cusID);

        if (!ticketManagements.isEmpty()) return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ticketManagements.get(0)));
        else return ResponseUtil.wrapOrNotFound(Optional.of(new TicketManagement()));
    }

    @GetMapping("/ticket/cus-acronym/{login}")
    public Mono<ResponseEntity<String>> getCusAcronym(@PathVariable String login) throws IOException {
        log.info("REST request to get cus-acronym: {}", login);

        return customerService.getCusID(login).map(cusID -> ResponseEntity.ok().header("status", "OK").body(cusID));
    }

    @GetMapping("/ticket/id/{id}")
    public ResponseEntity<TicketManagement> getTicket(@PathVariable String id) throws IOException {
        log.info("REST request to get a ticket id: {} ", id);
        String brokerURL = env.getProperty("gateway.kong.url");
        String apikey = env.getProperty("gateway.kong.apikey");
        TicketManagement ticketManagement = ticketService.getTicket(brokerURL, apikey, id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ticketManagement));
    }

    @PutMapping("/ticket")
    public ResponseEntity<TicketManagement> updateTicket(@RequestBody TicketManagement ticketManagement) throws IOException {
        log.info("REST request to update ticket management : {}", ticketManagement);
        TicketManagement result;
        String brokerURL = env.getProperty("gateway.kong.url");
        String apikey = env.getProperty("gateway.kong.apikey");
        result = ticketService.updateTicket(ticketManagement, brokerURL, apikey);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping("/ticket")
    public ResponseEntity<TicketManagement> createTicket(@RequestBody TicketManagement ticketManagement) throws IOException {
        log.info("REST request to create ticket management : {}", ticketManagement);
        TicketManagement result;
        String brokerURL = env.getProperty("gateway.kong.url");
        String apikey = env.getProperty("gateway.kong.apikey");
        result = ticketService.createTicket(ticketManagement, brokerURL, apikey);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}
