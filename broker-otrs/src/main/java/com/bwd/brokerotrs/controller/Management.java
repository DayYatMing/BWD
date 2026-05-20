package com.bwd.brokerotrs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class Management {

    private static final Logger logger = LoggerFactory.getLogger(Management.class);

    @GetMapping("/health")
    public String getStatus() {
        logger.info("Broker status is UP");
        return "UP";
    }

}
