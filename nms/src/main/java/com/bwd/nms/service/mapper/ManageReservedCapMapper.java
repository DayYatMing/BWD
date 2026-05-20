package com.bwd.nms.service.mapper;

import com.bwd.nms.mediationdomain.ManageReservedCap;
import com.bwd.nms.service.dto.ManageReservedCapDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageReservedCapMapper {

    /**
     * Convert a single ManageReservedCap entity to DTO
     */
    public ManageReservedCapDTO convertToManageReservedCapDTO(ManageReservedCap cap) {
        if (cap == null) return null;

        ManageReservedCapDTO dto = new ManageReservedCapDTO();
        dto.setId(cap.getId());
        dto.setTotal(cap.getTotal());
        dto.setCapSegFpDlsId(cap.getCapSegFpDlsId());
        // Other fields in DTO like capacityName, segmentName etc. can remain null
        // or be set elsewhere if needed
        return dto;
    }

    /**
     * Convert a list of ManageReservedCap entities to a list of DTOs
     */
    public List<ManageReservedCapDTO> convertToManageReservedCapDTO(List<ManageReservedCap> list) {
        if (list == null) return Collections.emptyList();
        return list.stream()
            .map(this::convertToManageReservedCapDTO)
            .collect(Collectors.toList());
    }
}
