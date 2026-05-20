package com.bwd.nms.service.dto;

public class MCPAlarmsResponse {

    private String alarmId;

    private String id;
    private String nodeId;
    private String raAlarmId;
    private String nodeType;
    private String state;
    private String resource;
    private String resourceId;
    private String nativeConditionType;
    private String conditionSeverity;
    private String serviceAffecting;
    private boolean manualClearable;
    private String additionalText;
    private String firstRaiseTime;
    private String lastRaiseTime;
    private String acknowledgeUpdateTime;
    private String acknowledgeUpdateUser;
    private int numberOfOccurrences;
    private String acknowledgeState;
    private String deviceId;
    private String deviceName;
    private String ipAddress;
    private String macAddress;

    private String sequenceId;
    private boolean isEvent;
    private boolean isClearable;

    private String hawaikiDeviceLabel;

    private String portDetails;

    public String getPortDetails() {
        return portDetails;
    }

    public void setPortDetails(String portDetails) {
        this.portDetails = portDetails;
    }

    public String getHawaikiDeviceLabel() {
        return hawaikiDeviceLabel;
    }

    public void setHawaikiDeviceLabel(String hawaikiDeviceLabel) {
        this.hawaikiDeviceLabel = hawaikiDeviceLabel;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getRaAlarmId() {
        return raAlarmId;
    }

    public void setRaAlarmId(String raAlarmId) {
        this.raAlarmId = raAlarmId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getNativeConditionType() {
        return nativeConditionType;
    }

    public void setNativeConditionType(String nativeConditionType) {
        this.nativeConditionType = nativeConditionType;
    }

    public String getConditionSeverity() {
        return conditionSeverity;
    }

    public void setConditionSeverity(String conditionSeverity) {
        this.conditionSeverity = conditionSeverity;
    }

    public String getServiceAffecting() {
        return serviceAffecting;
    }

    public void setServiceAffecting(String serviceAffecting) {
        this.serviceAffecting = serviceAffecting;
    }

    public boolean isManualClearable() {
        return manualClearable;
    }

    public void setManualClearable(boolean manualClearable) {
        this.manualClearable = manualClearable;
    }

    public String getAdditionalText() {
        return additionalText;
    }

    public void setAdditionalText(String additionalText) {
        this.additionalText = additionalText;
    }

    public String getFirstRaiseTime() {
        return firstRaiseTime;
    }

    public void setFirstRaiseTime(String firstRaiseTime) {
        this.firstRaiseTime = firstRaiseTime;
    }

    public String getLastRaiseTime() {
        return lastRaiseTime;
    }

    public void setLastRaiseTime(String lastRaiseTime) {
        this.lastRaiseTime = lastRaiseTime;
    }

    public String getAcknowledgeUpdateTime() {
        return acknowledgeUpdateTime;
    }

    public void setAcknowledgeUpdateTime(String acknowledgeUpdateTime) {
        this.acknowledgeUpdateTime = acknowledgeUpdateTime;
    }

    public String getAcknowledgeUpdateUser() {
        return acknowledgeUpdateUser;
    }

    public void setAcknowledgeUpdateUser(String acknowledgeUpdateUser) {
        this.acknowledgeUpdateUser = acknowledgeUpdateUser;
    }

    public int getNumberOfOccurrences() {
        return numberOfOccurrences;
    }

    public void setNumberOfOccurrences(int numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
    }

    public String getAcknowledgeState() {
        return acknowledgeState;
    }

    public void setAcknowledgeState(String acknowledgeState) {
        this.acknowledgeState = acknowledgeState;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public boolean isEvent() {
        return isEvent;
    }

    public void setEvent(boolean event) {
        isEvent = event;
    }

    public boolean isClearable() {
        return isClearable;
    }

    public void setClearable(boolean clearable) {
        isClearable = clearable;
    }
}
