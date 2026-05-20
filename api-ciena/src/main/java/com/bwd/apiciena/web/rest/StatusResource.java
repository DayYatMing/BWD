package com.bwd.apiciena.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bwd")
public class StatusResource {

    private final Logger log = LoggerFactory.getLogger(StatusResource.class);

    @GetMapping("/status")
    public Mono<String> getStatus() {
        log.debug("REST request to get Status.");
        return Mono.just("UP!");
    }
}
