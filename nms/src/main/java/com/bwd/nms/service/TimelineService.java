package com.bwd.nms.service;

import com.bwd.nms.domain.Notification;

import com.bwd.nms.domain.Timeline;
import com.bwd.nms.repository.TimelineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class TimelineService {

    private final Logger log = LoggerFactory.getLogger(TimelineService.class);

    @Autowired
    private TimelineRepository timelineRepository;

    public Flux<Notification> findAll() {

        return timelineRepository.findAllByEventDate();
    }

    public Mono<Void> save(Timeline timeline) {
        return timelineRepository.save(timeline).then();
    }


}
