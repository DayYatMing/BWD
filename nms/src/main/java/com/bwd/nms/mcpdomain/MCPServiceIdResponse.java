package com.bwd.nms.mcpdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MCPServiceIdResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("data")
    private MCPServiceIdData[] data;

    public MCPServiceIdData[] getData() {
        return data;
    }

    public void setData(MCPServiceIdData[] data) {
        this.data = data;
    }

}
