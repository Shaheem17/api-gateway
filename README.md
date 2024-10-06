# API Gateway Service

The **API Gateway Service** provides a simplified interface for sending REST API requests from your Spring Boot application. It supports various HTTP methods and allows you to specify request bodies, query parameters, headers, and path variables, handling serialization and deserialization seamlessly.

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)

## Installation

To include the API Gateway Service in your Spring Boot project, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.shmportfolio.gatewayservice</groupId>
    <artifactId>api-gateway</artifactId>
    <version>1.0.7</version>
</dependency>
```

## Configuration

You need to configure the `IGatewayService` bean in your Spring Boot application. Create a configuration class as shown below:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayServiceAutoConfiguration {

    @Bean
    public IGatewayService gatewayService(WebClient.Builder webClientBuilder) {
        return new GatewayService(webClientBuilder);
    }

}
```

## Usage

You can use field injection to access the `IGatewayService` in your Spring Boot application. Here's an example:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    @Autowired
    IGatewayService gatewayService;

    public void callApi() {
        String apiUrl = "https://example.com/api/resource";
        Map<String, String> queryParams = new HashMap<>();
        Map<String, String> pathVariables = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        queryParams.put("key", "value");
        YourResponseDto response = gatewayService.getRestApi(
                apiUrl, 
                queryParams, 
                pathVariables, 
                headers,
                YourResponseDto.class);
        System.out.println(response);
    }
}

```

_For more information on how to customize and extend the API Gateway Service, please refer to the documentation provided in the package._