package com.shmportfolio.gatewayservice.dto;

import java.util.List;
import java.util.Map;

public class ApiResponse<T> {

    private T body;
    private Map<String, List<String>> headers;

    public ApiResponse(T body, Map<String, List<String>> headers) {
        this.body = body;
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

}
