package com.bwd.nms.otrsrepository;

import com.bwd.nms.otrsdomain.TicketType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TicketTypeRepository extends R2dbcRepository<TicketType,Long> {

    @Query(value = "SELECT NAME FROM ticket_type WHERE valid_id = 1")
    List<String> findTickeTypes();
}
