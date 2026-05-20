
package com.bwd.nms.mcpdomain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


@JsonInclude(Include.NON_NULL)
public class Attributes implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("displayData")
    private DisplayData displayData;

	@JsonProperty("layerRateQualifier")
    private String layerRateQualifier;

	@JsonProperty("lastUpdatedAdminStateTimeStamp")
    private String lastUpdatedAdminStateTimeStamp;

	@JsonProperty("lastUpdatedOperationalStateTimeStamp")
    private String lastUpdatedOperationalStateTimeStamp;

	public DisplayData getDisplayData() {
		return displayData;
	}

	public void setDisplayData(DisplayData displayData) {
		this.displayData = displayData;
	}

	public String getLayerRateQualifier() {
		return layerRateQualifier;
	}

	public void setLayerRateQualifier(String layerRateQualifier) {
		this.layerRateQualifier = layerRateQualifier;
	}

	public String getLastUpdatedAdminStateTimeStamp() {
		return lastUpdatedAdminStateTimeStamp;
	}

	public void setLastUpdatedAdminStateTimeStamp(String lastUpdatedAdminStateTimeStamp) {
		this.lastUpdatedAdminStateTimeStamp = lastUpdatedAdminStateTimeStamp;
	}

	public String getLastUpdatedOperationalStateTimeStamp() {
		return lastUpdatedOperationalStateTimeStamp;
	}

	public void setLastUpdatedOperationalStateTimeStamp(String lastUpdatedOperationalStateTimeStamp) {
		this.lastUpdatedOperationalStateTimeStamp = lastUpdatedOperationalStateTimeStamp;
	}



}
