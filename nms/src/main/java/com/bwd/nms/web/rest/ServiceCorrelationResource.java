package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.ServiceCorrelationData;
import com.bwd.nms.service.ServiceCorrelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ServiceCorrelationResource {

    private final Logger log = LoggerFactory.getLogger(ServiceCorrelationResource.class);

    @Autowired
    ServiceCorrelationService  serviceCorrelationService;

    public ServiceCorrelationResource() {
    }

    @GetMapping("/correlation/servicecorrelation")
    public Flux<ServiceCorrelationData> getAll() {
        log.debug("REST request to get Service Correlation");

        return serviceCorrelationService.getAll();
    }

}
