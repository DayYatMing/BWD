package com.bwd.nms.mcpdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class AlertsResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("data")
    private Data[] data;

	public Data[] getData() {
		return data;
	}

	public void setData(Data[] data) {
		this.data = data;
	}

}
