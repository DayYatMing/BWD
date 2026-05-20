package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.Backhaul;
import com.bwd.nms.orientdbdomain.Offnet;
import com.bwd.nms.orientdbrepository.OffnetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OffnetService {

    private final Logger log = LoggerFactory.getLogger(OffnetService.class);

    @Autowired
    public OffnetRepository offnetRepository;

    public Flux<Offnet> getOffnets() {
        return offnetRepository.findAll();
    }


    public Mono<Void> update(Offnet offnet) {
        return offnetRepository.update(offnet);
    }

    public Mono<Void> save(Offnet offnet) {
        return offnetRepository.save(offnet);
    }

    public Mono<Offnet> findById(String id) {
        return offnetRepository.findById(id);
    }
}
