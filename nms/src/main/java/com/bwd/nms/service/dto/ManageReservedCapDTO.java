package com.bwd.nms.service.dto;
import org.springframework.data.relational.core.mapping.Column;


public class ManageReservedCapDTO {

    private Integer id;
    @Column("capacityName")
    private String capacityName;
    @Column("segmentName")
    private String segmentName;
    @Column("segmentid")
    private String segmentid;
    @Column("dlsid")
    private String dlsid;
    @Column("capacityid")
    private String capacityid;
    @Column("fiberpairid")
    private String fiberpairid;
    @Column("fiberPair")
    private String fiberPair;
    @Column("dlsName")
    private String dlsName;
    @Column("customerName")
    private String customerName;
    @Column("total")
    private String total;
    @Column("capSegFpDlsId")
    private Integer capSegFpDlsId;

    public ManageReservedCapDTO() {
    }
    public ManageReservedCapDTO(Integer id, String capacityName, String segmentName,
                                String segmentid, String dlsid, String fiberpairid,
                                String capacityid, String fiberPair, String dlsName,
                                String customerName, String total, Integer capSegFpDlsId) {
        this.id = id;
        this.capacityName = capacityName;
        this.segmentName = segmentName;
        this.segmentid = segmentid;
        this.dlsid = dlsid;
        this.fiberpairid = fiberpairid;
        this.capacityid = capacityid;
        this.fiberPair = fiberPair;
        this.dlsName = dlsName;
        this.customerName = customerName;
        this.total = total;
        this.capSegFpDlsId = capSegFpDlsId;
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCapacityName() { return capacityName; }
    public void setCapacityName(String capacityName) { this.capacityName = capacityName; }

    public String getSegmentName() { return segmentName; }
    public void setSegmentName(String segmentName) { this.segmentName = segmentName; }

    public String getSegmentId() { return segmentid; }
    public void setSegmentId(String segmentid) { this.segmentid = segmentid; }

    public String getDlsId() { return dlsid; }
    public void setDlsId(String dlsid) { this.dlsid = dlsid; }

    public String getCapacityId() { return capacityid; }
    public void setCapacityId(String capacityid) { this.capacityid = capacityid; }

    public String getFiberPairId() { return fiberpairid; }
    public void setFiberPairId(String fiberpairid) { this.fiberpairid = fiberpairid; }

    public String getFiberPair() { return fiberPair; }
    public void setFiberPair(String fiberPair) { this.fiberPair = fiberPair; }

    public String getDlsName() { return dlsName; }
    public void setDlsName(String dlsName) { this.dlsName = dlsName; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }

    public Integer getCapSegFpDlsId() { return capSegFpDlsId; }
    public void setCapSegFpDlsId(Integer capSegFpDlsId) { this.capSegFpDlsId = capSegFpDlsId; }
}
