package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.OdfmmrportData;
import com.bwd.nms.orientdbrepository.OdfmmrportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OdfmmrportService {
    private final Logger log = LoggerFactory.getLogger(OdfmmrportService.class);

    @Autowired
    OdfmmrportRepository odfmmrportRepository;

    public Flux<OdfmmrportData> getAll()
    {
        return odfmmrportRepository.findAll();
    }
}
