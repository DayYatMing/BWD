package com.bwd.nms.otrsrepository;

import com.bwd.nms.otrsdomain.CustomerUser;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerUserRepository extends R2dbcRepository<CustomerUser, Long> {

    @Query("SELECT * FROM customer_user WHERE customer_id LIKE CONCAT('%', :customerid, '%') AND valid_id = 1")
    Flux<CustomerUser> findByCustomerid(@Param("customerid") String customerid);
}
