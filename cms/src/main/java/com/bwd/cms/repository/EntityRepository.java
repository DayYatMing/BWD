package com.bwd.cms.repository;

import com.bwd.cms.domain.Entity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EntityRepository extends R2dbcRepository<Entity, Long> {
    Flux<Entity> findByActiveOrderByNameAsc(String active);
}
