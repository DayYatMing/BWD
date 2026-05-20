package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.LsiodfportData;
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
public class LsiodfportResource {

    private final Logger log = LoggerFactory.getLogger(LsiodfportResource.class);

    @Autowired
    LsiodfportService lsiodfportService;

    public LsiodfportResource() {
    }

    @GetMapping("/correlation/lsiodf")
    public Flux<LsiodfportData> getAll() {
        log.debug("REST request to get LSI ODF Port");

        return lsiodfportService.getAll();
    }

}
