package com.conexia.starwars.service;

import com.conexia.starwars.domain.dto.BaseEntity;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.domain.dto.swapi.SWAPIResponse;
import com.conexia.starwars.exception.SWAPIException;
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
import java.util.Objects;
import java.util.stream.Collectors;

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

    /*
     Llamada a la API para obtener los datos en un SWAPIResponse
     */
    private SWAPIResponse httpGet(String resource, Integer page, Integer size, Map<String, String> filters) {
        return restTemplate.exchange(
                        getUrlWithFilters(apiUrl + resource, page, size, filters),
                        HttpMethod.GET,
                        getDefaultHttpEntity(),
                        SWAPIResponse.class)
                .getBody();
    }

    public <T extends BaseEntity> PageResult<T> findAll(@NonNull String resource, Class<T[]> clazz, Integer page, Integer size, Map<String, String> filters) throws SWAPIException {
        try {
            SWAPIResponse data = httpGet(resource, page, size, filters);
            List<T> rows = Arrays.asList(om.convertValue(data.getResults(), clazz));
            return new PageResult<>(processUnsupportedFilters(rows, filters), data.getTotalRecords(), page, size);
        } catch (Exception e) {
            throw new SWAPIException("External API error obtaining list from " + resource, e);
        }
    }

    /*
     Proceso filtrados requeridos en el endpoint que no son soportados por la API
     */
    private <T extends BaseEntity> List<T> processUnsupportedFilters(List<T> rows, Map<String, String> filters) {
        if (filters.containsKey("id")) {
            Long id = Long.valueOf(filters.get("id"));
            return rows.stream().filter(e -> Objects.equals(e.getId(), id)).collect(Collectors.toList());
        }
        return rows;
    }
}
