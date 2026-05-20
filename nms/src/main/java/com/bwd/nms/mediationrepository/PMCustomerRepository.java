package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.PMCustomer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PMCustomerRepository extends R2dbcRepository<PMCustomer, Long> {
    @Query("SELECT cus.id, cus.name, cus.shortname, cus.active FROM customer cus ")
    Flux<PMCustomer> findCustomers();
}
