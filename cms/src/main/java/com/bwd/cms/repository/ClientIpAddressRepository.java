package com.bwd.cms.repository;

import com.bwd.cms.domain.ClientIpAddress;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientIpAddressRepository extends R2dbcRepository<ClientIpAddress, Long> {}
