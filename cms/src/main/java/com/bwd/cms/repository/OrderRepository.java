package com.bwd.cms.repository;

import com.bwd.cms.domain.Order;
import com.bwd.cms.service.dto.OrderDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends R2dbcRepository<Order, Long> {
    @Query(
        """
        SELECT o.id, o.name, o.status, o.entity_id AS entityId, e.name AS entityName, o.duration
        FROM orders o
        JOIN entity e
            ON o.entity_id = e.id
        WHERE e.active = '1'
        """
    )
    Flux<OrderDTO> findAllOrderDTO();

    @Query(
        """
        SELECT o.id, o.name, o.status, o.circuit, o.entity_id AS entityId, e.name AS entityName, o.duration
        FROM orders o
        JOIN entity e
            ON o.entity_id = e.id
        WHERE o.id = :id
        """
    )
    Mono<OrderDTO> findByIdOrderDTO(Long id);

    @Query(
        """
        SELECT o.id, o.name, o.status, o.circuit, o.entity_id AS entityId, e.name AS entityName, o.duration
        FROM orders o
        JOIN entity e
            ON o.entity_id = e.id
        WHERE e.short_name = :code
        """
    )
    Flux<OrderDTO> findAllCustomerOrderDTO(String code);
}
