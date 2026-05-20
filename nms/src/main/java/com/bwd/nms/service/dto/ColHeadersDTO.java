package com.bwd.nms.service.dto;
import org.springframework.data.relational.core.mapping.Column;

public class ColHeadersDTO {

    @Column("tbl_id")
    private Integer tbl_id;
    @Column("capacity_id")
    private String capacity_id;
    @Column("capacity_name")
    private String capacity_name;
    @Column("dlsname")
    private String dlsname;
    @Column("fibrepairname")
    private String fibrepairname;
    @Column("segmentname")
    private String segmentname;

    public ColHeadersDTO(Integer tbl_id,String capacity_id, String capacity_name,  String dlsname, String fibrepairname, String segmentname ) {
            this.tbl_id = tbl_id;
            this.capacity_id = capacity_id;
            this.capacity_name = capacity_name;
            this.dlsname = dlsname;
            this.fibrepairname = fibrepairname;
            this.segmentname = segmentname;
    }


    public Integer getTblId() { return tbl_id; }
    public void setTblId(Integer tbl_id) { this.tbl_id = tbl_id; }

    public String getCapacityId() { return capacity_id; }
    public void setCapacityId(String capacity_id) { this.capacity_id = capacity_id; }

    public String getCapacityName() { return capacity_name; }
    public void setCapacityName(String capacity_name) { this.capacity_name = capacity_name; }

    public String getDlsname() { return dlsname; }
    public void setDlsname(String dlsname) { this.dlsname = dlsname; }

    public String getfibrepairname() { return fibrepairname; }
    public void setfibrepairname(String fibrepairname) { this.fibrepairname = fibrepairname; }

    public String getSegmentName() { return segmentname; }
    public void setSegmentName(String segmentname) { this.segmentname = segmentname; }


}
