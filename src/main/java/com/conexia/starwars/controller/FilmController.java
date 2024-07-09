package com.conexia.starwars.controller;

import com.conexia.starwars.domain.dto.FilmDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping()
    public ResponseEntity<PageResult<FilmDTO>> findAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                                       @RequestParam(required = false) Long id,
                                                       @RequestParam(required = false) String title) {
        return ResponseEntity.ok(filmService.findAll(page, size, id, title));
    }
}
