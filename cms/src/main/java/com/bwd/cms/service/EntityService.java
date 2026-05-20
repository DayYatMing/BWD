package com.bwd.cms.service;

import com.bwd.cms.domain.Entity;
import com.bwd.cms.repository.EntityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EntityService {

    private final EntityRepository entityRepository;

    public EntityService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public Flux<Entity> getActiveEntities(String active) {
        return entityRepository.findByActiveOrderByNameAsc(active);
    }

    public Flux<Entity> getAllEntities() {
        return entityRepository.findAll();
    }

    public Mono<Entity> getEntityById(Long id) {
        return entityRepository.findById(id);
    }

    public Mono<Entity> createEntity(Entity entity) {
        return entityRepository.save(entity);
    }

    public Mono<Void> deleteEntity(Long id) {
        return entityRepository.deleteById(id);
    }
}
