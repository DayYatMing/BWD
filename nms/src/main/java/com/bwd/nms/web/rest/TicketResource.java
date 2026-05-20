package com.bwd.nms.web.rest;

import com.bwd.nms.service.TicketService;
import com.bwd.nms.service.dto.TicketRequest;
import com.bwd.nms.service.dto.TicketStatusReponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);

    @Autowired
    TicketService ticketService;

    @Autowired
    Environment env;

    @GetMapping("/ticket")
    public Flux<TicketStatusReponse> getAllTickets() {
        log.debug("REST request to get all tickets");
        return ticketService.findAll();
    }

    @PostMapping("/ticket/create")
    public ResponseEntity<Map<String, String>> createTicket(@Valid @RequestBody TicketRequest ticketRequest) throws Exception {
        log.debug("REST request to create a new Ticket");
        String brokerURL = env.getProperty("gateway.kong.url");
        String brokerServer = env.getProperty("ticketing.server");
        String apikey = env.getProperty("gateway.kong.apikey");

        String result = ticketService.createTicket(ticketRequest, brokerURL, brokerServer, apikey);

        if (result.contains("Error")) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("status", "Failed: error creating ticket"));
        }

        return ResponseEntity.ok(Map.of("status", "Success"));
    }

}
