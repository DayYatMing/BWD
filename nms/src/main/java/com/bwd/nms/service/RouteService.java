package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.Offnet;
import com.bwd.nms.orientdbdomain.Route;
import com.bwd.nms.orientdbrepository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RouteService {

    private final Logger log = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    public RouteRepository routeRepository;

    public Flux<Route> getRoutes() {
        return routeRepository.findAll();
    }


    public Mono<Void> update(Route route) {
        return routeRepository.update(route);
    }

    public Mono<Void> save(Route route) {
        return routeRepository.save(route);
    }

}
