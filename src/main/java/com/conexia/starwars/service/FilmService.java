package com.conexia.starwars.service;

import com.conexia.starwars.domain.dto.FilmDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.exception.SWAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class FilmService {

    private final SWAPIService swapiService;
    private static final String SWAPI_RESOURCE = "/films";

    @Autowired
    public FilmService(SWAPIService swapiService) {
        this.swapiService = swapiService;
    }

    public PageResult<FilmDTO> findAll(Integer page, Integer size, Long id, String title) throws SWAPIException {
        Map<String, String> filters = new HashMap<>();
        if (!isNull(id)) filters.put("id", String.valueOf(id));
        if (!isNull(title)) filters.put("title", title);

        return swapiService.findAll(SWAPI_RESOURCE, FilmDTO[].class, page, size, filters);
    }
}
