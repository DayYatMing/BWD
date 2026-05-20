package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.DlsCapacity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DlsCapacityRepository extends ReactiveCrudRepository<DlsCapacity, Long> {

    @Query("""
        SELECT sfx.id AS sfxtblid,
               sfx.dls_id AS dlsid,
               sfx.is_reserved AS isreserved,
               sfx.segment_fp_id,
               c.id AS crossTableid,
               c.capacity_id AS capacity_id,
               (SELECT name FROM pm_db.dls d WHERE d.id = sfx.dls_id) AS dlsname,
               (SELECT name FROM pm_db.ref_capacity WHERE id = c.capacity_id) AS capacity_name
        FROM pm_db.segment_fp_dls_xrf sfx
        JOIN pm_db.capacity_segment_fp_dls_xrf c ON sfx.id = c.segment_fp_dls_id
        """)
    Flux<DlsCapacity> findDlsCapacityData();
}
