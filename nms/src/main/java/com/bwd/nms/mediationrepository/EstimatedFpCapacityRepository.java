package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.EstimatedFpCapacity;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EstimatedFpCapacityRepository extends ReactiveCrudRepository<EstimatedFpCapacity, Long> {

    // ✅ SELECT
    @Query("SELECT * FROM estimated_fp_capacity WHERE id = 1")
    Flux<EstimatedFpCapacity> findEstimatedCapacityData();

    // ✅ UPDATE
    @Query("UPDATE estimated_fp_capacity SET " +
        "au_nz_fp1_dls16 = :au_nz_fp1_dls16, " +
        "nz_hi_fp1_dls02 = :nz_hi_fp1_dls02, " +
        "nz_hi_fp1_dls13 = :nz_hi_fp1_dls13, " +
        "nz_hi_fp1_dls11 = :nz_hi_fp1_dls11, " +
        "au_hi_fp1_dls14 = :au_hi_fp1_dls14, " +
        "au_hi_fp2_dls54 = :au_hi_fp2_dls54, " +
        "hi_uf_fp1_dls27 = :hi_uf_fp1_dls27, " +
        "hi_uf_fp1_dls67 = :hi_uf_fp1_dls67, " +
        "hi_uf_fp2_dls57 = :hi_uf_fp2_dls57, " +
        "hi_uf_fp3_dls = :hi_uf_fp3_dls " +
        "WHERE id = :id")
    Mono<Integer> updateEstimatedCapacity(
        String au_nz_fp1_dls16,
        String nz_hi_fp1_dls02,
        String nz_hi_fp1_dls13,
        String nz_hi_fp1_dls11,
        String au_hi_fp1_dls14,
        String au_hi_fp2_dls54,
        String hi_uf_fp1_dls27,
        String hi_uf_fp1_dls67,
        String hi_uf_fp2_dls57,
        String hi_uf_fp3_dls,
        Integer id
    );
}
