package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.CapacityRepLabel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CapacityRepLabelRepository extends ReactiveCrudRepository<CapacityRepLabel, Long> {

    @Query("""
        SELECT sf.id, s.segment_id, s.fibre_pair_id,
               (SELECT name FROM pm_db.ref_segment WHERE id = s.segment_id) AS segment_name,
               (SELECT name FROM pm_db.fiber_pair WHERE id = s.fibre_pair_id) AS fiber_pair_name,
               (SELECT name FROM pm_db.dls WHERE id = sf.dls_id) AS dlsname
        FROM pm_db.segment_fp_xrf s
        JOIN pm_db.segment_fp_dls_xrf sf ON sf.segment_fp_id = s.id
        """)
    Flux<CapacityRepLabel> findCapacityRepLabelData();
}
