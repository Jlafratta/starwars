package com.conexia.starwars.service;

import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.domain.dto.swapi.SWAPIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.conexia.starwars.util.SWAPIUtils.getDefaultHttpEntity;
import static com.conexia.starwars.util.SWAPIUtils.getUrlWithFilters;

@Service
public class SWAPIService {
    @Value("${swapi.url}")
    private String apiUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;

    @Autowired
    public SWAPIService(@Qualifier("SWAPIRestTemplate") RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.om = objectMapper;
    }

    // Llamada a la API para obtener los datos en un SWAPIResponse
    private SWAPIResponse httpGet(String resource, Integer page, Integer size, Map<String, String> filters) {
        return restTemplate.exchange(
                        getUrlWithFilters(apiUrl + resource, page, size, filters),
                        HttpMethod.GET,
                        getDefaultHttpEntity(),
                        SWAPIResponse.class)
                .getBody();
    }

    public <T> PageResult<T> findAll(@NonNull String resource, Class<T[]> clazz, Integer page, Integer size, Map<String, String> filters) {
        SWAPIResponse data = httpGet(resource, page, size, filters);
        List<T> rows = Arrays.asList(om.convertValue(data.getResults(), clazz));
        return new PageResult<>(rows, data.getTotalRecords(), page, size);
    }
}
