package com.bwd.nms.web.rest;

import com.bwd.nms.domain.Timeline;
import com.bwd.nms.domain.Notification;

import com.bwd.nms.service.TimelineService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class TimelineResource {

    private final Logger log = LoggerFactory.getLogger(TimelineResource.class);

    @Autowired
    private final TimelineService timelineService;

    public TimelineResource(TimelineService timelineService) {

        this.timelineService = timelineService;
    }

    @GetMapping("/timeline")
    public Flux<Notification> getTimelineData() {
        log.debug("REST request to get timeline data");
        return timelineService.findAll();
    }


}
