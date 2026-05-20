package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.Backhaul;
import com.bwd.nms.orientdbdomain.Segment;
import com.bwd.nms.orientdbrepository.BackhaulRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BackhaulService {

    private final Logger log = LoggerFactory.getLogger(BackhaulService.class);

    @Autowired
    public BackhaulRepository backhaulRepository;

    public Flux<Backhaul> getBackhauls() {
        return backhaulRepository.findAll();
    }

    public Mono<Void> update(Backhaul backhaul) {
        return backhaulRepository.update(backhaul);
    }

    public Mono<Void> save(Backhaul backhaul) {
        return backhaulRepository.save(backhaul);
    }

    public Mono<Backhaul> findById(String id) {
        return backhaulRepository.findById(id);
    }
}
