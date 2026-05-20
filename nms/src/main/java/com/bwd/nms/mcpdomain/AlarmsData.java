package com.bwd.nms.mcpdomain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class AlarmsData implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("attributes")
    private AlarmAttributes attributes;
    public AlarmAttributes getAttributes() {
        return attributes;
    }
    public void setAttributes(AlarmAttributes attributes) {
        this.attributes = attributes;
    }
}
