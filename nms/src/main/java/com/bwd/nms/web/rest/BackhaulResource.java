package com.bwd.nms.web.rest;

import com.bwd.nms.domain.Feature;
import com.bwd.nms.orientdbdomain.Backhaul;
import com.bwd.nms.orientdbdomain.Segment;
import com.bwd.nms.service.BackhaulService;
import com.bwd.nms.service.NotificationService;
import com.bwd.nms.service.SegmentService;
import com.bwd.nms.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing Backhaul.
 */
@RestController
@RequestMapping("/api")
public class BackhaulResource {

    private final Logger log = LoggerFactory.getLogger(BackhaulResource.class);

    private BackhaulService backhaulService;

    private NotificationService notificationService;

    public BackhaulResource(BackhaulService backhaulService,  NotificationService notificationService) {
        this.backhaulService = backhaulService;
        this.notificationService = notificationService;
    }

    @GetMapping("/backhauls")
    public Flux<Backhaul> getAllBackhauls() {
        log.debug("REST request to get Backhauls");
        return backhaulService.getBackhauls();
    }


    @PutMapping("/backhauls")
    public Mono<Void> updateBackhaul(@Valid @RequestBody Backhaul backhaul) {
        log.debug("REST request to update Backhaul : {}", backhaul);

        return backhaulService.findById(backhaul.getName())
                .flatMap(oldBackhaul -> backhaulService.update(backhaul)
                    .then(notificationService.createNotificationFromFeature(oldBackhaul, backhaul, "UPDATE", Feature.BACKHAUL)
                        .flatMap(notificationService::enabledNotification)));
    }

    @PostMapping("/backhauls")
    public Mono<Void> createBackhaul(@Valid @RequestBody Backhaul backhaul) {
        log.debug("REST request to save backhaul : {}", backhaul);
        if (backhaul.getId() != null) {
            throw new BadRequestAlertException("A new backhaul cannot already have an ID", "BACKHAUL", "id exists");
        }
        return backhaulService.save(backhaul)
            .then(Mono.defer(() ->
                notificationService
                    .createNotificationFromFeature(null, backhaul, "INSERT", Feature.BACKHAUL)
                    .flatMap(notificationService::enabledNotification)
            ));
    }
}
