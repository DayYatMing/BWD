package com.bwd.nms.service.mapper;

import com.bwd.nms.mediationdomain.PMConfiguration;
import com.bwd.nms.service.dto.PMConfigurationDTO;
import org.springframework.stereotype.Service;

@Service
public class PMConfigurationMapper {

    public PMConfiguration covertToPMConfiguration(PMConfigurationDTO pmConfigurationDTO) {
        PMConfiguration pmConfiguration = new PMConfiguration();
        pmConfiguration.setId(pmConfigurationDTO.getId());
        pmConfiguration.setServiceId(pmConfigurationDTO.getServiceId());
        pmConfiguration.setSourceName(pmConfigurationDTO.getSourceName());
        pmConfiguration.setNodeId(pmConfigurationDTO.getNodeId());
        pmConfiguration.setVendor(pmConfigurationDTO.getVendor());
        pmConfiguration.setLabelName(pmConfigurationDTO.getLabelName());
        pmConfiguration.setRouteDirection(pmConfigurationDTO.getRouteDirection());
        pmConfiguration.setSegmentDirection(pmConfigurationDTO.getSegmentDirection());
        pmConfiguration.setDisableCollection(pmConfigurationDTO.getDisableCollection());
        pmConfiguration.setVisibleToCustomer(pmConfigurationDTO.getVisibleToCustomer());
        pmConfiguration.setFrequency(pmConfigurationDTO.getFrequency());

        return pmConfiguration;
    }
}
