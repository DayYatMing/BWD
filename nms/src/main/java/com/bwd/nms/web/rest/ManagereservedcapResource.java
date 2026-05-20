package com.bwd.nms.web.rest;
import com.bwd.nms.mediationdomain.InsertColStatus;
import com.bwd.nms.mediationdomain.PMCustomer;
import com.bwd.nms.mediationdomain.UpdateCols;
import com.bwd.nms.service.dto.ManageReservedCapDTO;
import com.bwd.nms.service.dto.DlsCapacityDTO;
import com.bwd.nms.service.dto.ColHeadersDTO;
import com.bwd.nms.service.dto.CapacityRepLabelDTO;
import com.bwd.nms.service.ManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author ICS-Nisha
 *
 */


@RestController
@RequestMapping("/api")
public class ManagereservedcapResource {
    private final Logger log = LoggerFactory.getLogger(ManagereservedcapResource.class);
    public ManagereservedcapResource() {}

    @Autowired
    private ManageService manageService;

    @GetMapping("/managereservedcap")

    public Flux<ManageReservedCapDTO> getAllData() {
       return (manageService.findAllData());
    }


    @GetMapping("/managereservedcap/customer")
    public Flux<PMCustomer> getAllCustomers() {
        return (manageService.findAllCustomer());
    }

    @GetMapping("/managereservedcap/dlscapacity")
    public Flux<DlsCapacityDTO> getDlsCapacity() {
        return (manageService.findDlsCapacityData());
    }


    @GetMapping("/managereservedcap/colHeaders")
    public Flux<ColHeadersDTO> getColheaders() {
        return (manageService.findColHeadersData());
    }

    @GetMapping("/capacityreport/capacityRepLabel")
    public Flux<CapacityRepLabelDTO> getCapacityRepLabel() {
        return (manageService.findCapacityRepLabelData());
    }

    @PostMapping("/managereservedcap/update")
    public Mono<UpdateCols> updateData(@RequestBody Mono<UpdateCols> updateCols) {
        return updateCols.flatMap(u -> manageService.update(u));
    }

    @PostMapping("/managereservedcap/save")
    public Mono<InsertColStatus> insertData(@RequestBody InsertColStatus insertColStatus) {
        return manageService.save(insertColStatus);
    }
    @DeleteMapping("/managereservedcap/delete")
    public Mono<ResponseEntity<Void>> deleteRecord(@RequestParam Integer id) {
        log.debug("REST request to delete reserved capacity data with ID: {}", id);
        return manageService.deleteRecordById(id)   // make this return Mono<Void>
            .then(Mono.just(ResponseEntity.ok().build()));  // return 200 OK when complete
    }
}

