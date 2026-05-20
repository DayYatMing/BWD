package com.bwd.nms.service;

import com.bwd.nms.mcpdomain.AlarmsResponse;
import com.bwd.nms.mcpdomain.AlertsResponse;
import com.bwd.nms.mcpdomain.MCPServiceIdResponse;
import com.bwd.nms.service.dto.AlertsStatusResponse;
import com.bwd.nms.service.dto.MCPAlarmsResponse;
import com.bwd.nms.service.dto.TokenRequest;
import com.bwd.nms.service.dto.TokenResponse;
import com.bwd.nms.web.util.ExternalHttpUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


@Service
public class ExternalService {

    private final Logger log = LoggerFactory.getLogger(ExternalService.class);

    final String SERVICE_STATUS_URL = "/nsi/api/v2/search/fres?include=expectations,tpes,networkConstructs&limit=10000&offset=0&searchFields=data.attributes.displayData.displayName,data.attributes.mgmtName,data.attributes.userLabel,data.attributes.nativeName,data.attributes.serviceClass,data.attributes.displayData.operationState,data.attributes.layerRate,data.attributes.layerRateQualifier,data.attributes.note,data.attributes.tpeLocations,data.attributes.neNames,data.attributes.displayData.adminState,data.attributes.displayData.displayDeploymentState,data.attributes.resilienceLevel,data.attributes.displayData.displayTopologySource,data.attributes.domainTypes,data.attributes.customerName,data.attributes.utilizationData.totalCapacity,data.attributes.utilizationData.utilizationPercent,data.attributes.displayData.displayPhotonicSpectrumData.frequency,data.attributes.displayData.displayPhotonicSpectrumData.channel,data.attributes.displayData.displayPhotonicSpectrumData.wavelength,data.attributes.lqsData.margin.minMargin,data.attributes.displayData.sncgUserlabel,data.attributes.displayData.displayResiliencyControllerData.recoverCharacteristics_onHome,data.attributes.description,data.attributes.tags&searchText=&serviceClass=Transport%20Client&sortBy=name";

    final String ALARMS_STATUS_RUL = "/nsa/api/v2_0/alarms/filter/filteredAlarms?filter%5Bstate%5D%5B%5D=ACTIVE&filter%5BcontextState%5D%5B%5D=ACTIVE&filter%5Bseverity%5D%5B%5D=INDETERMINATE&filter%5Bseverity%5D%5B%5D=CRITICAL&filter%5Bseverity%5D%5B%5D=MAJOR&filter%5Bseverity%5D%5B%5D=MINOR&filter%5Bseverity%5D%5B%5D=WARNING&sort%5B%5D=-first-raise-time";

    private final ObjectMapper mapper = new ObjectMapper();

    private String authorizationHeader = null;

    @Autowired
    Environment env;

    public List<AlertsStatusResponse> sendAndProcessEvents() {
        String response = sendRequest(SERVICE_STATUS_URL);
        if (response.contains("error")) {
            authorizationHeader = null;
            response = sendRequest(SERVICE_STATUS_URL);
        }
        return processResponse(response);

    }

    public List<MCPAlarmsResponse> sendAndProcessAlarms() {
        String response = sendRequest(ALARMS_STATUS_RUL);
        if (response.contains("error")) {
            authorizationHeader = null;
            response = sendRequest(ALARMS_STATUS_RUL);
        }
        return processAlarmsResponse(response);

    }

    private String createBearer() {
        String token = "";
        String tokenUrl = env.getProperty("mcp.url")+"/tron/api/v1/oauth2/tokens";
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setGranttype(env.getProperty("mcp.grant_type"));
        tokenRequest.setPassword(env.getProperty("mcp.password"));
        tokenRequest.setTenant(env.getProperty("mcp.tenant"));
        tokenRequest.setUsername(env.getProperty("mcp.username"));

        try {
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            String response = ExternalHttpUtil.makePOSTRequest(tokenUrl, "", mapper.writeValueAsString(tokenRequest), "application/json");
            TokenResponse tokenResponse = mapper.readValue(response, TokenResponse.class);
            token = tokenResponse.getAccessToken();
        } catch (IOException e) {
            log.error("Failed to generate AuthN Token for MCP: {}",e.getLocalizedMessage());
        }

        return "Bearer " + token;
    }

    private String sendRequest(String url) {

        if (authorizationHeader == null) {
            authorizationHeader = createBearer();
        }

        HttpHeaders headers = new HttpHeaders();
        String mcpUrl = env.getProperty("mcp.url");
        headers.add("Authorization", authorizationHeader);
        headers.add("Content-Type", "application/json");
        return ExternalHttpUtil.makeGETRequest(mcpUrl + url, headers);

    }


    private List<AlertsStatusResponse> processResponse(String response) {
        List<AlertsStatusResponse> convertedResponse = new ArrayList<AlertsStatusResponse>();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        AlertsResponse responseObj = null;
        try {
            responseObj = mapper.readValue(response, AlertsResponse.class);
        } catch (IOException e) {
            log.error("Error in parsing response {}", e.getLocalizedMessage());
        }

        if (responseObj != null && responseObj.getData().length > 0) {
            for (int i = 0; i < responseObj.getData().length; i++) {
                try {
                    if (responseObj.getData()[i].getAttributes().getDisplayData().getDisplayName() != null
                        && !responseObj.getData()[i].getAttributes().getDisplayData().getDisplayName().isEmpty()) {
                        AlertsStatusResponse alertsStatusResponse = new AlertsStatusResponse();
                        alertsStatusResponse.setAdminState(
                            responseObj.getData()[i].getAttributes().getDisplayData().getAdminState());
                        alertsStatusResponse.setDisplayDeploymentState(
                            responseObj.getData()[i].getAttributes().getDisplayData().getDisplayDeploymentState());
                        alertsStatusResponse.setLastUpdatedAdminStateTimeStamp(
                            responseObj.getData()[i].getAttributes().getLastUpdatedAdminStateTimeStamp());
                        alertsStatusResponse.setLastUpdatedOperationalStateTimeStamp(
                            responseObj.getData()[i].getAttributes().getLastUpdatedOperationalStateTimeStamp());
                        if(responseObj.getData()[i].getAttributes().getDisplayData().getOperationState() != null) {
                            if (responseObj.getData()[i].getAttributes().getDisplayData().getOperationState()
                                .contains("Down"))
                                alertsStatusResponse.setOperationState("DOWN");
                            else if (responseObj.getData()[i].getAttributes().getDisplayData().getOperationState()
                                .contains("Up"))
                                alertsStatusResponse.setOperationState("UP");
                            else
                                alertsStatusResponse.setOperationState("UNKNOWN");
                        }else
                            alertsStatusResponse.setOperationState("UNKNOWN");

                        alertsStatusResponse.setLayerRateQualifier(
                            responseObj.getData()[i].getAttributes().getLayerRateQualifier());

                        if(responseObj.getData()[i].getAttributes().getDisplayData()
                            .getDisplayName().contains("/"))
                            alertsStatusResponse.setCustomerName(responseObj.getData()[i].getAttributes().getDisplayData()
                                .getDisplayName().substring(0, responseObj.getData()[i].getAttributes().getDisplayData()
                                    .getDisplayName().indexOf("/")));
                        alertsStatusResponse.setServiceName(
                            responseObj.getData()[i].getAttributes().getDisplayData().getDisplayName().substring(
                                responseObj.getData()[i].getAttributes().getDisplayData().getDisplayName()
                                    .indexOf("/") + 1
                            ));

                        convertedResponse.add(alertsStatusResponse);
                    }
                } catch (Exception e) {
                    log.error("Error setting service , {}", e.getLocalizedMessage());
                }
            }

        }
        convertedResponse.sort(Comparator.comparing(AlertsStatusResponse::getOperationState));

        return convertedResponse;

    }

    private List<MCPAlarmsResponse> processAlarmsResponse(String response) {
        List<MCPAlarmsResponse> convertedResponse = new ArrayList<MCPAlarmsResponse>();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        AlarmsResponse responseObj = null;
        try {
            responseObj = mapper.readValue(response, AlarmsResponse.class);
        } catch (IOException e) {
            log.error("Error in parsing response {}", e.getLocalizedMessage());
        }

        if (responseObj != null && responseObj.getData().length > 0) {
            for (int i = 0; i < responseObj.getData().length; i++) {
                try {
                    if (responseObj.getData()[i].getAttributes() != null){
                        MCPAlarmsResponse mcpAlarmsResponse = new MCPAlarmsResponse();
                        mcpAlarmsResponse.setId(responseObj.getData()[i].getAttributes().getId());
                        mcpAlarmsResponse.setAlarmId(responseObj.getData()[i].getAttributes().getAlarmid());
                        mcpAlarmsResponse.setNodeId(responseObj.getData()[i].getAttributes().getNodeId());
                        mcpAlarmsResponse.setRaAlarmId(responseObj.getData()[i].getAttributes().getRaAlarmId());
                        mcpAlarmsResponse.setNodeType(responseObj.getData()[i].getAttributes().getNodeType());
                        mcpAlarmsResponse.setState(responseObj.getData()[i].getAttributes().getState());
                        mcpAlarmsResponse.setResource(responseObj.getData()[i].getAttributes().getResource());
                        mcpAlarmsResponse.setResourceId(responseObj.getData()[i].getAttributes().getResourceId());
                        mcpAlarmsResponse.setNativeConditionType(responseObj.getData()[i].getAttributes().getNativeConditionType());
                        mcpAlarmsResponse.setConditionSeverity(responseObj.getData()[i].getAttributes().getConditionSeverity());
                        mcpAlarmsResponse.setServiceAffecting(responseObj.getData()[i].getAttributes().getServiceAffecting());
                        mcpAlarmsResponse.setManualClearable(responseObj.getData()[i].getAttributes().isManualClearable());
                        mcpAlarmsResponse.setFirstRaiseTime(responseObj.getData()[i].getAttributes().getFirstRaiseTime());
                        mcpAlarmsResponse.setLastRaiseTime(responseObj.getData()[i].getAttributes().getLastRaiseTime());
                        mcpAlarmsResponse.setAcknowledgeUpdateTime(responseObj.getData()[i].getAttributes().getAcknowledgeUpdateTime());
                        mcpAlarmsResponse.setAcknowledgeUpdateUser(responseObj.getData()[i].getAttributes().getAcknowledgeUpdateUser());
                        mcpAlarmsResponse.setNumberOfOccurrences(responseObj.getData()[i].getAttributes().getNumberOfOccurrences());
                        mcpAlarmsResponse.setAcknowledgeState(responseObj.getData()[i].getAttributes().getAcknowledgeState());
                        mcpAlarmsResponse.setDeviceId(responseObj.getData()[i].getAttributes().getDeviceId());
                        mcpAlarmsResponse.setDeviceName(responseObj.getData()[i].getAttributes().getDeviceName());
                        mcpAlarmsResponse.setIpAddress(responseObj.getData()[i].getAttributes().getIpAddress());
                        mcpAlarmsResponse.setMacAddress(responseObj.getData()[i].getAttributes().getMacAddress());
                        mcpAlarmsResponse.setSequenceId(responseObj.getData()[i].getAttributes().getSequenceId());
                        mcpAlarmsResponse.setEvent(responseObj.getData()[i].getAttributes().isEvent());
                        mcpAlarmsResponse.setClearable(responseObj.getData()[i].getAttributes().isClearable());

                        if(mcpAlarmsResponse.getDeviceName() !=null) {
                            int lastDashIndex = mcpAlarmsResponse.getDeviceName().lastIndexOf("-");
                            if (lastDashIndex != -1) {
                                String part1 = mcpAlarmsResponse.getDeviceName().substring(0, lastDashIndex).replace("-", ":");
                                String part2 = mcpAlarmsResponse.getDeviceName().substring(lastDashIndex + 1);
                                String part3 = removeFirstDelimiter(mcpAlarmsResponse.getResource());
                                mcpAlarmsResponse.setHawaikiDeviceLabel(part1 + "-" + part2 + ":" + part3);
                                mcpAlarmsResponse.setPortDetails(part3);
                            }
                        }
                        convertedResponse.add(mcpAlarmsResponse);
                    }
                } catch (Exception e) {
                    log.error("Error getting alarms , {}", e.getLocalizedMessage());
                }
            }

        }
        convertedResponse.sort(Comparator.comparing(MCPAlarmsResponse::getLastRaiseTime));

        return convertedResponse;
    }

    private String removeFirstDelimiter(String input) {
        int index = input.indexOf("-");
        if (index != -1) {
            int nextIndex = input.indexOf("-", index );
            if (nextIndex != -1) {
                return input.substring(nextIndex +1 );
            }
        }
        return input;
    }

}
