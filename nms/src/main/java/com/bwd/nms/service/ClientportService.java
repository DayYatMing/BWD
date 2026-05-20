package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.ClientportData;
import com.bwd.nms.orientdbdomain.LsiodfportData;
import com.bwd.nms.orientdbrepository.ClientportRepository;
import com.bwd.nms.orientdbrepository.LsiodfportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ClientportService {
    private final Logger log = LoggerFactory.getLogger(ClientportService.class);

    @Autowired
    ClientportRepository clientportRepository;

    public Flux<ClientportData> getAll()
    {
        return clientportRepository.findAll();
    }
}
