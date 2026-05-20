package com.bwd.nms.web.rest;

import com.bwd.nms.service.NetworkStateService;
import com.bwd.nms.service.dto.NetworkStateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class NetworkStateResource {

    private final Logger log = LoggerFactory.getLogger(NetworkStateResource.class);


    private NetworkStateService networkStateService;

    public NetworkStateResource(NetworkStateService networkStateService) {
        this.networkStateService = networkStateService;
    }

    @GetMapping("/correlation/networkstate")
    public Mono<NetworkStateDTO> getNetworkState() {
        log.debug("REST request to getNetworkState...");
        return networkStateService.getNetworkState();
    }

    @PostMapping(value = "/correlation/networkstate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> createNetworkState(
        @RequestPart("updatedby") String updatedBy,
        @RequestPart(value = "networkimage") FilePart networkImage
    ) {
        log.debug("REST request to upload network state diagram...");

        return networkStateService.uploadFile(updatedBy, networkImage);
    }

    @PostMapping(value = "/correlation/networkstate/update")
    public Mono<NetworkStateDTO> updateNetworkState(
        @RequestParam Long id,
        @RequestParam Boolean withDetails
    ) {
        log.debug("REST request to update network state diagram for version: {}, with full details: {}", id, withDetails);
        return networkStateService.updateNetworkState(id, withDetails);
    }

}
