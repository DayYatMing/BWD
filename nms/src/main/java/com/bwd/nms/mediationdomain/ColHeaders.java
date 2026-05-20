package com.bwd.nms.mediationdomain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "capacity_segment_fp_dls_xrf ")
public class ColHeaders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "tbl_id")
    private Integer tblId;
    @Column(name = "capacity_id")
    private String capacity_id;
    @Column(name= "capacity_name")
    private String capacityName;
    @Column(name= "dlsname")
    private String dlsName;
    @Column(name= "fibrepairname")
    private String fibrePairName;
    @Column(name= "segmentname")
    private String segmentName;
    public Integer getTblId() {
        return tblId;
    }
    public void setTblId(Integer tblId) {
        this.tblId = tblId;
    }
    public String getCapacityName() {
        return capacityName;
    }
    public void setCapacityName(String capacityName) {
        this.capacityName = capacityName;
    }
    public String getDlsname() {return dlsName;}
    public void setDlsname(String dlsName) {this.dlsName = dlsName;}
    public String getfibrepairname() {return fibrePairName;}
    public void setFibrepairname(String fibrePairName) {this.fibrePairName = fibrePairName;}
    public String getSegmentname() {return segmentName;}
    public void setSegmentname(String segmentName) {this.segmentName = segmentName;}
    public String getCapacityId() {
        return capacity_id;
    }
    public void setCapacityId(String capacity_id) {
        this.capacity_id = capacity_id;
    }


}
