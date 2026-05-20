package com.bwd.cms.web.rest;

import com.bwd.cms.domain.Stats;
import com.bwd.cms.service.StatsService;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StatsResource {

    private final Logger log = LoggerFactory.getLogger(StatsResource.class);

    @Autowired
    Environment env;

    @Autowired
    StatsService statsService;

    @GetMapping("/stats")
    public ResponseEntity<List<Stats>> getStats(@RequestHeader HttpHeaders headers) throws IOException {
        log.info("REST request to get a list of stats.");
        String brokerURL = env.getProperty("gateway.kong.url");
        String brokerAPI = env.getProperty("gateway.kong.apikey");
        List<Stats> stats = statsService.getStats(brokerURL, brokerAPI, headers);

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @GetMapping("/stats/services")
    public ResponseEntity<List<String>> getServices(@RequestHeader HttpHeaders headers) throws IOException {
        log.info("REST request to get a list of services.");
        String brokerURL = env.getProperty("gateway.kong.url");
        String brokerAPI = env.getProperty("gateway.kong.apikey");
        headers.add("customer", headers.get("customer").get(0));
        List<String> services = statsService.getServices(brokerURL, brokerAPI, headers);

        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/stats/sources")
    public ResponseEntity<List<String>> getSources(@RequestHeader HttpHeaders headers) throws IOException {
        log.info("REST request to get a list of sources.");
        String brokerURL = env.getProperty("gateway.kong.url");
        String brokerAPI = env.getProperty("gateway.kong.apikey");
        headers.add("serviceId", headers.get("serviceId").get(0));
        List<String> sources = statsService.getSources(brokerURL, brokerAPI, headers);

        return new ResponseEntity<>(sources, HttpStatus.OK);
    }
}
