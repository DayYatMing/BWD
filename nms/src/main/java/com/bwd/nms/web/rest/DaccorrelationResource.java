package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.DaccorrelationData;
import com.bwd.nms.service.DaccorrelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class DaccorrelationResource {

    private final Logger log = LoggerFactory.getLogger(DaccorrelationResource.class);

    @Autowired
    DaccorrelationService daccorrelationService;

    public DaccorrelationResource() {
    }

    @GetMapping("/correlation/daccorrelation")
    public Flux<DaccorrelationData> getAll() {
        log.debug("REST request to get DAC Details");

        return daccorrelationService.getAll();
    }

    @PostMapping("/correlation/daccorrelation")
    public Mono<Void> createData(@RequestBody DaccorrelationData daccorrelationData) {
        return daccorrelationService.createData(daccorrelationData);
    }

    @PutMapping("/correlation/daccorrelation")
    public Mono<Void> updateData(@RequestBody DaccorrelationData daccorrelationData) {
        return daccorrelationService.updateData(daccorrelationData);
    }
}
