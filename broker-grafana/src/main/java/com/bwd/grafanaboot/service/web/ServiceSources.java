package com.bwd.grafanaboot.service.web;

import com.bwd.grafanaboot.persistence.repository.ServiceSourcesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceSources {

    private static final Logger logger = LoggerFactory.getLogger(ServiceSources.class);

    @Autowired
    ServiceSourcesRepository serviceSourcesRepository;

    public List<String> getSources(String service) {

        return serviceSourcesRepository.findSources(service);
    }

}
