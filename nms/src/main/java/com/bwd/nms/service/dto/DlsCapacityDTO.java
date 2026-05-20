package com.bwd.nms.service.dto;
import org.springframework.data.relational.core.mapping.Column;

public class DlsCapacityDTO {
    @Column("crossTableid")
    private Integer crossTableid;
    @Column("sfxtblid")
    private Integer sfxtblid;
    @Column("dlsid")
    private Integer dlsid;
    @Column("isreserved")
    private Integer isreserved;
    @Column("segment_fp_id")
    private Integer segment_fp_id;
    @Column("capacity_id")
    private Integer capacity_id;
    @Column("dlsname")
    private String dlsname;
    @Column("capacity_name")
    private String capacity_name;

    public DlsCapacityDTO() {}

    public DlsCapacityDTO(Integer crossTableid, Integer sfxtblid, Integer dlsid, Integer isreserved,
                          Integer segment_fp_id, Integer capacity_id, String dlsname, String capacity_name) {
        this.crossTableid = crossTableid;
        this.sfxtblid = sfxtblid;
        this.dlsid = dlsid;
        this.isreserved = isreserved;
        this.segment_fp_id = segment_fp_id;
        this.capacity_id = capacity_id;
        this.dlsname = dlsname;
        this.capacity_name = capacity_name;
    }

    public Integer getCrossTableId() { return crossTableid; }
    public void setCrossTableId(Integer crossTableid) { this.crossTableid = crossTableid; }

    public Integer getSfxTblId() { return sfxtblid; }
    public void setSfxTblId(Integer sfxtblid) { this.sfxtblid = sfxtblid; }

    public Integer getDlsId() { return dlsid; }
    public void setDlsId(Integer dlsid) { this.dlsid = dlsid; }

    public Integer getIsReserved() { return isreserved; }
    public void setIsReserved(Integer isreserved) { this.isreserved = isreserved; }

    public Integer getSegmentFpId() { return segment_fp_id; }
    public void setSegmentFpId(Integer segment_fp_id) { this.segment_fp_id = segment_fp_id; }

    public Integer getCapacityId() { return capacity_id; }
    public void setCapacityId(Integer capacity_id) { this.capacity_id = capacity_id; }

    public String getDlsName() { return dlsname; }
    public void setDlsName(String dlsname) { this.dlsname = dlsname; }

    public String getCapacityName() { return capacity_name; }
    public void setCapacityName(String capacity_name) { this.capacity_name = capacity_name; }
}
