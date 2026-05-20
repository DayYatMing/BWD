
package com.bwd.nms.service.dto;

import java.io.Serializable;

public class AlertsStatusResponse implements Serializable{

	   private static final long serialVersionUID = 1L;

	    private String layerRateQualifier;

	    private String lastUpdatedAdminStateTimeStamp;

	    private String lastUpdatedOperationalStateTimeStamp;

		private String operationState;

		private String adminState;

		private String displayDeploymentState;

		private String serviceName;

		private String customerName;

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

		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public AlertsStatusResponse() {}



}
