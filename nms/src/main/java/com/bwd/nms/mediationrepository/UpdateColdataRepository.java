package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.UpdateCols;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface UpdateColdataRepository extends R2dbcRepository<UpdateCols, Integer> {
    @Query( value = "Update  reserved_capacity_calculation set total = :total where id = :id " )
    Mono<Integer> update(String total, Integer id);
}
