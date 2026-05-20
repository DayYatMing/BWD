package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.Port;
import com.bwd.nms.service.PortService;
import com.bwd.nms.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class PortResource {

    private final Logger log = LoggerFactory.getLogger(PortResource.class);

    private PortService portService;

    public PortResource(PortService portService) {
        this.portService = portService;
    }

    @GetMapping("/ports")
    public Flux<Port> getAllPorts() {
        log.debug("REST request to get portService");
        return portService.getPorts();
    }

    @PostMapping("/ports")
    public Mono<Void> createPort(@Valid @RequestBody Port port) {
        log.debug("REST request to save port : {}", port);
        if (port.getId() != null) {
            throw new BadRequestAlertException("A new port cannot already have an ID", "PORT", "id exists");
        }
        return portService.save(port);
    }

    @PutMapping("/ports")
    public Mono<Void> updatePort(@Valid @RequestBody Port port) {
        log.debug("REST request to update port : {}", port);

        return portService.update(port);
    }

    @PostMapping(value = "/ports/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> createOrder(
        @RequestPart(value = "uploadedBulkPortsCSV") FilePart bulkPorts
    ) {
        log.info("Uploading and importing multiple ports.");

        return portService.upload(bulkPorts);
    }

}
