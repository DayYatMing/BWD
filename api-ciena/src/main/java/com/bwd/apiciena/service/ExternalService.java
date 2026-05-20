package com.bwd.apiciena.service;

import com.bwd.apiciena.service.dto.TokenRequest;
import com.bwd.apiciena.service.dto.TokenResponse;
import com.bwd.apiciena.web.rest.util.ExternalHttpUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ExternalService {

    private final Logger log = LoggerFactory.getLogger(ExternalService.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private String authorizationHeader = null;

    @Autowired
    Environment env;

    public String sendAndProcessEvents(String endpoint, String body) {
        String response = sendRequest(endpoint, body);
        if (response.contains("error")) {
            authorizationHeader = null;
            response = sendRequest(endpoint, body);
        }
        return response;
    }

    private String createBearer() {
        String token = "";
        String tokenUrl = env.getProperty("mcp.url") + "/tron/api/v1/oauth2/tokens";
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setGranttype(env.getProperty("mcp.grant_type"));
        tokenRequest.setPassword(env.getProperty("mcp.password"));
        tokenRequest.setTenant(env.getProperty("mcp.tenant"));
        tokenRequest.setUsername(env.getProperty("mcp.username"));

        try {
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            String response = ExternalHttpUtil.makeAuthRequest(tokenUrl, mapper.writeValueAsString(tokenRequest), "application/json");
            TokenResponse tokenResponse = mapper.readValue(response, TokenResponse.class);
            token = tokenResponse.getAccessToken();
        } catch (IOException e) {
            log.error("Failed to generate AuthN Token for MCP: {}", e.getLocalizedMessage());
        }

        return "Bearer " + token;
    }

    private String sendRequest(String endpoint, String body) {
        if (authorizationHeader == null) {
            authorizationHeader = createBearer();
        }

        HttpHeaders headers = new HttpHeaders();
        String mcpUrl = env.getProperty("mcp.url");
        headers.add("Authorization", authorizationHeader);
        headers.add("Content-Type", "application/json");
        return ExternalHttpUtil.makePOSTRequest(mcpUrl + endpoint, body, headers);
    }
}
