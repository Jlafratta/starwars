package com.conexia.starwars.service;

import com.conexia.starwars.domain.dto.PeopleDTO;
import com.conexia.starwars.domain.dto.StarshipDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class StarshipService {

    private final SWAPIService swapiService;
    private static final String SWAPI_RESOURCE = "/starships";

    @Autowired
    public StarshipService(SWAPIService swapiService) {
        this.swapiService = swapiService;
    }

    public PageResult<StarshipDTO> findAll(Integer page, Integer size, Long id, String name) {
        Map<String, String> filters = new HashMap<>();
        if (!isNull(id)) filters.put("id", String.valueOf(id));
        if (!isNull(name)) filters.put("name", name);

        return swapiService.findAll(SWAPI_RESOURCE, StarshipDTO[].class, page, size, filters);
    }
}
