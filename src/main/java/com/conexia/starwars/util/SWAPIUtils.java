package com.conexia.starwars.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class SWAPIUtils {

    public static HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Application");
        return headers;
    }

    public static HttpEntity getDefaultHttpEntity() {
        return new HttpEntity<>(getDefaultHeaders());
    }

    public static String getUrlWithFilters(String url, Integer page, Integer size, Map<String, String> filters) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("page", page)
                .queryParam("limit", size);
        filters.forEach(uri::queryParam);
        return uri.toUriString();
    }
}
