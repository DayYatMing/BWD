package com.bwd.grafanaboot.controller;

import com.bwd.grafanaboot.component.model.TimeSeries;
import com.bwd.grafanaboot.service.web.CustomerServices;
import com.bwd.grafanaboot.service.web.ServiceSources;
import com.bwd.grafanaboot.service.web.TemsOCHService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GrafanaBootController {

    private static final Logger logger = LoggerFactory.getLogger(GrafanaBootController.class);

    @Autowired
    TemsOCHService temsOCHService;

    @Autowired
    CustomerServices customerServices;

    @Autowired
    ServiceSources serviceSources;

    @GetMapping("/tems")
    public ResponseEntity<List<TimeSeries>> getTems(@RequestHeader HttpHeaders headers) {
        long dateTimeFrom = Long.parseLong(headers.get("dtFr").get(0));
        long dateTimeTo = Long.parseLong(headers.get("dtTo").get(0));
        String serviceId = headers.get("serviceId").get(0);
        String pmSource = headers.get("pmSource").get(0);

        LocalDateTime from = Instant.ofEpochMilli(dateTimeFrom).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime to   = Instant.ofEpochMilli(dateTimeTo).atZone(ZoneId.systemDefault()).toLocalDateTime();

        logger.info("[>>> Starting Tems OCH collection: {} | from {} to {}", serviceId, from, to);

        List<TimeSeries> results = null;
        try {
            results = temsOCHService.getTemsOCH(serviceId, pmSource, from, to);
        }catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }

        logger.info("Completed request <<<]");

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/services")
    public List<String> getServices(@RequestHeader HttpHeaders headers) {

        String customer = headers.get("customer").get(0);
        logger.info("[>>> Customer {}", customer);

        return customerServices.getServices(customer);
    }

    @GetMapping("/sources")
    public List<String> getSources(@RequestHeader HttpHeaders headers) {

        String service = headers.get("serviceId").get(0);
        logger.info("[>>> Service {}", service);

        return serviceSources.getSources(service);
    }
}
