package com.bwd.nms.web.rest;

import com.bwd.nms.mediationdomain.PMConfiguration;
import com.bwd.nms.mediationdomain.PMCustomer;
import com.bwd.nms.mediationdomain.PMService;
import com.bwd.nms.mediationdomain.PMSource;
import com.bwd.nms.service.PMComponentService;
import com.bwd.nms.service.dto.PMConfigurationDTO;
import com.bwd.nms.service.dto.PMCustomerDTO;
import com.bwd.nms.service.dto.PMServiceDTO;
import com.bwd.nms.service.dto.PMSourceDTO;
import jakarta.validation.Valid;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ICS-OSCAR
 *
 */

@RestController
@RequestMapping("/api")
public class PMComponentResource {

    private final Logger log = LoggerFactory.getLogger(PMComponentResource.class);

    @Autowired
    private PMComponentService pmComponentService;

    public PMComponentResource() {}

    @GetMapping("/pm/customers")
    public Flux<PMCustomer> getPMCustomers() {
        log.debug("REST request to get customers");
        return pmComponentService.getPMCustomers().switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/pm/services/{customerId}")
    public Flux<PMService> getPMServices(@PathVariable String customerId) {
        log.debug("REST request to get services from customer id " + customerId);
        return pmComponentService.getPMServices(customerId).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/pm/sources/{serviceId}")
    public Flux<PMSource> getPMSources(@PathVariable String serviceId) {
        log.debug("REST request to get sources from service id " + serviceId);
        return pmComponentService.getPMSources(serviceId).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/pm/configurations/{serviceId}")
    public Flux<PMConfiguration> getPMConfigurations(@PathVariable String serviceId) {
        log.debug("REST request to get configurations from service id " + serviceId);
        return pmComponentService
            .getPMConfigurations(serviceId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/pm/customer")
    public Mono<Void> updateOrAddPMCustomer(@Valid @RequestBody PMCustomerDTO pmCustomerDTO) throws URISyntaxException {
        log.debug("REST request to update or add customer " + pmCustomerDTO.getName());
        return pmComponentService.updateOrAddPMCustomer(pmCustomerDTO);
    }

    @PostMapping("/pm/service")
    public Mono<Void> updateOrAddPMService(@Valid @RequestBody PMServiceDTO pmServiceDTO) throws URISyntaxException {
        log.debug("REST request to update or add service " + pmServiceDTO.getServiceId());
        return pmComponentService.updateOrAddPMService(pmServiceDTO);
    }

    @PostMapping("/pm/source")
    public Mono<Void> updateOrAddPMSource(@Valid @RequestBody PMSourceDTO pmSourceDTO) throws URISyntaxException {
        log.debug("REST request to update or add source " + pmSourceDTO.getCustomerSid());
        return pmComponentService.updateOrAddPMSource(pmSourceDTO);
    }

    @PostMapping("/pm/configuration")
    public Mono<Void> updateOrAddPMConfiguration(@Valid @RequestBody PMConfigurationDTO pmConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update or add configuration " + pmConfigurationDTO.getServiceId());
        return pmComponentService.updateOrAddPMConfiguration(pmConfigurationDTO);
    }
}
