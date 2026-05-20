package com.bwd.nms.mediationdomain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estimated_fp_capacity ")

  public class EstimatedFpCapacity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;
    @Column(name= "au_nz_fp1_dls16")
    private String au_nz_fp1_dls16;
    @Column(name= "nz_hi_fp1_dls02")
    private String nz_hi_fp1_dls02;
    @Column(name= "nz_hi_fp1_dls13")
    private String nz_hi_fp1_dls13;
    @Column(name= "nz_hi_fp1_dls11")
    private String nz_hi_fp1_dls11;
    @Column(name= "au_hi_fp1_dls14")
    private String au_hi_fp1_dls14;

    @Column(name= "au_hi_fp2_dls54")
    private String au_hi_fp2_dls54;
    @Column(name= "hi_uf_fp1_dls27")
    private String hi_uf_fp1_dls27;
    @Column(name= "hi_uf_fp1_dls67")
    private String hi_uf_fp1_dls67;
    @Column(name= "hi_uf_fp2_dls57")
    private String hi_uf_fp2_dls57;
    @Column(name= "hi_uf_fp3_dls")
    private String hi_uf_fp3_dls;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getau_nz_fp1_dls16() {
        return au_nz_fp1_dls16;
    }
    public void setau_nz_fp1_dls16(String au_nz_fp1_dls16) {
        this.au_nz_fp1_dls16 = au_nz_fp1_dls16;
    }

    public String getnz_hi_fp1_dls02() {
        return nz_hi_fp1_dls02;
    }
    public void setnz_hi_fp1_dls02(String nz_hi_fp1_dls02) {
        this.nz_hi_fp1_dls02 = nz_hi_fp1_dls02;
    }

    public String getnz_hi_fp1_dls13() {
        return nz_hi_fp1_dls13;
    }
    public void setnz_hi_fp1_dls13(String nz_hi_fp1_dls13) {
        this.nz_hi_fp1_dls13 = nz_hi_fp1_dls13;
    }

    public String getnz_hi_fp1_dls11() {
        return nz_hi_fp1_dls11;
    }
    public void setnz_hi_fp1_dls11(String nz_hi_fp1_dls11) {
        this.nz_hi_fp1_dls11 = nz_hi_fp1_dls11;
    }

    public String getau_hi_fp1_dls14() {
        return au_hi_fp1_dls14;
    }
    public void setau_hi_fp1_dls14(String au_hi_fp1_dls14) {
        this.au_hi_fp1_dls14 = au_hi_fp1_dls14;
    }

    public String getau_hi_fp2_dls54() {
        return au_hi_fp2_dls54;
    }
    public void setau_hi_fp2_dls54(String au_hi_fp2_dls54) {
        this.au_hi_fp2_dls54 = au_hi_fp2_dls54;
    }

    public String gethi_uf_fp1_dls27() {
        return hi_uf_fp1_dls27;
    }
    public void sethi_uf_fp1_dls27(String hi_uf_fp1_dls27) {
        this.hi_uf_fp1_dls27 = hi_uf_fp1_dls27;
    }

    public String gethi_uf_fp1_dls67() {
        return hi_uf_fp1_dls67;
    }
    public void sethi_uf_fp1_dls67(String hi_uf_fp1_dls67) {
        this.hi_uf_fp1_dls67 = hi_uf_fp1_dls67;
    }
    public String gethi_uf_fp2_dls57() {
        return hi_uf_fp2_dls57;
    }
    public void sethi_uf_fp2_dls57(String hi_uf_fp2_dls57) {
        this.hi_uf_fp2_dls57 = hi_uf_fp2_dls57;
    }

    public String gethi_uf_fp3_dls() {
        return hi_uf_fp3_dls;
    }
    public void sethi_uf_fp3_dls(String hi_uf_fp3_dls) {
        this.hi_uf_fp3_dls = hi_uf_fp3_dls;
    }

    @Override
    public String toString() {
        return "EstimatedFpCapacity{" +
            "id=" + id +
            ", au_nz_fp1_dls16='" + au_nz_fp1_dls16 + '\'' +
            ", nz_hi_fp1_dls02='" + nz_hi_fp1_dls02 + '\'' +
            ", nz_hi_fp1_dls13='" + nz_hi_fp1_dls13 + '\'' +
            ", nz_hi_fp1_dls11='" + nz_hi_fp1_dls11 + '\'' +
            ", au_hi_fp1_dls14='" + au_hi_fp1_dls14 + '\'' +
            ", au_hi_fp2_dls54='" + au_hi_fp2_dls54 + '\'' +
            ", hi_uf_fp1_dls27='" + hi_uf_fp1_dls27 + '\'' +
            ", hi_uf_fp1_dls67='" + hi_uf_fp1_dls67 + '\'' +
            ", hi_uf_fp2_dls57='" + hi_uf_fp2_dls57 + '\'' +
            ", hi_uf_fp3_dls='" + hi_uf_fp3_dls + '\'' +
            '}';
    }
}
