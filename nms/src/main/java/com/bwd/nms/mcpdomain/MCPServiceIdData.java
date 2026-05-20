package com.bwd.nms.mcpdomain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCPServiceIdData implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("attributes")
    private MCPServiceIdAttributes attributes;

    public MCPServiceIdAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(MCPServiceIdAttributes attributes) {
        this.attributes = attributes;
    }

}
