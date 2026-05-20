package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.PMSource;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PMSourceRepository extends R2dbcRepository<PMSource, Long> {
    @Query(
        "SELECT sour.id, sour.customer, sour.customer_sid, sour.pm_source, sour.visible_to_customer, sour.route_end, sour.segment_end, sour.source, sour.node_id, sour.route, sour.segment FROM service_source sour WHERE sour.customer_sid = :serviceId "
    )
    Flux<PMSource> findSources(String serviceId);
}
