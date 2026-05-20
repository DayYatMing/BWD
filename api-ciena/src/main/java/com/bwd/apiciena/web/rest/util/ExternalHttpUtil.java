package com.bwd.apiciena.web.rest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public class ExternalHttpUtil {

    private static final Logger log = LoggerFactory.getLogger(ExternalHttpUtil.class);

    public static String makeGETRequest(String url, HttpHeaders httpHeaders) {
        log.debug("Entering HTTPClientUtil for GET Request for URL: {}", url);
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5 * 1000).build();
        CustomHTTPRetryHandler retryHandler = new CustomHTTPRetryHandler();

        String response = null;
        try (
            CloseableHttpClient client = HttpClients.custom()
                .setRetryHandler(retryHandler.process())
                .setDefaultRequestConfig(config)
                .build()
        ) {
            HttpGet get = new HttpGet(url);
            addHTTPHeaders(get, httpHeaders);
            HttpResponse httpReponse = client.execute(get);
            response = EntityUtils.toString(httpReponse.getEntity());
        } catch (Exception exception) {
            log.error("Error in HTTPClientUtil.createGETRequest() :{}", exception.getLocalizedMessage());
        }
        if (response != null || !response.isEmpty()) {
            log.debug("Return none empty response.");
        } else {
            log.debug("Response is null or empty.");
        }
        return response;
    }

    public static String makePOSTRequest(String url, String body, HttpHeaders headers) {
        log.debug("Entering HTTPClientUtil for POST Request for URL: {}", url);
        String response = null;
        try {
            URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String value = entry.getValue() != null ? entry.getValue().get(0) : null;

                if (value != null) {
                    con.setRequestProperty(entry.getKey(), value);
                }
            }
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(body.getBytes());
            response = ExternalHttpUtil.readHttpResponse(con);
        } catch (Exception exception) {
            log.error("Error in HTTPClientUtil.createPOSTRequest() :{}", exception.getLocalizedMessage());
        }
        log.debug("Exiting HTTPClientUtil for POST Request Response: {}", response);
        return response;
    }

    public static String makeAuthRequest(String url, String body, String content_type) {
        log.debug("Entering HTTPClientUtil for Authentication Request for URL: {}", url);
        String auth = null;
        try {
            URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestProperty("content-type", content_type);
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(body.getBytes());
            auth = ExternalHttpUtil.readHttpResponse(con);
        } catch (Exception exception) {
            log.error("Error in HTTPClientUtil.createAuthRequest() :{}", exception.getLocalizedMessage());
        }
        log.debug("Exiting HTTPClientUtil for Auth Request Response: {}", auth);
        return auth;
    }

    public static String readHttpResponse(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    private static void addHTTPHeaders(HttpGet httpGet, HttpHeaders httpHeaders) {
        if (httpHeaders == null) return;
        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            String headerName = entry.getKey();
            for (String headerValue : entry.getValue()) {
                httpGet.addHeader(headerName, headerValue);
            }
        }
    }
}
