package com.bwd.nms.service.mapper;

import com.bwd.nms.mediationdomain.PMCustomer;
import com.bwd.nms.service.dto.PMCustomerDTO;
import org.springframework.stereotype.Service;

@Service
public class PMCustomerMapper {

    public PMCustomer covertToPMCustomer(PMCustomerDTO pmCustomerDTO) {
        PMCustomer pmCustomer = new PMCustomer();
        pmCustomer.setId(pmCustomerDTO.getId());
        pmCustomer.setName(pmCustomerDTO.getName());
        pmCustomer.setShortname(pmCustomerDTO.getShortname());
        pmCustomer.setActive(pmCustomerDTO.getActive());

        return pmCustomer;
    }
}
