package com.conexia.starwars.service;

import com.conexia.starwars.domain.dto.BaseEntity;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {
    public PageResult<BaseEntity> findAll(Integer page, Integer size, Long id, String name) {
        // request to SWAPIService ...
        return new PageResult<>();
    }
}
