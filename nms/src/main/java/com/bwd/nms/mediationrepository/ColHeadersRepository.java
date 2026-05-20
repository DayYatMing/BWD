package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.ColHeaders;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ColHeadersRepository extends ReactiveCrudRepository<ColHeaders, Long> {

    @Query("""
        SELECT c.id AS tbl_id, c.capacity_id,
               (SELECT name FROM pm_db.ref_capacity WHERE id = c.capacity_id) AS capacity_name,
               (SELECT name FROM pm_db.dls WHERE id = s.dls_id) AS dlsname,
               (SELECT name FROM pm_db.fiber_pair WHERE id = sf.fibre_pair_id) AS fibrepairname,
               (SELECT name FROM pm_db.ref_segment WHERE id = sf.segment_id) AS segmentname
        FROM pm_db.capacity_segment_fp_dls_xrf c
        JOIN pm_db.segment_fp_dls_xrf s ON s.id = c.segment_fp_dls_id
        JOIN pm_db.segment_fp_xrf sf ON sf.id = s.segment_fp_id
        WHERE s.is_reserved = 1
        """)
    Flux<ColHeaders> findColHeadersData();
}
