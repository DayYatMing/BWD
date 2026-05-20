package com.bwd.nms.service;

import com.bwd.nms.mediationdomain.*;
import com.bwd.nms.mediationrepository.*;
import com.bwd.nms.service.dto.*;
import com.bwd.nms.service.mapper.CapacityRepLabelMapper;
import com.bwd.nms.service.mapper.ManageReservedCapMapper;
import com.bwd.nms.service.mapper.PMCustomerMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.util.List;


@Service
public class CapacityRepLabelService {

    private final CapacityRepLabelRepository capacityRepLabelRepository;
    private final ColHeadersRepository colHeadersRepository;
    private final DlsCapacityRepository dlsCapacityRepository;
    private final PMCustomerRepository manageCustomerRepository;
    private final CapacityRepLabelMapper capacityRepLabelMapper;
    private final ManageReservedCapMapper manageReservedCapMapper;
    private final ManageReservedCapRepository manageReservedCapRepository;
    private final PMCustomerMapper pmCustomerMapper;

    public CapacityRepLabelService(CapacityRepLabelRepository capacityRepLabelRepository,
                                   ColHeadersRepository colHeadersRepository,
                                   DlsCapacityRepository dlsCapacityRepository,
                                   PMCustomerRepository manageCustomerRepository,
                                   ManageReservedCapMapper manageReservedCapMapper,
                                   ManageReservedCapRepository manageReservedCapRepository,
                                   CapacityRepLabelMapper capacityRepLabelMapper, PMCustomerMapper pmCustomerMapper) {
        this.capacityRepLabelRepository = capacityRepLabelRepository;
        this.colHeadersRepository = colHeadersRepository;
        this.dlsCapacityRepository = dlsCapacityRepository;
        this.capacityRepLabelMapper = capacityRepLabelMapper;
        this.manageCustomerRepository = manageCustomerRepository;
        this.manageReservedCapMapper = manageReservedCapMapper;
        this.manageReservedCapRepository = manageReservedCapRepository;
        this.pmCustomerMapper = pmCustomerMapper;
    }

    // Reactive Flux versions

    public Flux<CapacityRepLabelDTO> getCapacityRepLabel() {
        return capacityRepLabelRepository.findCapacityRepLabelData()
            .map(capacityRepLabelMapper::convertToCapacityRepLabelDTO);
    }

    public Flux<ColHeadersDTO> getColHeaders() {
        return colHeadersRepository.findColHeadersData()
            .map(capacityRepLabelMapper::convertToColHeadersDTO);
    }

    public Flux<DlsCapacityDTO> getDlsCapacity() {
        return dlsCapacityRepository.findDlsCapacityData()
            .map(capacityRepLabelMapper::convertToManageReservedCapDTO);
    }

    public Flux<PMCustomerDTO> getAllCustomer() {
        return manageCustomerRepository.findCustomers()
            .map(pmCustomer -> new PMCustomerDTO(pmCustomer));
    }
    public Flux<ManageReservedCapDTO> getManageReservedCapDTOs() {
        return manageReservedCapRepository.findAllData(); // already Flux<ManageReservedCapDTO>
    }
}
