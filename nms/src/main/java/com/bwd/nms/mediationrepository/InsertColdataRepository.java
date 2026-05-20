package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.InsertColStatus;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository

public interface InsertColdataRepository extends R2dbcRepository<InsertColStatus, Integer> {
    @Query( value = "Insert into reserved_capacity_calculation (customer_id, cap_seg_fp_dls_id, total) VALUES (:customer_id, :cap_seg_fp_dls_id, :total)")
    Mono<Integer> saveinsert(@Param("customer_id")Integer customer_id, @Param("cap_seg_fp_dls_id")Integer cap_seg_fp_dls_id, @Param("total")String total);
}


