package com.bwd.apiciena.web.rest;

import com.bwd.apiciena.service.ExternalService;
import com.bwd.apiciena.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bwd")
public class PerformanceResource {

    private final Logger log = LoggerFactory.getLogger(PerformanceResource.class);

    @Autowired
    PerformanceService performanceService;

    @GetMapping("/pm")
    public ResponseEntity<String> getPM() {
        log.debug("REST request to get PM.");
        return ResponseEntity.ok().body(performanceService.getPM());
    }
}
