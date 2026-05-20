package com.bwd.brokerotrs.controller.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;

public class ExternalHttpUtil {

    private static final Logger log = LoggerFactory.getLogger(ExternalHttpUtil.class);

    private static final CloseableHttpClient client = HttpClients.createDefault();

    public static String makeGETRequest(String url, HttpHeaders httpHeaders) {
        log.debug("GET request to URL: {}", url);

        HttpGet request = new HttpGet(url);
        addHeaders(request, httpHeaders);

        try (CloseableHttpResponse response = client.execute(request)) {

            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;

        } catch (Exception e) {
            log.error("GET request failed for {}: {}", url, e.getMessage());
            return null;
        }
    }

    public static String makePOSTRequest(String url, String body, String contentType) {
        log.debug("POST request to URL: {}", url);

        HttpPost request = new HttpPost(url);

        try {
            if (contentType != null) {
                request.setHeader("Content-Type", contentType);
            } else {
                request.setHeader("Content-Type", "application/json");
            }

            if (body != null) {
                request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = client.execute(request)) {

                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            }

        } catch (Exception e) {
            log.error("POST request failed for {}: {}", url, e.getMessage());
            return null;
        }
    }

    public static String makePATCHRequest(String url, String body, String contentType) {
        log.debug("PATCH request to URL: {}", url);

        HttpPatch request = new HttpPatch(url);

        try {
            request.setHeader("Content-Type", contentType != null ? contentType : "application/json");

            if (body != null && !body.isEmpty()) {
                request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = client.execute(request)) {

                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            }

        } catch (Exception e) {
            log.error("PATCH request failed for {}: {}", url, e.getMessage());
            return null;
        }
    }

    private static void addHeaders(HttpGet request, HttpHeaders headers) {
        if (headers == null) return;

        headers.forEach((key, values) -> {
            for (String value : values) {
                request.addHeader(key, value);
            }
        });
    }
}