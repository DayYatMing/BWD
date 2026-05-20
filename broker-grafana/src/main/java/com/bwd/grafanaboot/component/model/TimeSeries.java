package com.bwd.grafanaboot.component.model;

import java.time.LocalDateTime;

public class TimeSeries {

    public LocalDateTime time;
    public String metric;
    public Double linkFailSecIn;
    public Double linkFailSecOut;
    public Double physicalErrCntIn;
    public Double physicalErrCntOut;
    public Double frameChkSeqErrCntIn;
    public Double frameChkSeqErrCntOut;
    public Double numOfSecInBinTxLineCard;
    public Double numOfSecInBinRxLineCard;
    public Double errSecIn;
    public Double errSecOut;
    public Double severelyErrSecIn;
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


    public Double getErrSecOut() {
        return errSecOut;
    }

    public void setErrSecOut(Double errSecOut) {
        this.errSecOut = errSecOut;
    }

    public Double getErrSecIn() {
        return errSecIn;
    }

    public void setErrSecIn(Double errSecIn) {
        this.errSecIn = errSecIn;
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
