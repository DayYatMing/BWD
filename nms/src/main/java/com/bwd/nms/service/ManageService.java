package com.bwd.nms.service;

import com.bwd.nms.mediationrepository.ManageReservedCapRepository;
import com.bwd.nms.mediationdomain.PMCustomer;
import com.bwd.nms.mediationdomain.ManageReservedCap;
import com.bwd.nms.mediationrepository.PMCustomerRepository;
import com.bwd.nms.mediationrepository.UpdateColdataRepository;
import com.bwd.nms.mediationrepository.InsertColdataRepository;
import com.bwd.nms.mediationrepository.DeleteCustomerRepository;
import com.bwd.nms.service.dto.ManageReservedCapDTO;
import com.bwd.nms.service.dto.DlsCapacityDTO;
import com.bwd.nms.service.dto.ColHeadersDTO;
import com.bwd.nms.service.dto.CapacityRepLabelDTO;
import com.bwd.nms.mediationdomain.UpdateCols;
import com.bwd.nms.mediationdomain.InsertColStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ManageService{

    private final Logger log = LoggerFactory.getLogger(ManageService.class);

    @Autowired
    public ManageReservedCapRepository manageReservedCapRepository;
    @Autowired
    public PMCustomerRepository pmCustomerRepository;
    @Autowired
    public UpdateColdataRepository updateColdataRepository;
    @Autowired
    public InsertColdataRepository insertColdataRepository;
    @Autowired
    public DeleteCustomerRepository deleteCustomerRepository;



    public Flux<ManageReservedCapDTO> findAllData() {
        return manageReservedCapRepository.findAllData();
    }
    public Flux<PMCustomer> findAllCustomer() {
        return pmCustomerRepository.findCustomers();
    }
    public Flux<DlsCapacityDTO> findDlsCapacityData() {
        return manageReservedCapRepository.findDlsCapacityData();
    }
    public Flux<ColHeadersDTO> findColHeadersData() {
        return manageReservedCapRepository.findColHeadersData();
    }
    public Flux<CapacityRepLabelDTO> findCapacityRepLabelData() {return manageReservedCapRepository.findCapacityRepLabelData();}
    public Mono<UpdateCols> update(UpdateCols updateCols) {
        Integer id = updateCols.getId();
        String total = updateCols.getTotal();
        return updateColdataRepository.update(total, id)
            .doOnNext(rowsUpdated -> log.debug("Rows updated: {}", rowsUpdated))
            .map(rowsUpdated -> {
                UpdateCols update = new UpdateCols();
                update.setId(id);
                update.setTotal(total);
                return update;
            });
    }
    public Mono<InsertColStatus> save(InsertColStatus insertColStatus) {
        InsertColStatus insert = new InsertColStatus();
        insert.setCustomer_id(insertColStatus.getCustomer_id());
        insert.setCap_seg_fp_dls_id(insertColStatus.getCap_seg_fp_dls_id());
        insert.setTotal(insertColStatus.getTotal());
        return insertColdataRepository.saveinsert(
                insert.getCustomer_id(),
                insert.getCap_seg_fp_dls_id(),
                insert.getTotal()
            )
            .map(rowsUpdated -> {
                // Return the InsertColStatus object after "insert"
                return insert;
            });

    }

    public Mono<Void> deleteRecordById(Integer id) {
        return deleteCustomerRepository.deleteByCustomerId(id);
    }

}
