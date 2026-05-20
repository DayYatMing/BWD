package com.bwd.nms.web.rest;

import com.bwd.nms.domain.Feature;
import com.bwd.nms.orientdbdomain.Segment;
import com.bwd.nms.orientdbdomain.Site;
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
 * REST controller for managing segment.
 */
@RestController
@RequestMapping("/api")
public class SegmentResource {

    private final Logger log = LoggerFactory.getLogger(SegmentResource.class);

    private SegmentService segmentService;
    private NotificationService notificationService;

    public SegmentResource(SegmentService segmentService, NotificationService notificationService) {
        this.segmentService = segmentService;
        this.notificationService = notificationService;
    }

    @GetMapping("/segments")
    public Flux<Segment> getAllSegments() {
        log.debug("REST request to get Segments");
        return segmentService.getSegments();
    }

    @PutMapping("/segments")
    public Mono<Void> updateSegment(@Valid @RequestBody Segment segment) {
        log.debug("REST request to update segment : {}", segment);

        return segmentService.findById(segment.getId())
                .flatMap(oldSegment -> segmentService.update(segment)
                    .then(
                        notificationService.createNotificationFromFeature(oldSegment, segment, "UPDATE", Feature.SEGMENT)
                            .flatMap(notificationService::enabledNotification)));
    }

    @PostMapping("/segments")
    public Mono<Void> createSegment(@Valid @RequestBody Segment segment) {
        log.debug("REST request to save segment : {}", segment);
        if (segment.getId() != null) {
            throw new BadRequestAlertException("A new segment cannot already have an ID", "SEGMENT", "id exists");
        }
        return segmentService.save(segment)
            .then(Mono.defer(() ->
                notificationService
                    .createNotificationFromFeature(null, segment, "INSERT", Feature.SEGMENT)
                    .flatMap(notificationService::enabledNotification)
            ));
    }

}
