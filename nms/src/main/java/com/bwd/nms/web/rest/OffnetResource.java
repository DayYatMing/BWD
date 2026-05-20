package com.bwd.nms.web.rest;

import com.bwd.nms.domain.Feature;
import com.bwd.nms.orientdbdomain.Backhaul;
import com.bwd.nms.orientdbdomain.Offnet;
import com.bwd.nms.service.NotificationService;
import com.bwd.nms.service.OffnetService;
import com.bwd.nms.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class OffnetResource {

    private final Logger log = LoggerFactory.getLogger(OffnetResource.class);

    private OffnetService offnetService;

    private NotificationService notificationService;

    public OffnetResource(OffnetService offnetService, NotificationService notificationService) {
        this.offnetService = offnetService;
        this.notificationService = notificationService;
    }

    @GetMapping("/offnets")
    public Flux<Offnet> getAllOffnets() {
        log.debug("REST request to get offnetService");
        return offnetService.getOffnets();
    }

    @PutMapping("/offnets")
    public Mono<Void> updateOffnet(@Valid @RequestBody Offnet offnet) {
        log.debug("REST request to update offnet : {}", offnet);

        return offnetService.findById(offnet.getName())
                .flatMap(oldOffnet -> offnetService.update(offnet)
                    .then(notificationService.createNotificationFromFeature(oldOffnet, offnet, "UPDATE", Feature.OFFNET)
                        .flatMap(notificationService::enabledNotification)));
    }

    @PostMapping("/offnets")
    public Mono<Void> createOffnet(@Valid @RequestBody Offnet offnet) {
        log.debug("REST request to save offnet : {}", offnet);
        if (offnet.getId() != null) {
            throw new BadRequestAlertException("A new offnet cannot already have an ID", "OFFNET", "id exists");
        }
        return offnetService.save(offnet)
            .then(Mono.defer(() ->
                notificationService
                    .createNotificationFromFeature(null, offnet, "INSERT", Feature.OFFNET)
                    .flatMap(notificationService::enabledNotification)
            ));
    }

}
