package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.MCPServiceIdSync;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MCPServiceIdSyncRepository extends R2dbcRepository<MCPServiceIdSync, Long> {

    @Query( value = "UPDATE service SET serviceid = ?1 WHERE serviceid LIKE ?2% " )
    void updateService(String serviceId, String pattern);

    @Query( value = "UPDATE service_source SET customer_sid = ?1 WHERE customer_sid LIKE ?2% " )
    void updateServiceSource(String serviceId, String pattern);

    @Query( value = "UPDATE configuration SET serviceid = ?1 WHERE serviceid LIKE ?2% " )
    void updateServiceConfiguration(String serviceId, String pattern);

}
