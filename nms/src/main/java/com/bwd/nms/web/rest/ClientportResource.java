package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.ClientportData;
import com.bwd.nms.orientdbdomain.LsiodfportData;
import com.bwd.nms.service.ClientportService;
import com.bwd.nms.service.LsiodfportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ClientportResource {

    private final Logger log = LoggerFactory.getLogger(ClientportResource.class);

    @Autowired
    ClientportService clientportService;

    public ClientportResource() {
    }

    @GetMapping("/correlation/clientport")
    public Flux<ClientportData> getAll() {
        log.debug("REST request to get Client Port");

        return clientportService.getAll();
    }

}
