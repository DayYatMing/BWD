package com.bwd.nms.service;

import com.bwd.nms.otrsdomain.CustomerUser;
import com.bwd.nms.otrsrepository.CustomerUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerUserService {

    @Autowired
    CustomerUserRepository customerUserRepository;

    private final Logger log = LoggerFactory.getLogger(CustomerUserService.class);

    public List<CustomerUser> getCustomerId(String customerId){

       return customerUserRepository.findByCustomerid(customerId)
           .collectList()
           .block();
    }

}
