
package com.bwd.nms.web.rest;
import com.bwd.nms.service.OperationalService;
import com.bwd.nms.service.dto.AlertsStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api")
public class OprationalResource {

    public OprationalResource() {}
    @Autowired
    private OperationalService operationalService;

    @GetMapping("/operationalstate/events")
    public Flux<AlertsStatusResponse> getAllEvents() {
        return operationalService.sendAndProcessEvents();
    }


}
