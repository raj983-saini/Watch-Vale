package com.example.Titan.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RestUtils {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;

    public RestUtils(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.mapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    // General method for building URIs with query parameters
    private UriComponents buildUriComponents(String url, Map<String, ?> queryParams) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        if (queryParams != null) {
            queryParams.forEach(uriComponentsBuilder::queryParam);
        }
        return uriComponentsBuilder.build();
    }

    // General method for sending requests and handling responses
    private <T> T exchange(String url, HttpMethod method, HttpHeaders headers, Object payload, Class<T> clazz) throws Exception {
        HttpEntity<?> requestEntity = new HttpEntity<>(payload, headers);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();

        try {
            logRequest(url, requestEntity);
            long startTime = System.currentTimeMillis();
            ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUri(), method, requestEntity, String.class);
            logResponse(uriComponents, startTime, response);
            return mapper.readValue(response.getBody(), clazz);
        } catch (Exception e) {
            log.error("Error during request: {}, {}", e.getMessage(), e);
            throw e;
        }
    }

    // Logging methods
    private void logRequest(String url, HttpEntity<?> requestEntity) throws Exception {
        log.info("Request to URL: {} with Payload: {}", url, mapper.writeValueAsString(requestEntity));
    }

    private void logResponse(UriComponents uriComponents, long startTime, ResponseEntity<String> response) throws Exception {
        log.info("Fetched response from URL: {} in {}ms", uriComponents.encode().toUri(), System.currentTimeMillis() - startTime);
        log.info("Response: {}", mapper.writeValueAsString(response));
    }

    // General method for handling GET requests
    public <T> T getForObject(String url, HttpHeaders headers, Map<String, Object> queryParams, Class<T> clazz) throws Exception {
        UriComponents uriComponents = buildUriComponents(url, queryParams);
        return exchange(uriComponents.toUriString(), HttpMethod.GET, headers, null, clazz);
    }

    public <T> T getForObject(String url, Map<String, String> headers, Map<String, String> queryParams, Class<T> clazz) throws Exception {
        // Convert Map<String, String> to Map<String, Object>
        Map<String, Object> objectQueryParams = convertToObjectMap(queryParams);
        return getForObject(url, createHttpHeaders(headers), objectQueryParams, clazz);
    }

    public <T> T getForObject(String url, HttpHeaders headers, Class<T> clazz) throws Exception {
        return exchange(url, HttpMethod.GET, headers, null, clazz);
    }

    public <T> T getForObject(String url, Map<String, String> headers, Class<T> clazz) throws Exception {
        return getForObject(url, createHttpHeaders(headers), null, clazz);
    }

    // General method for handling POST requests
    public <T> T postForObject(String url, HttpHeaders headers, Map<String, Object> payload, Class<T> clazz) throws Exception {
        return exchange(url, HttpMethod.POST, headers, payload, clazz);
    }

    public <T> T postForObject(String url, HttpHeaders headers, Object payload, Class<T> clazz) throws Exception {
        return exchange(url, HttpMethod.POST, headers, payload, clazz);
    }

    public <T> T postForObject(String url, Map<String, String> headers, Map<String, Object> payload, Class<T> clazz) throws Exception {
        return postForObject(url, createHttpHeaders(headers), payload, clazz);
    }

    public <T> T postForObject(String url, Map<String, String> headers, Class<T> clazz) throws Exception {
        return exchange(url, HttpMethod.POST, createHttpHeaders(headers), null, clazz);
    }

    // General method for handling PUT requests
    public <T> T putForObject(String url, HttpHeaders headers, Object payload, Class<T> clazz) throws Exception {
        return exchange(url, HttpMethod.PUT, headers, payload, clazz);
    }

    public <T> T putForObject(String url, Map<String, String> headers, Object payload, Class<T> clazz) throws Exception {
        return putForObject(url, createHttpHeaders(headers), payload, clazz);
    }

    // General method for handling PATCH requests
    public <T> T patchForObject(String url, HttpHeaders headers, Map<String, Object> payload, Class<T> clazz) throws Exception {
        return exchange(url, HttpMethod.PATCH, headers, payload, clazz);
    }

    public <T> T patchForObject(String url, Map<String, String> headers, Map<String, Object> payload, Class<T> clazz) throws Exception {
        return patchForObject(url, createHttpHeaders(headers), payload, clazz);
    }

    // Method for uploading files
    public <T> T postFile(String url, Map<String, String> headers, File file, Class<T> clazz) throws Exception {
        HttpHeaders httpHeaders = createHttpHeaders(headers);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        return exchange(url, HttpMethod.POST, httpHeaders, body, clazz);
    }

    // Method for GET with path parameters
    public <T> T getForObjectWithPathParam(String url, Map<String, String> headers, String pathParam, Class<T> clazz) throws Exception {
        String fullUrl = url + "/" + pathParam;
        return getForObject(fullUrl, headers, clazz);
    }

    // Helper method to create HttpHeaders from a Map
    private HttpHeaders createHttpHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        return httpHeaders;
    }

    // Helper method to convert Map<String, String> to Map<String, Object>
    private Map<String, Object> convertToObjectMap(Map<String, String> stringMap) {
        return stringMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
