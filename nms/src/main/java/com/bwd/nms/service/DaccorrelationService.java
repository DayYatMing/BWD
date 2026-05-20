package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.DaccorrelationData;
import com.bwd.nms.orientdbdomain.LsiodfportData;
import com.bwd.nms.orientdbrepository.DaccorrelationRepository;
import com.bwd.nms.orientdbrepository.LsiodfportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DaccorrelationService {
    private final Logger log = LoggerFactory.getLogger(DaccorrelationService.class);

    @Autowired
    DaccorrelationRepository daccorrelationRepository;

    public Flux<DaccorrelationData> getAll()
    {
        return daccorrelationRepository.findAll();
    }

    public Mono<Void> createData(DaccorrelationData  daccorrelationData){
        return daccorrelationRepository.createData(daccorrelationData);
    }

    public Mono<Void> updateData(DaccorrelationData  daccorrelationData){
        return daccorrelationRepository.updateData(daccorrelationData);
    }
}
