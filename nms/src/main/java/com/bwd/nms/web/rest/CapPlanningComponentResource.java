package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.CapPlanningData;
import com.bwd.nms.service.CapPlanningComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ICS-OSCAR
 *
 */

@RestController
@RequestMapping("/api")
public class CapPlanningComponentResource {

    private final Logger log = LoggerFactory.getLogger(CapPlanningComponentResource.class);

    public CapPlanningComponentResource() {}

    @Autowired
    private CapPlanningComponentService capPlanningComponentService;

    @GetMapping("/capplanning/partialcapplanningdata")
    public Flux<CapPlanningData> getPartialCapData() {
        log.debug("REST request to getPartialCapData");
        return capPlanningComponentService.getCapPlanningData().switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
