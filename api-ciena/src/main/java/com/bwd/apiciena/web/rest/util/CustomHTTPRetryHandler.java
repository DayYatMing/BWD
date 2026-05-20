package com.bwd.apiciena.web.rest.util;

import org.apache.http.client.HttpRequestRetryHandler;

public class CustomHTTPRetryHandler {

    public HttpRequestRetryHandler process() {
        return (exception, executionCount, context) -> executionCount < 3;
    }
}
