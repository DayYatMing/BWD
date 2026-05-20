package com.bwd.nms.service.mapper;

import com.bwd.nms.mediationdomain.PMSource;
import com.bwd.nms.service.dto.PMSourceDTO;
import org.springframework.stereotype.Service;

@Service
public class PMSourceMapper {

    public PMSource covertToPMSource(PMSourceDTO pmSourceDTO) {
        PMSource pmSource = new PMSource();
        pmSource.setId(pmSourceDTO.getId());
        pmSource.setCustomer(pmSourceDTO.getCustomer());
        pmSource.setCustomerSid(pmSourceDTO.getCustomerSid());
        pmSource.setPmSource(pmSourceDTO.getPmSource());
        pmSource.setVisibleToCustomer(pmSourceDTO.getVisibleToCustomer());
        pmSource.setRouteEnd(pmSourceDTO.getRouteEnd());
        pmSource.setSegmentEnd(pmSourceDTO.getSegmentEnd());
        pmSource.setSource(pmSourceDTO.getSource());
        pmSource.setNodeId(pmSourceDTO.getNodeId());
        pmSource.setRoute(pmSourceDTO.getRoute());
        pmSource.setSegment(pmSourceDTO.getSegment());

        return pmSource;
    }
}
