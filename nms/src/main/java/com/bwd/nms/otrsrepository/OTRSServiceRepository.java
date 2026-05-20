package com.bwd.nms.otrsrepository;

import com.bwd.nms.otrsdomain.Service;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface OTRSServiceRepository extends R2dbcRepository<Service,Long> {
}
