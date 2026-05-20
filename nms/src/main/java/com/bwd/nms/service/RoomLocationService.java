package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.RoomLocation;
import com.bwd.nms.orientdbrepository.RoomLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomLocationService {

    private final Logger log = LoggerFactory.getLogger(RoomLocationService.class);

    @Autowired
    public RoomLocationRepository roomLocationRepository;

    public Flux<RoomLocation> getRoomLocations() {
        return roomLocationRepository.findAll();
    }

    public Mono<Void> update(RoomLocation roomLocation) {
        return roomLocationRepository.update(roomLocation);
    }

    public Mono<Void> save(RoomLocation roomLocation) {
        return roomLocationRepository.save(roomLocation);
    }
}
