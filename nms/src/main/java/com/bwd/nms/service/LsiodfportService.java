package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.LsiodfportData;
import com.bwd.nms.orientdbrepository.LsiodfportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class LsiodfportService {
    private final Logger log = LoggerFactory.getLogger(LsiodfportService.class);

    @Autowired
    LsiodfportRepository lsiodfportRepository;

    public Flux<LsiodfportData> getAll()
    {
        return lsiodfportRepository.findAll();
    }
}
