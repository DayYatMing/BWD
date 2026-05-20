package com.bwd.grafanaboot.service.web;

import com.bwd.grafanaboot.persistence.repository.CustomerServicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServices {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServices.class);

    @Autowired
    CustomerServicesRepository customerServicesRepository;

    public List<String> getServices(String customerId) {

        return customerServicesRepository.findServices(customerId + "%");
    }

}
