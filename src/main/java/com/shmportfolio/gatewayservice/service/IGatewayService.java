package com.shmportfolio.gatewayservice.service;

import com.shmportfolio.gatewayservice.dto.ApiResponse;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * IGatewayService interface
 * <p>
 * This interface defines methods for sending and receiving data via REST APIs. It provides
 * a generic approach to handle HTTP requests (e.g., GET, POST, PUT, DELETE) to external
 * services, making it easier to integrate with third-party APIs. The methods allow for
 * dynamic construction of API requests, including support for query parameters, path variables,
 * headers, and request bodies.
 * </p>
 *
 * <p>
 * Implementations of this interface should handle the HTTP communication and provide
 * appropriate error handling for failed requests. The generic typing ensures that the
 * caller can specify the expected response type.
 * </p>
 *
 * <p><b>Note:</b> Ensure that the implementations of this interface properly handle
 * serialization and deserialization of request and response objects.</p>
 */
public interface IGatewayService {

    /**
     * Sends a REST API request with the specified HTTP method and request body.
     * <p>
     * This method facilitates sending an HTTP request to an external API, using the specified
     * path, HTTP method (e.g., POST, PUT), and request body. It also supports adding query
     * parameters, path variables, and custom headers to the request.
     * </p>
     *
     * <p>
     * The method returns a response of type {@code R}, which is determined by the caller.
     * It handles the serialization of the request body and the deserialization of the response
     * into the specified response type.
     * </p>
     *
     * <p><b>Note:</b> Ensure that the request body and response type are properly structured
     * and compatible with the API being called. Implementations should also handle potential
     * exceptions or errors that may arise during the API call.</p>
     *
     * @param path the endpoint path of the API to be called
     * @param body the request body to be sent with the API call, of type {@code T}
     * @param method the HTTP method to use (e.g., POST, PUT)
     * @param queryParams a map of query parameters to include in the API request
     * @param pathVariables a map of path variables to substitute in the API endpoint path
     * @param headers a map of headers to include in the API request
     * @param responseType the expected response type, of type {@code R}
     * @param <T> the type of the request body
     * @param <R> the type of the response
     * @return the response from the API, deserialized into the specified response type {@code R}
     */
    <T, R> R sendRestApi(String path, T body, HttpMethod method, Map<String, String> queryParams, Map<String, String> pathVariables, Map<String, String> headers, Class<R> responseType);

    /**
     * Sends an HTTP request to a REST API using the specified method and body.
     * <p>
     * This method is designed for making requests to external APIs that may require
     * a request body in the form of form data, such as POST or PUT requests. It allows you
     * to specify the endpoint path, request body, query parameters, path variables, and headers.
     * The method returns the response deserialized into the specified response type {@code R}.
     * </p>
     *
     * <p><b>Note:</b> Ensure that the response type is correctly specified to match the
     * expected API response format. Implementations should also handle potential exceptions
     * or errors that may occur during the API call.</p>
     *
     * @param path the endpoint path of the API to be called
     * @param body the required request body to be sent with the API request as form data, of type {@code T}
     * @param method the HTTP method to be used for the request (e.g., POST, PUT)
     * @param queryParams a map of query parameters to include in the API request
     * @param pathVariables a map of path variables to substitute in the API endpoint path
     * @param headers a map of headers to include in the API request
     * @param responseType the expected response type, of type {@code R}
     * @param <T> the type of the request body
     * @param <R> the type of the response
     * @return the response from the API, deserialized into the specified response type {@code R}
     */
    <T, R> R sendRestApiForm(String path, T body, HttpMethod method, Map<String, String> queryParams, Map<String, String> pathVariables, Map<String, String> headers, Class<R> responseType);


    /**
     * Sends a GET request to a REST API and retrieves the response.
     * <p>
     * This method is designed for making GET requests to external APIs, using the specified
     * path, query parameters, path variables, and headers. It returns the response deserialized
     * into the specified response type {@code T}.
     * </p>
     *
     * <p><b>Note:</b> Ensure that the response type is correctly specified to match the
     * expected API response format. Implementations should also handle potential exceptions
     * or errors that may occur during the API call.</p>
     *
     * @param path the endpoint path of the API to be called
     * @param queryParams a map of query parameters to include in the API request
     * @param pathVariables a map of path variables to substitute in the API endpoint path
     * @param headers a map of headers to include in the API request
     * @param responseType the expected response type, of type {@code T}
     * @param <T> the type of the response
     * @return the response from the API, deserialized into the specified response type {@code T}
     */

    <T> T getRestApi(String path, Map<String, String> queryParams, Map<String, String> pathVariables, Map<String, String> headers, Class<T> responseType);


    /**
     * Sends an HTTP request to a REST API using the specified method and form data.
     * <p>
     * This method is designed for making requests to external APIs that require
     * a request body in the form of <b>multipart/form-data</b>. It supports HTTP methods such as
     * POST, PUT, or others as specified. The method allows you to specify the endpoint path,
     * form data fields, query parameters, path variables, and headers.
     * The method returns the response deserialized into the specified response type {@code R}.
     * </p>
     *
     * <p><b>Note:</b>
     * Ensure that the form data is properly structured, with keys representing field names and values
     * representing the data to be sent. Values can include strings, files, or other supported data types.
     * The response type should match the expected API response format. Implementations should also
     * handle potential exceptions or errors that may occur during the API call.</p>
     *
     * <p><b>Usage:</b></p>
     * <pre>{@code
     * Map<String, Object> formData = new HashMap<>();
     * formData.put("key1", "value1");
     * formData.put("file", new FileSystemResource("/path/to/file.txt"));
     *
     * String response = sendRestApiMultipart(
     *     "/upload",
     *     formData,
     *     HttpMethod.POST,
     *     null, // queryParams
     *     null, // pathVariables
     *     Map.of("Authorization", "Bearer token"),
     *     String.class
     * );
     * }</pre>
     *
     * @param path the endpoint path of the API to be called
     * @param formData a map containing form data fields to include in the request body.
     *                 Keys represent field names, and values can include strings, files, or other data types.
     * @param method the HTTP method to be used for the request (e.g., POST, PUT, PATCH)
     * @param queryParams a map of query parameters to include in the API request
     * @param pathVariables a map of path variables to substitute in the API endpoint path
     * @param headers a map of headers to include in the API request
     * @param responseType the expected response type, of type {@code R}
     * @param <R> the type of the response
     * @return the response from the API, deserialized into the specified response type {@code R}
     */
    <R> R sendRestApiMultipart(String path, Map<String, Object> formData, HttpMethod method, Map<String, String> queryParams, Map<String, String> pathVariables, Map<String, String> headers, Class<R> responseType);


    /**
     * Sends a GET request to a REST API and retrieves the response along with headers.
     * <p>
     * This method is designed for making GET requests to external APIs, using the specified
     * path, query parameters, path variables, and headers. It returns an {@code ApiResponse<T>}
     * object that encapsulates the response body and headers.
     * </p>
     *
     * <p><b>Note:</b> Ensure that the response type is correctly specified to match the
     * expected API response format. The method also provides access to response headers,
     * making it suitable for scenarios where additional metadata from the response is required.
     * Implementations should handle potential exceptions or errors that may occur during the API call.
     * </p>
     *
     * @param path the endpoint path of the API to be called
     * @param queryParams a map of query parameters to include in the API request
     * @param pathVariables a map of path variables to substitute in the API endpoint path
     * @param headers a map of headers to include in the API request
     * @param responseType the expected response type, of type {@code T}
     * @param <T> the type of the response body
     * @return an {@code ApiResponse<T>} object containing the response body and headers
     */
    <T> ApiResponse<T> getRestApiWithHeaders(String path, Map<String, String> queryParams, Map<String, String> pathVariables, Map<String, String> headers, Class<T> responseType);

    /**
     * Sends an HTTP request to a REST API and retrieves the response along with headers.
     * <p>
     * This method is designed for making HTTP requests (GET, POST, PUT, DELETE, etc.) to external APIs,
     * using the specified path, query parameters, path variables, headers, HTTP method, and request body.
     * It returns an {@code ApiResponse<T>} object that encapsulates the response body and headers.
     * </p>
     *
     * @param path         the endpoint path of the API to be called
     * @param queryParams  a map of query parameters to include in the API request
     * @param pathVariables a map of path variables to substitute in the API endpoint path
     * @param headers      a map of headers to include in the API request
     * @param method       the HTTP method (GET, POST, PUT, DELETE, etc.)
     * @param requestBody  the request body to send with the API call (if applicable)
     * @param responseType the expected response type, of type {@code T}
     * @param <T>          the type of the response body
     * @param <R>          the type of the request body
     * @return an {@code ApiResponse<T>} object containing the response body and headers
     */
    <T, R> ApiResponse<T> sendRestApiRequest(String path, Map<String, String> queryParams, Map<String, String> pathVariables, Map<String, String> headers, HttpMethod method, R requestBody, Class<T> responseType);


}
