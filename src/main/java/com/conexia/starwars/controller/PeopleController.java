package com.conexia.starwars.controller;

import com.conexia.starwars.domain.dto.BaseEntity;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    public ResponseEntity<PageResult<BaseEntity>> findAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                          @RequestParam(required = false, defaultValue = "10") Integer size,
                                                          @RequestParam(required = false) Long id,
                                                          @RequestParam(required = false) String name) {
        return ResponseEntity.ok(peopleService.findAll(page, size, id, name));
    }
}