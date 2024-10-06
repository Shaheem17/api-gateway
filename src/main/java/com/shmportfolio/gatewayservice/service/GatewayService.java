package com.shmportfolio.gatewayservice.service;

import com.shmportfolio.gatewayservice.exception.GatewayException;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

public class GatewayService implements IGatewayService{
    private final WebClient webClient;

    public GatewayService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public <T, R> R sendRestApi(String path, T body, HttpMethod method, Map<String, String> queryParams, Map<String, String> pathVariables, Map<String, String> headers, Class<R> responseType) {
        return sendRestApi(uriBuilder -> buildUri(path, queryParams, pathVariables), body, method, headers, responseType);
    }

    public <T, R> R sendRestApi(Function<UriBuilder, URI> uriFunction, T body, HttpMethod method, Map<String, String> headers, Class<R> responseType) {
        WebClient.RequestHeadersSpec<?> headersSpec = (body != null)
                ? webClient.method(method).uri(uriFunction).bodyValue(body)
                : webClient.method(method).uri(uriFunction);
        if (headers != null) {
            headers.forEach(headersSpec::header);
        }
        return headersSpec.retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new GatewayException(response.statusCode(), errorBody))))
                .bodyToMono(responseType)
                .block();
    }

    @Override
    public <T> T getRestApi(String path, Map<String, String> queryParams, Map<String, String> pathVariables, Map<String, String> headers, Class<T> responseType) {
        return getRestApi(uriBuilder -> buildUri(path, queryParams, pathVariables), headers, responseType);
    }

    public <T> T getRestApi(Function<UriBuilder, URI> uriFunction, Map<String, String> headers, Class<T> responseType) {
        WebClient.RequestHeadersSpec<?> headersSpec = webClient.get().uri(uriFunction);
        if (headers != null) {
            headers.forEach(headersSpec::header);
        }
        return headersSpec.retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new GatewayException(response.statusCode(), errorBody))))
                .bodyToMono(responseType)
                .block();
    }



    @Override
    public <T, R> R sendRestApiForm(String path, T body, HttpMethod method, Map<String, String> queryParams, Map<String, String> pathVariables, Map<String, String> headers, Class<R> responseType) {
        return sendRestApiForm(uriBuilder -> buildUri(path, queryParams, pathVariables), body, method, headers, responseType);
    }

    public <T, R> R sendRestApiForm(Function<UriBuilder, URI> uriFunction, T body, HttpMethod method, Map<String, String> headers, Class<R> responseType) {
        WebClient.RequestHeadersSpec<?> headersSpec;
        headersSpec = webClient.method(method)
                .uri(uriFunction)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body);

        if (headers != null) {
            headers.forEach(headersSpec::header);
        }
        return headersSpec.retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new GatewayException(response.statusCode(), errorBody))))
                .bodyToMono(responseType)
                .block();
    }

    private URI buildUri(String basePath, Map<String, String> queryParams, Map<String, String> pathVariables) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(basePath);

        if (queryParams != null) {
            queryParams.forEach(uriComponentsBuilder::queryParam);
        }

        if (pathVariables != null) {
            return uriComponentsBuilder.buildAndExpand(pathVariables).toUri();
        } else {
            return uriComponentsBuilder.build().toUri();
        }
    }
}
