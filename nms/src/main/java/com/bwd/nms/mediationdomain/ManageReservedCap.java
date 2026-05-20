package com.bwd.nms.mediationdomain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.io.Serializable;

@Table(name = "reserved_capacity_calculation")
public class ManageReservedCap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column("id")
    private Integer id;
    @Column("customer_id")
    private Integer customerId;
    @Column("total")
    private String total;
    @Column("cap_seg_fp_dls_id")
    private Integer capSegFpDlsId;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCustomerId() {return customerId;}
    public void setCustomerId(Integer customerId) {this.customerId = customerId;}
    public String getTotal() {return total;}
    public void setTotal(String total) {this.total = total;}
    public Integer getCapSegFpDlsId() {return capSegFpDlsId;}
    public void setCapSegFpDlsId(Integer capSegFpDlsId) {this.capSegFpDlsId = capSegFpDlsId;}
}
