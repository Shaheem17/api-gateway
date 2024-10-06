package com.shmportfolio.gatewayservice.exception;


import org.springframework.http.HttpStatusCode;

public class GatewayException extends RuntimeException {

    private final HttpStatusCode status;
    private final Object errorBody;

    public GatewayException(HttpStatusCode status, Object errorBody) {
        this.status = status;
        this.errorBody = errorBody;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public Object getErrorBody() {
        return errorBody;
    }
}
