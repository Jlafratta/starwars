package com.conexia.starwars.controller;

import com.conexia.starwars.domain.dto.PeopleDTO;
import com.conexia.starwars.domain.dto.StarshipDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.service.StarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/starship")
public class StarshipController {

    private final StarshipService starshipService;

    @Autowired
    public StarshipController(StarshipService starshipService) {
        this.starshipService = starshipService;
    }

    @GetMapping()
    public ResponseEntity<PageResult<StarshipDTO>> findAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                                           @RequestParam(required = false) Long id,
                                                           @RequestParam(required = false) String name) {
        return ResponseEntity.ok(starshipService.findAll(page, size, id, name));
    }
}
