package com.bwd.cms.service;

import com.bwd.cms.domain.Stats;
import com.bwd.cms.web.util.OtrsHttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<Stats> getStats(String brokerURL, String key, HttpHeaders headers) throws IOException {
        String updatedUrl = brokerURL + "/api/tems";

        String jsonResponse = OtrsHttpUtil.makeGETRequest(updatedUrl, key, headers);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.readValue(jsonResponse, new TypeReference<>() {});
    }

    public List<String> getServices(String brokerURL, String key, HttpHeaders headers) throws IOException {
        String updatedUrl = brokerURL + "/api/services";

        String jsonResponse = OtrsHttpUtil.makeGETRequest(updatedUrl, key, headers);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.readValue(jsonResponse, new TypeReference<>() {});
    }

    public List<String> getSources(String brokerURL, String key, HttpHeaders headers) throws IOException {
        String updatedUrl = brokerURL + "/api/sources";

        String jsonResponse = OtrsHttpUtil.makeGETRequest(updatedUrl, key, headers);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.readValue(jsonResponse, new TypeReference<>() {});
    }
}
