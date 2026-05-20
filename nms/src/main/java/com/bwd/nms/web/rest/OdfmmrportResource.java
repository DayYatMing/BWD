package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.OdfmmrportData;
import com.bwd.nms.service.OdfmmrportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class OdfmmrportResource {

    private final Logger log = LoggerFactory.getLogger(OdfmmrportResource.class);

    @Autowired
    OdfmmrportService odfmmrportService;

    public OdfmmrportResource() {
    }

    @GetMapping("/correlation/odfmmr")
    public Flux<OdfmmrportData> getAll() {
        log.debug("REST request to get ODF MMR Port");

        return odfmmrportService.getAll();
    }

}
