package com.bwd.nms.mcpdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AlarmsResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("data")
    private AlarmsData[] data;

    public AlarmsData[] getData() {
        return data;
    }

    public void setData(AlarmsData[] data) {
        this.data = data;
    }

}
