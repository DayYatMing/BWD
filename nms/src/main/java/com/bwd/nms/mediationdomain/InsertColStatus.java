package com.bwd.nms.mediationdomain;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reserved_capacity_calculation")
public class InsertColStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "cap_seg_fp_dls_id")
    private Integer capSegFpDlsId;
    @Column(name = "total")
    private String total;

    public Integer getCustomer_id() {
        return customerId;
    }
    public void setCustomer_id(Integer customerId) {
        this.customerId = customerId;
    }
    public Integer getCap_seg_fp_dls_id() {
        return capSegFpDlsId;
    }
    public void setCap_seg_fp_dls_id(Integer capSegFpDlsId) {
        this.capSegFpDlsId = capSegFpDlsId;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }

}
