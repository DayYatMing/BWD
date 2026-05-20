package com.bwd.cms.web.rest;

import com.bwd.cms.domain.Customer;
import com.bwd.cms.domain.Entity;
import com.bwd.cms.service.CustomerService;
import com.bwd.cms.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class EntityResource {

    private final Logger LOG = LoggerFactory.getLogger(EntityResource.class);

    @Autowired
    EntityService entityService;

    @GetMapping("/entity/active")
    public Mono<ResponseEntity<Flux<Entity>>> getActiveEntities() {
        LOG.info("Rest to get active Entities.");

        return Mono.just(ResponseEntity.ok().header("status", "ok").body(entityService.getActiveEntities("1")));
    }

    @GetMapping("/entity")
    public Mono<ResponseEntity<Flux<Entity>>> getAllEntities() {
        LOG.info("Rest to get all Entities.");

        return Mono.just(ResponseEntity.ok().header("status", "ok").body(entityService.getAllEntities()));
    }

    @GetMapping("/entity/{id}")
    public Mono<ResponseEntity<Entity>> getEntityById(@PathVariable Long id) {
        LOG.info("Rest to get Entity by ID.");

        return entityService
            .getEntityById(id)
            .map(entity -> ResponseEntity.ok().header("status", "ok").body(entity))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/entity")
    public Mono<ResponseEntity<Void>> createEntity(@RequestBody Entity entity) {
        LOG.info("Rest to create a new entity.");

        return entityService
            .createEntity(entity)
            .map(savedEntity -> ResponseEntity.status(HttpStatus.CREATED).header("status", "created").build());
    }

    @PutMapping("/entity")
    public Mono<ResponseEntity<Void>> updateEntity(@RequestBody Entity entity) {
        LOG.info("Rest to update an existing entity.");

        return entityService
            .createEntity(entity)
            .map(savedEntity -> ResponseEntity.status(HttpStatus.CREATED).header("status", "updated").build());
    }

    @DeleteMapping("/entity/{id}")
    public Mono<ResponseEntity<Void>> deleteEntity(@PathVariable Long id) {
        LOG.info("Rest to delete an existing entity.");

        return entityService.deleteEntity(id).then(Mono.just(ResponseEntity.ok().header("status", "deleted").build()));
    }
}
