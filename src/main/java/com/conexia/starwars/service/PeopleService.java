package com.conexia.starwars.service;

import com.conexia.starwars.domain.dto.PeopleDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.exception.SWAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class PeopleService {

    private final SWAPIService swapiService;
    private static final String SWAPI_RESOURCE = "/people";

    @Autowired
    public PeopleService(SWAPIService swapiService) {
        this.swapiService = swapiService;
    }

    public PageResult<PeopleDTO> findAll(Integer page, Integer size, Long id, String name) throws SWAPIException {
        Map<String, String> filters = new HashMap<>();
        if (!isNull(id)) filters.put("id", String.valueOf(id));
        if (!isNull(name)) filters.put("name", name);

        return swapiService.findAll(SWAPI_RESOURCE, PeopleDTO[].class, page, size, filters);
    }
}
