package com.bwd.nms.mediationdomain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "capacity_segment_fp_dls_xrf ")
public class DlsCapacity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "crossTableid")
    private Integer crossTableId;
    @Column(name= "sfxtblid")
    private Integer sfxTblId;
    @Column(name= "dlsid")
    private Integer dlsId;
    @Column(name= "isreserved")
    private Integer isReserved;
    @Column(name= "segment_fp_id")
    private Integer segmentFpId;
    @Column(name= "capacity_id")
    private Integer capacityId;
    @Column(name= "dlsname")
    private String dlsName;
    @Column(name= "capacity_name")
    private String capacityName;

    public Integer getSfxtblid() {
        return sfxTblId;
    }
    public void setSfxtblid(Integer sfxTblId) {
        this.sfxTblId = sfxTblId;
    }
    public Integer getDlsid() {
        return dlsId;
    }
    public void setDlsid(Integer dlsid) {
        this.dlsId = dlsId;
    }
    public Integer getIsreserved() {
        return isReserved;
    }
    public void setIsreserved(Integer isReserved) {
        this.isReserved = isReserved;
    }
    public Integer getSegment_fp_id() {
        return segmentFpId;
    }
    public void setSegment_fp_id(Integer segmentFpId) {
        this.segmentFpId = segmentFpId;
    }
    public Integer getCrossTableid() {
        return crossTableId;
    }
    public void setCrossTableid(Integer crossTableId) {
        this.crossTableId = crossTableId;
    }
    public Integer getCapacityid() {
        return capacityId;
    }
    public void setCapacityid(Integer capacityId) {
        this.capacityId = capacityId;
    }
    public String getDlsname() {
        return dlsName;
    }
    public void setDlsname(String dlsName) {
        this.dlsName = dlsName;
    }
    public String getCapacityName() {
        return capacityName;
    }
    public void setCapacityName(String capacityName) {
        this.capacityName = capacityName;
    }
}
