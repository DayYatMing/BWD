package com.bwd.nms.web.rest;

import com.bwd.nms.mediationdomain.EstimatedFpCapacity;
import com.bwd.nms.service.ManageEstimatedCapacity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CapacityReportResource {

    private final Logger log = LoggerFactory.getLogger(CapacityReportResource.class);

    private final ManageEstimatedCapacity estimatedFpCapacityService;

    public CapacityReportResource(ManageEstimatedCapacity estimatedFpCapacityService) {
        this.estimatedFpCapacityService = estimatedFpCapacityService;
    }

    @GetMapping("/capacityreport/estimatedFpCapacity")
    public Flux<EstimatedFpCapacity> getEstimatedFpCapacity() {
        log.debug("REST request to get all estimated capacity");

        return estimatedFpCapacityService.getEstimatedFpCapacity()
            .doOnNext(capacity ->
                log.info("Controller received EstimatedFpCapacity: {}", capacity)
            );
    }

    @PutMapping("/capacityreport/updateEstimatedCapacity")
    public Mono<ResponseEntity<EstimatedFpCapacity>> updateEstimatedFpData(
        @RequestBody EstimatedFpCapacity estimatedFpCapacity) {

        return estimatedFpCapacityService.updateEstimatedFpCapacity(estimatedFpCapacity)
            .map(ResponseEntity::ok)
            .switchIfEmpty(
                Mono.just(ResponseEntity.ok(estimatedFpCapacity)) // fallback success
            );
    }
}
