package com.bwd.nms.otrsrepository;

import com.bwd.nms.otrsdomain.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface OtrsCustomerRepository extends R2dbcRepository<Customer,Long> {

}
