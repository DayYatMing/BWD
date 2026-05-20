package com.bwd.nms.otrsrepository;

import com.bwd.nms.otrsdomain.TicketType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LinkRepository extends R2dbcRepository<TicketType,Long> {

    @Query(value = "SELECT t.tn as id , t.title as name FROM ticket t INNER JOIN link_relation lr ON t.id = lr.target_key WHERE lr.source_key = :id " )
    List<TicketType> findAllSlaveTickets(@Param("id") String id);

    @Query(value = "SELECT t.tn  as id , t.title  as name  FROM ticket t INNER JOIN link_relation lr ON t.id = lr.source_key WHERE lr.target_key = :id " )
    List<TicketType>  findAllMasterTickets(@Param("id") String id);


}
