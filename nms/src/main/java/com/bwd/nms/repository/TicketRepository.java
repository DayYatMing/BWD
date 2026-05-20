package com.bwd.nms.repository;

import com.bwd.nms.domain.Ticket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TicketRepository extends R2dbcRepository<Ticket, String> {

    @Query("select * from ticket order by event_date desc")
    Flux<Ticket> findAllByEventDate();
}
