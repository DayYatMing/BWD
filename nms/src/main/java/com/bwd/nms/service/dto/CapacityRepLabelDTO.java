package com.bwd.nms.service.dto;

import com.bwd.nms.mediationdomain.CapacityRepLabel;

public class CapacityRepLabelDTO {

    private Integer id;
    private String segmentId;
    private String fibrePairId;
    private String segmentName;
    private String fiberPairName;
    private String dlsname;

    // Default constructor (needed by Jackson/Spring)
    public CapacityRepLabelDTO() {
    }

    // Constructor with all fields (used in mapper)
    public CapacityRepLabelDTO(Integer id, String segmentId, String fibrePairId,
                               String segmentName, String fiberPairName, String dlsname) {
        this.id = id;
        this.segmentId = segmentId;
        this.fibrePairId = fibrePairId;
        this.segmentName = segmentName;
        this.fiberPairName = fiberPairName;
        this.dlsname = dlsname;
    }

    // Optional: convenience constructor to map from entity
    public CapacityRepLabelDTO(CapacityRepLabel entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.segmentId = entity.getSegmentId() != null ? entity.getSegmentId().toString() : null;
            this.fibrePairId = entity.getFibrePairId() != null ? entity.getFibrePairId().toString() : null;
            this.segmentName = entity.getSegmentName();
            this.fiberPairName = entity.getFiberPairName();
            this.dlsname = entity.getDlsName();
        }
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSegmentId() { return segmentId; }
    public void setSegmentId(String segmentId) { this.segmentId = segmentId; }

    public String getFibrePairId() { return fibrePairId; }
    public void setFibrePairId(String fibrePairId) { this.fibrePairId = fibrePairId; }

    public String getSegmentName() { return segmentName; }
    public void setSegmentName(String segmentName) { this.segmentName = segmentName; }

    public String getFiberPairName() { return fiberPairName; }
    public void setFiberPairName(String fiberPairName) { this.fiberPairName = fiberPairName; }

    public String getDlsName() { return dlsname; }
    public void setDlsName(String dlsname) { this.dlsname = dlsname; }
}
