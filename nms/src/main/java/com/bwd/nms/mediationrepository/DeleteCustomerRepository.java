package com.bwd.nms.mediationrepository;
import com.bwd.nms.mediationdomain.ManageCustomer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.persistence.PersistenceContext;

@Repository
public interface DeleteCustomerRepository extends R2dbcRepository< ManageCustomer, Long> {
    @Query("DELETE FROM reserved_capacity_calculation WHERE customer_id = :customerId")
    Mono<Void> deleteByCustomerId(Integer customerId);

}
