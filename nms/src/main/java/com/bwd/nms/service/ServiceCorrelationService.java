package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.ServiceCorrelationData;
import com.bwd.nms.orientdbrepository.ServiceCorrelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ServiceCorrelationService {
    private final Logger log = LoggerFactory.getLogger(ServiceCorrelationService.class);

    @Autowired
    ServiceCorrelationRepository serviceCorrelationRepository;

    public Flux<ServiceCorrelationData> getAll()
    {
        return serviceCorrelationRepository.findAll();
    }
}
