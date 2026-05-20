package com.bwd.nms.mediationdomain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "capacity_segment_fp_dls_xrf ")

  public class CapacityRepLabel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;
    @Column(name= "segment_id")
    private Integer segmentId;
    @Column(name= "fibre_pair_id")
    private Integer fibrePairId;
    @Column(name= "segment_name")
    private String segmentName;
    @Column(name= "fiber_pair_name")
    private String fiberPairName;
    @Column(name= "dlsname")
    private String dlsName;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSegmentId() {
        return segmentId;
    }
    public void setSegmentId(Integer segmentId) {
        this.segmentId = segmentId;
    }
    public Integer getFibrePairId() {
        return fibrePairId;
    }
    public void setFibrePairId(Integer fibrePairId) {
        this.fibrePairId = fibrePairId;
    }
    public String getSegmentName() {
        return segmentName;
    }
    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }
    public String getFiberPairName() {return fiberPairName;}
    public void setFiberPairName(String fiberPairName) {this.fiberPairName = fiberPairName;}
    public String getDlsName() {return dlsName;}
    public void setDlsName(String dlsName) {this.dlsName = dlsName;}
}
