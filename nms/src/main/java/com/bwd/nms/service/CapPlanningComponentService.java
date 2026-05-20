package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.CapPlanningData;
import com.bwd.nms.orientdbrepository.CapacityPlanningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CapPlanningComponentService {

    private final Logger log = LoggerFactory.getLogger(CapPlanningComponentService.class);

    @Autowired
    public CapacityPlanningRepository capPlanningRepository;

    public Flux<CapPlanningData> getCapPlanningData() {
        return capPlanningRepository.findAll();
    }

}
