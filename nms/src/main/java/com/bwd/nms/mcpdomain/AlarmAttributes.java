package com.bwd.nms.mcpdomain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlarmAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("alarm-id")
    private String alarmid;

    @JsonProperty("node-id")
    private String nodeId;
    @JsonProperty("ra-alarm-id")
    private String raAlarmId;

    @JsonProperty("node-type")
    private String nodeType;
    @JsonProperty("state")
    private String state;
    @JsonProperty("resource")
    private String resource;
    @JsonProperty("resource-id")
    private String resourceId;
    @JsonProperty("native-condition-type")
    private String nativeConditionType;
    @JsonProperty("condition-severity")
    private String conditionSeverity;
    @JsonProperty("service-affecting")
    private String serviceAffecting;
    @JsonProperty("manual-clearable")
    private boolean manualClearable;
    @JsonProperty("additional-text")
    private String additionalText;
    @JsonProperty("first-raise-time")
    private String firstRaiseTime;
    @JsonProperty("last-raise-time")
    private String lastRaiseTime;
    @JsonProperty("acknowledge-update-time")
    private String acknowledgeUpdateTime;
    @JsonProperty("acknowledge-update-user")
    private String acknowledgeUpdateUser;
    @JsonProperty("number-of-occurrences")
    private int numberOfOccurrences;
    @JsonProperty("acknowledge-state")
    private String acknowledgeState;
    @JsonProperty("device-id")
    private String deviceId;
    @JsonProperty("device-name")
    private String deviceName;
    @JsonProperty("ip-address")
    private String ipAddress;
    @JsonProperty("mac-address")
    private String macAddress;

    @JsonProperty("sequence-id")
    private String sequenceId;
    @JsonProperty("is-event")
    private boolean isEvent;
    @JsonProperty("is-clearable")
    private boolean isClearable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(String alarmid) {
        this.alarmid = alarmid;
    }

    public String getNodeId() {
        return nodeId;
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
