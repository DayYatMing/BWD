package com.bwd.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(
    value = {
        //        "Changed",
        //        "ChangeBy",
    }
)
@JsonInclude(Include.NON_NULL)
public class Stats implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("time")
    private LocalDateTime time;

    @JsonProperty("metric")
    public String metric;

    @JsonProperty("linkFailSecIn")
    public Double linkFailSecIn;

    @JsonProperty("linkFailSecOut")
    public Double linkFailSecOut;

    @JsonProperty("physicalErrCntIn")
    public Double physicalErrCntIn;

    @JsonProperty("physicalErrCntOut")
    public Double physicalErrCntOut;

    @JsonProperty("frameChkSeqErrCntIn")
    public Double frameChkSeqErrCntIn;

    @JsonProperty("frameChkSeqErrCntOut")
    public Double frameChkSeqErrCntOut;

    @JsonProperty("numOfSecInBinTxLineCard")
    public Double numOfSecInBinTxLineCard;

    @JsonProperty("numOfSecInBinRxLineCard")
    public Double numOfSecInBinRxLineCard;

    @JsonProperty("errSecIn")
    public Double errSecIn;

    @JsonProperty("errSecOut")
    public Double errSecOut;

    @JsonProperty("severelyErrSecIn")
    public Double severelyErrSecIn;

    @JsonProperty("severelyErrSecOut")
    public Double severelyErrSecOut;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Double getLinkFailSecIn() {
        return linkFailSecIn;
    }

    public void setLinkFailSecIn(Double linkFailSecIn) {
        this.linkFailSecIn = linkFailSecIn;
    }

    public Double getLinkFailSecOut() {
        return linkFailSecOut;
    }

    public void setLinkFailSecOut(Double linkFailSecOut) {
        this.linkFailSecOut = linkFailSecOut;
    }

    public Double getPhysicalErrCntIn() {
        return physicalErrCntIn;
    }

    public void setPhysicalErrCntIn(Double physicalErrCntIn) {
        this.physicalErrCntIn = physicalErrCntIn;
    }

    public Double getPhysicalErrCntOut() {
        return physicalErrCntOut;
    }

    public void setPhysicalErrCntOut(Double physicalErrCntOut) {
        this.physicalErrCntOut = physicalErrCntOut;
    }

    public Double getFrameChkSeqErrCntIn() {
        return frameChkSeqErrCntIn;
    }

    public void setFrameChkSeqErrCntIn(Double frameChkSeqErrCntIn) {
        this.frameChkSeqErrCntIn = frameChkSeqErrCntIn;
    }

    public Double getFrameChkSeqErrCntOut() {
        return frameChkSeqErrCntOut;
    }

    public void setFrameChkSeqErrCntOut(Double frameChkSeqErrCntOut) {
        this.frameChkSeqErrCntOut = frameChkSeqErrCntOut;
    }

    public Double getNumOfSecInBinTxLineCard() {
        return numOfSecInBinTxLineCard;
    }

    public void setNumOfSecInBinTxLineCard(Double numOfSecInBinTxLineCard) {
        this.numOfSecInBinTxLineCard = numOfSecInBinTxLineCard;
    }

    public Double getNumOfSecInBinRxLineCard() {
        return numOfSecInBinRxLineCard;
    }

    public void setNumOfSecInBinRxLineCard(Double numOfSecInBinRxLineCard) {
        this.numOfSecInBinRxLineCard = numOfSecInBinRxLineCard;
    }

    public Double getErrSecIn() {
        return errSecIn;
    }

    public void setErrSecIn(Double errSecIn) {
        this.errSecIn = errSecIn;
    }

    public Double getErrSecOut() {
        return errSecOut;
    }

    public void setErrSecOut(Double errSecOut) {
        this.errSecOut = errSecOut;
    }

    public Double getSeverelyErrSecIn() {
        return severelyErrSecIn;
    }

    public void setSeverelyErrSecIn(Double severelyErrSecIn) {
        this.severelyErrSecIn = severelyErrSecIn;
    }

    public Double getSeverelyErrSecOut() {
        return severelyErrSecOut;
    }

    public void setSeverelyErrSecOut(Double severelyErrSecOut) {
        this.severelyErrSecOut = severelyErrSecOut;
    }
}
