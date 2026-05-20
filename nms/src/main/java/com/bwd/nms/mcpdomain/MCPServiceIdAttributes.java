package com.bwd.nms.mcpdomain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCPServiceIdAttributes implements Serializable {


    private static final long serialVersionUID = 1L;

    @JsonProperty("displayData")
    private MCPServiceIdName displayData;

    public MCPServiceIdName getDisplayData() {
        return displayData;
    }

    public void setDisplayData(MCPServiceIdName displayData) {
        this.displayData = displayData;
    }
}
