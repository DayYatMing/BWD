package com.bwd.nms.service.mapper;

import com.bwd.nms.mediationdomain.PMService;
import com.bwd.nms.service.dto.PMServiceDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PMServiceMapper {

    public List<PMServiceDTO> convertToPMServiceDTOS(List<PMService> pmService) {
        return pmService.stream().map(PMServiceDTO::new).collect(Collectors.toList());
    }

    public PMService covertToPMService(PMServiceDTO pmServiceDTO) {
        PMService pmService = new PMService();
        pmService.setId(pmServiceDTO.getId());
        pmService.setServiceId(pmServiceDTO.getServiceId());
        pmService.setCustomerId(pmServiceDTO.getCustomerId());
        pmService.setStartDate(pmServiceDTO.getStartDate());
        pmService.setEndDate(pmServiceDTO.getEndDate());
        pmService.setBandwidth(pmServiceDTO.getBandwidth());
        pmService.setActive(pmServiceDTO.getActive());
        pmService.setVisibleToCustomer(pmServiceDTO.getVisibleToCustomer());
        pmService.setMasked(pmServiceDTO.getMasked());

        return pmService;
    }
}
