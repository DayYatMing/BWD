package com.bwd.nms.service.mapper;

import com.bwd.nms.mediationdomain.*;
import com.bwd.nms.service.dto.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapacityRepLabelMapper {

    public CapacityRepLabelDTO convertToCapacityRepLabelDTO(CapacityRepLabel entity) {
        if (entity == null) return null;
        return new CapacityRepLabelDTO(entity);
    }

    public ColHeadersDTO convertToColHeadersDTO(ColHeaders entity) {
        if (entity == null) return null;

        return new ColHeadersDTO(
            entity.getTblId(),         // Integer tbl_id
            entity.getCapacityId(),    // String capacity_id
            entity.getCapacityName(),  // String capacity_name
            entity.getDlsname(),        // String dlsname
            entity.getfibrepairname(),  // String fibrepairname
            entity.getSegmentname()     // String segmentname
        );

    }

    public DlsCapacityDTO convertToManageReservedCapDTO(DlsCapacity entity) {
        if (entity == null) return null;

        return new DlsCapacityDTO(
            entity.getCrossTableid(),     // map to crossTableid
            entity.getSfxtblid(),         // map to sfxtblid
            entity.getDlsid(),            // map to dlsid
            entity.getIsreserved(),       // map to isreserved
            entity.getSegment_fp_id(),    // map to segment_fp_id
            entity.getCapacityid(),      // map to capacity_id
            entity.getDlsname(),          // map to dlsname
            entity.getCapacityName()     // map to capacity_name
        );
    }

    public PMCustomerDTO convertToCustomerDTO(ManageCustomer entity) {
        if (entity == null) {
            return null;
        }

        PMCustomerDTO dto = new PMCustomerDTO(); // use no-arg constructor
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        // Optionally set other fields if needed
        // dto.setShortname(entity.getShortname());
        // dto.setActive(entity.getActive());

        return dto;
    }

    // --- Existing List methods (optional for non-reactive usage) ---
    public List<CapacityRepLabelDTO> convertToCapacityReplabelDTOS(List<CapacityRepLabel> capacityRepLabelList) {
        if (capacityRepLabelList == null) return Collections.emptyList();
        return capacityRepLabelList.stream()
            .map(this::convertToCapacityRepLabelDTO)
            .collect(Collectors.toList());
    }

    public List<ColHeadersDTO> convertToColHeadersDTOS(List<ColHeaders> colHeadersList) {
        if (colHeadersList == null) return Collections.emptyList();
        return colHeadersList.stream()
            .map(this::convertToColHeadersDTO)
            .collect(Collectors.toList());
    }

    public List<DlsCapacityDTO> convertToManageReservedCapDTOS(List<DlsCapacity> dlscapacityList) {
        if (dlscapacityList == null) return Collections.emptyList();
        return dlscapacityList.stream()
            .map(this::convertToManageReservedCapDTO)
            .collect(Collectors.toList());
    }

    public List<PMCustomerDTO> convertToCustomerDTOs(List<ManageCustomer> manageCustomerList) {
        if (manageCustomerList == null) return Collections.emptyList();
        return manageCustomerList.stream()
            .map(this::convertToCustomerDTO)
            .collect(Collectors.toList());
    }
}
