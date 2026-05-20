package com.bwd.grafanaboot.persistence.DAO;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tems_och")
public class TemsOCH implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCustomerSid() {
        return customerSid;
    }

    public void setCustomerSid(String customerSid) {
        this.customerSid = customerSid;
    }

    public String getPMSource() {
        return PMSource;
    }

    public void setPMSource(String PMSource) {
        this.PMSource = PMSource;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getqMin() {
        return qMin;
    }

    public void setqMin(String qMin) {
        this.qMin = qMin;
    }

    public String getqMax() {
        return qMax;
    }

    public void setqMax(String qMax) {
        this.qMax = qMax;
    }

    public String getqAvg() {
        return qAvg;
    }

    public void setqAvg(String qAvg) {
        this.qAvg = qAvg;
    }

    public String getqStdDev() {
        return qStdDev;
    }

    public void setqStdDev(String qStdDev) {
        this.qStdDev = qStdDev;
    }

    public String getLinkFailSecIn() {
        return linkFailSecIn;
    }

    public void setLinkFailSecIn(String linkFailSecIn) {
        this.linkFailSecIn = linkFailSecIn;
    }

    public String getLinkFailSecOut() {
        return linkFailSecOut;
    }

    public void setLinkFailSecOut(String linkFailSecOut) {
        this.linkFailSecOut = linkFailSecOut;
    }

    public String getSeverelyErrSecIn() {
        return severelyErrSecIn;
    }

    public void setSeverelyErrSecIn(String severelyErrSecIn) {
        this.severelyErrSecIn = severelyErrSecIn;
    }

    public String getSeverelyErrSecOut() {
        return severelyErrSecOut;
    }

    public void setSeverelyErrSecOut(String severelyErrSecOut) {
        this.severelyErrSecOut = severelyErrSecOut;
    }

    public String getErrSecIn() {
        return errSecIn;
    }

    public void setErrSecIn(String errSecIn) {
        this.errSecIn = errSecIn;
    }

    public String getErrSecOut() {
        return errSecOut;
    }

    public void setErrSecOut(String errSecOut) {
        this.errSecOut = errSecOut;
    }

    public String getPhysicalErrCnt() {
        return physicalErrCnt;
    }

    public void setPhysicalErrCnt(String physicalErrCnt) {
        this.physicalErrCnt = physicalErrCnt;
    }

    public String getPhysicalErrCntOut() {
        return physicalErrCntOut;
    }

    public void setPhysicalErrCntOut(String physicalErrCntOut) {
        this.physicalErrCntOut = physicalErrCntOut;
    }

    public String getFrameChkSeqErrCntIn() {
        return frameChkSeqErrCntIn;
    }

    public void setFrameChkSeqErrCntIn(String frameChkSeqErrCntIn) {
        this.frameChkSeqErrCntIn = frameChkSeqErrCntIn;
    }

    public String getFrameChkSeqErrCntOut() {
        return frameChkSeqErrCntOut;
    }

    public void setFrameChkSeqErrCntOut(String frameChkSeqErrCntOut) {
        this.frameChkSeqErrCntOut = frameChkSeqErrCntOut;
    }

    public String getUnavailSec() {
        return unavailSec;
    }

    public void setUnavailSec(String unavailSec) {
        this.unavailSec = unavailSec;
    }

    public String getNumOfSecInBinTxLineCard() {
        return numOfSecInBinTxLineCard;
    }

    public void setNumOfSecInBinTxLineCard(String numOfSecInBinTxLineCard) {
        this.numOfSecInBinTxLineCard = numOfSecInBinTxLineCard;
    }

    public String getNumOfSecInBinRxLineCard() {
        return numOfSecInBinRxLineCard;
    }

    public void setNumOfSecInBinRxLineCard(String numOfSecInBinRxLineCard) {
        this.numOfSecInBinRxLineCard = numOfSecInBinRxLineCard;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Date/Time")
    private String dateTime;

    @Column(name = "customer_sid")
    private String customerSid;

    @Column(name = "PM Source")
    private String PMSource;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "Type")
    private String type;

    @Column(name = "Q (dB) Minimum")
    private String qMin;

    @Column(name = "Q (dB) Maximum")
    private String qMax;

    @Column(name = "Q (dB) Average")
    private String qAvg;

    @Column(name = "Q (dB) Std Dev")
    private String qStdDev;

    @Column(name = "Link Fail Seconds - In")
    private String linkFailSecIn;

    @Column(name = "Link Fail Seconds - Out")
    private String linkFailSecOut;

    @Column(name = "Severely Errored Seconds - In")
    private String severelyErrSecIn;

    @Column(name = "Severely Errored Seconds - Out")
    private String severelyErrSecOut;

    @Column(name = "Errored Seconds - In")
    private String errSecIn;

    @Column(name = "Errored Seconds - Out")
    private String errSecOut;

    @Column(name = "Physical Error Count - In")
    private String physicalErrCnt;

    @Column(name = "Physical Error Count - Out")
    private String physicalErrCntOut;

    @Column(name = "Frame Check Sequence Error Count - In")
    private String frameChkSeqErrCntIn;

    @Column(name = "Frame Check Sequence Error Count - Out")
    private String frameChkSeqErrCntOut;

    @Column(name = "Unavailable Seconds (UAS)")
    private String unavailSec;

    @Column(name = "Number of Seconds in Bin - Tx Line Card")
    private String numOfSecInBinTxLineCard;

    @Column(name = "Number of Seconds in Bin - Rx Line Card")
    private String numOfSecInBinRxLineCard;
}
