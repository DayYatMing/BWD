package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.PMService;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PMServiceRepository extends R2dbcRepository<PMService, Long> {
    @Query(
        "SELECT serv.id, serv.serviceid, serv.customer_id, serv.startdate, serv.enddate, serv.bandwidth, serv.active, serv.visible_to_customer, serv.masked FROM service serv WHERE serv.customer_id = :customerId "
    )
    Flux<PMService> findServices(String customerId);
}
