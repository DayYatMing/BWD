package com.bwd.nms.mediationrepository;

import com.bwd.nms.mediationdomain.PMConfiguration;
import com.bwd.nms.service.dto.PMConfigurationDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface PMConfigurationRepository extends R2dbcRepository<PMConfiguration, Long> {
    @Query("SELECT conf.id, conf.serviceid, conf.sourcename, conf.nodeid, conf.vendor, conf.label_name, conf.route_direction, conf.segment_direction, conf.disable_collection, conf.visible_to_customer, conf.frequency FROM configuration conf WHERE conf.serviceid = :serviceId")
    Flux<PMConfiguration> findConfigurations(String serviceId);

    @Query("SELECT *, name as customer FROM ( SELECT *, SUBSTRING(con.serviceid, 1, 3) AS shrtnm FROM configuration con  ) AS con JOIN customer cust ON con.shrtnm = cust.shortname JOIN service ser ON con.serviceid = ser.serviceid WHERE ser.active = 1 AND label_name IS NOT NULL GROUP BY label_name")
    Flux<PMConfigurationDTO> findAllConfigurationDTO();
}
