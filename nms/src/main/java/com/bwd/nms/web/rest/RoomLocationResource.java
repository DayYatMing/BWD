package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.RoomLocation;
import com.bwd.nms.orientdbdomain.Route;
import com.bwd.nms.service.RoomLocationService;
import com.bwd.nms.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class RoomLocationResource {

    private final Logger log = LoggerFactory.getLogger(RoomLocationResource.class);

    private RoomLocationService roomLocationService;

    public RoomLocationResource(RoomLocationService roomLocationService) {
        this.roomLocationService = roomLocationService;
    }

    @GetMapping("/roomlocations")
    public Flux<RoomLocation> getAllRoomLocations() {
        log.debug("REST request to get roomLocationService");
        return roomLocationService.getRoomLocations();
    }

    @PostMapping("/roomlocations")
    public Mono<Void> createRoomLocation(@Valid @RequestBody RoomLocation roomLocation) {
        log.debug("REST request to save RoomLocation : {}", roomLocation);
        if (roomLocation.getId() != null) {
            throw new BadRequestAlertException("A new roomLocation cannot already have an ID", "ROOMLOCATION", "id exists");
        }
        return roomLocationService.save(roomLocation);
    }

    @PutMapping("/roomlocations")
    public Mono<Void> updateRoomLocation(@Valid @RequestBody RoomLocation roomLocation) {
        log.debug("REST request to update roomLocation : {}", roomLocation);

        return roomLocationService.update(roomLocation);
    }

}
