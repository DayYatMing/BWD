
package com.bwd.nms.mcpdomain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


@JsonInclude(Include.NON_NULL)
public class DisplayData  implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("operationState")
    private String operationState;


	@JsonProperty("adminState")
    private String adminState;

	@JsonProperty("displayDeploymentState")
    private String displayDeploymentState;

	@JsonProperty("displayName")
    private String displayName;


	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getOperationState() {
		return operationState;
	}

	public void setOperationState(String operationState) {
		this.operationState = operationState;
	}

	public String getAdminState() {
		return adminState;
	}

	public void setAdminState(String adminState) {
		this.adminState = adminState;
	}

	public String getDisplayDeploymentState() {
		return displayDeploymentState;
	}

	public void setDisplayDeploymentState(String displayDeploymentState) {
		this.displayDeploymentState = displayDeploymentState;
	}



}
