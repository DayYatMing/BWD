package com.bwd.nms.service;

import com.bwd.nms.mediationdomain.PMCustomer;
import com.bwd.nms.service.dto.AlertsStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.Optional;

@Component
public class OperationalService {

    private final Logger log = LoggerFactory.getLogger(OperationalService.class);

    private ExternalService externalService;

    private PMComponentService  pmComponentService;

    public OperationalService(ExternalService  externalService, PMComponentService pmComponentService) {
        this.externalService = externalService;
        this.pmComponentService = pmComponentService;
    }

    public Flux<AlertsStatusResponse> sendAndProcessEvents() {

        Mono<Map<String, String>> customerMapMono =
            pmComponentService.getPMCustomers()
                .collectMap(
                    PMCustomer::getShortname,
                    PMCustomer::getName
                );

        return customerMapMono.flatMapMany(customerMap ->
            Flux.fromIterable(externalService.sendAndProcessEvents())
                .map(alert -> {

                    String serviceName = alert.getServiceName();

                    if (serviceName != null && serviceName.length() >= 3) {

                        String prefix = serviceName.substring(0, 3);

                        String customerName = customerMap.get(prefix);

                        if (customerName != null) {
                            alert.setCustomerName(customerName);
                        }
                    }

                    return alert;
                })
        );
    }

}
