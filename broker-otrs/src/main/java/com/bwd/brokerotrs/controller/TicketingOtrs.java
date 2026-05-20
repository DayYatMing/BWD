package com.bwd.brokerotrs.controller;

import com.bwd.brokerotrs.controller.util.ExternalHttpUtil;
import com.bwd.brokerotrs.service.CredentialSetup;
import com.bwd.brokerotrs.service.OtrsService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/otrs")
public class TicketingOtrs {

    private static final Logger logger = LoggerFactory.getLogger(TicketingOtrs.class);

    Environment env;
    public TicketingOtrs(Environment env) {
        this.env = env;
    }

    @PatchMapping("/Ticket/{id}")
    public String updateTicket(@PathVariable("id") String id, @RequestBody String body){
        logger.info("Broker to update ticket");

        CredentialSetup credentialSetup = new CredentialSetup();
        OtrsService otrsService = new OtrsService();
        String url = otrsService.getDomainURN(env) +
                     "/" + id +
                     credentialSetup.getCredentials(env);

        String response = ExternalHttpUtil.makePATCHRequest(url, body, "application/json");
        logger.info("Updated ticket response: {}", response);

        return response;
    }

    @PostMapping("/Ticket")
    public String createTicket(HttpServletRequest request) throws IOException { 
        String body = request.getReader()
                .lines()
                .collect(Collectors.joining("\n"));

        logger.info("Broker to create ticket");

        CredentialSetup credentialSetup = new CredentialSetup();
        OtrsService otrsService = new OtrsService();

        String url = otrsService.getDomainURN(env) +
                credentialSetup.getCredentials(env);

        String response = ExternalHttpUtil.makePOSTRequest(url, body, "application/json");

        logger.info("Created ticket response: {}", response);

        return response;
    }

    @GetMapping("/Ticket/{id}")
    public String getTicketId(
            @PathVariable("id") String id){
        logger.info("Broker to get ticket id {}", id);

        CredentialSetup credentialSetup = new CredentialSetup();
        OtrsService otrsService = new OtrsService();
        String url = otrsService.getDomainURN(env) +
                     "/" + id +
                     credentialSetup.getCredentials(env) +
                     otrsService.getTicketArticle();

        return otrsService.extractTicket(ExternalHttpUtil.makeGETRequest(url, null));
    }

    @GetMapping("/Ticket")
    public String getCustomerTickets(
            @RequestParam("CustomerID") String cusID,
            @RequestParam("StateType") String stateType) {
        logger.info("Broker to get customer {} tickets type {}", cusID, stateType);

        CredentialSetup credentialSetup = new CredentialSetup();
        OtrsService otrsService = new OtrsService();
        String url = otrsService.getDomainURN(env) +
                     credentialSetup.getCredentials(env) +
                     otrsService.getUrlParams(cusID, stateType);

        String response = ExternalHttpUtil.makeGETRequest(url, null);
        if (response == null || response.equals("{}")) {
            return null;
        }

        String detailsUrl = otrsService.getDomainURN(env) +
                            otrsService.getDetails(response) +
                            credentialSetup.getCredentials(env) +
                            otrsService.getUrlParams(cusID, stateType);

        String detailsResponse = ExternalHttpUtil.makeGETRequest(detailsUrl, null);
        if (detailsResponse == null || detailsResponse.equals("{}")) {
            return null;
        }

        return detailsResponse;
    }

    @PostMapping("/Ticket/create")
    public ResponseEntity<String> createTicket(
            @RequestParam String customer,
            @RequestParam String lastRaiseTime,
            @RequestParam String serviceId,
            @RequestParam String type,
            @RequestParam(required = false) String clearedTime) {

        CredentialSetup credentialSetup = new CredentialSetup();
        OtrsService otrsService = new OtrsService();

        String url = otrsService.getDomainURN(env) +
                     credentialSetup.getCredentials(env);

        try {
            if (customer == null || lastRaiseTime == null || serviceId == null || type == null) {
                return ResponseEntity.badRequest().body("Missing required parameters");
            }

            boolean isCleared = (clearedTime != null);

            Map<String, Object> payload =
                    otrsService.createDynamicTicketPayload(customer, lastRaiseTime, serviceId, type, clearedTime, isCleared);

            String response = ExternalHttpUtil.makePOSTRequest(url, otrsService.toJson(payload), "application/json");

            if (response == null) {
                return ResponseEntity.status(500).body("No response from OTRS");
            }

            return ResponseEntity.ok(
                    "Ticket created successfully: " + type.toUpperCase()
                            + (isCleared ? " (Cleared)" : " (Raised)")
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
