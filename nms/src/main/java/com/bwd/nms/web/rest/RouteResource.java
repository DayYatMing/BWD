package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.Route;
import com.bwd.nms.service.RouteService;
import com.bwd.nms.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class RouteResource {

    private final Logger log = LoggerFactory.getLogger(RouteResource.class);

    private RouteService routeService;

    public RouteResource(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/routes")
    public Flux<Route> getAllRoutes() {
        log.debug("REST request to get routeService...");
        return routeService.getRoutes();
    }

    @PostMapping("/routes")
    public Mono<Void> createRoute(@Valid @RequestBody Route route) {
        log.debug("REST request to save route : {}", route);
        if (route.getId() != null) {
            throw new BadRequestAlertException("A new route cannot already have an ID", "ROUTE", "id exists");
        }
        return routeService.save(route);
    }

    @PutMapping("/routes")
    public Mono<Void> updateRoute(@Valid @RequestBody Route route) {
        log.debug("REST request to update route : {}", route);

        return routeService.update(route);
    }

}
