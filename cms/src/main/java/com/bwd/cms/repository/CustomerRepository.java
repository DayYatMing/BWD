package com.bwd.cms.repository;

import com.bwd.cms.domain.Customer;
import com.bwd.cms.service.dto.CustomerDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends R2dbcRepository<Customer, Long> {
    @Query(
        """
        SELECT e.short_name
        FROM customer c, entity e
        WHERE c.user = :login
          AND c.entity_id = e.id
        """
    )
    Mono<String> findNameByLogin(String login);

    @Query(
        """
        SELECT c.id, c.user, c.contact, c.address, c.entity_id, e.name, e.short_name
        FROM customer c
        JOIN entity e
            ON c.entity_id = e.id
        """
    )
    Flux<CustomerDTO> findAllCustomerDTO();

    @Query(
        """
        SELECT c.id, c.user, c.contact, c.address, c.entity_id, e.name, e.short_name
        FROM customer c
        JOIN entity e
            ON c.entity_id = e.id
        WHERE c.id = :id
        """
    )
    Mono<CustomerDTO> findByIdCustomerDTO(Long id);
}
