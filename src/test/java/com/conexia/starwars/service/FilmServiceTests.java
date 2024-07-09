package com.conexia.starwars.service;

import com.conexia.starwars.domain.dto.FilmDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.exception.SWAPIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmServiceTests {

    @MockBean
    private SWAPIService swapiService;

    private FilmService filmService;

    @BeforeEach
    public void setUp() {
        this.filmService = new FilmService(swapiService);
    }

    private FilmDTO getFristFilmDTO() {
        FilmDTO film = new FilmDTO();

        film.setId(1L);
        film.setCharacters(new ArrayList<>());
        film.setPlanets(new ArrayList<>());
        film.setStarships(new ArrayList<>());
        film.setVehicles(new ArrayList<>());
        film.setSpecies(new ArrayList<>());
        film.setCreated(new Date());
        film.setEdited(new Date());
        film.setProducer("George Lucas");
        film.setTitle("Attack of the Clones");
        film.setEpisodeid("2");
        film.setDirector("George Lucas");
        film.setReleasedate("2002-05-16");
        film.setOpeningcrawl("A long time ago in a galaxy far, far away...");
        return film;
    }

    private FilmDTO getSecondFilmDTO() {
        FilmDTO film = new FilmDTO();

        film.setId(2L);
        film.setCharacters(new ArrayList<>());
        film.setPlanets(new ArrayList<>());
        film.setStarships(new ArrayList<>());
        film.setVehicles(new ArrayList<>());
        film.setSpecies(new ArrayList<>());
        film.setCreated(new Date());
        film.setEdited(new Date());
        film.setProducer("George Lucas");
        film.setTitle("Attack of the Clones 2");
        film.setEpisodeid("3");
        film.setDirector("George Lucas");
        film.setReleasedate("2002-05-16");
        film.setOpeningcrawl("A long time ago in a galaxy far, far away...");
        return film;
    }

    @Test
    public void testFindAllFilmsWithoutFiltersOk() {
        // Mock objs
        List<FilmDTO> filmList = new ArrayList<>();
        filmList.add(getFristFilmDTO());
        filmList.add(getSecondFilmDTO());
        Map<String, String> filters = new HashMap<>();
        int page = 1;
        int size = 10;
        PageResult<FilmDTO> pageResult = new PageResult<>(filmList, filmList.size(), page, size);

        // Comportamiento de mocks
        when(swapiService.findAll("/films", FilmDTO[].class, page, size, filters)).thenReturn(pageResult);

        // Ejecucion metodo a testear
        PageResult<FilmDTO> result = this.filmService.findAll(page, size, null, null);

        // Assertions
        assertNotNull(result);
        assertEquals(pageResult, result);
        verify(swapiService, times(1)).findAll("/films", FilmDTO[].class, page, size, filters);
    }

    @Test
    public void testFindAllFilmsWithFiltersOk() {
        // Mock objs
        List<FilmDTO> filmList = new ArrayList<>();
        filmList.add(getFristFilmDTO());
        filmList.add(getSecondFilmDTO());
        int page = 1;
        int size = 10;
        long id = 1L;
        String title = "Attack";
        Map<String, String> filters = new HashMap<>();
        filters.put("id", String.valueOf(id));
        filters.put("title", title);
        PageResult<FilmDTO> pageResult = new PageResult<>(filmList, filmList.size(), page, size);

        // Comportamiento de mocks
        when(swapiService.findAll("/films", FilmDTO[].class, page, size, filters)).thenReturn(pageResult);

        // Ejecucion metodo a testear
        PageResult<FilmDTO> result = this.filmService.findAll(page, size, id, title);

        // Assertions
        assertNotNull(result);
        assertEquals(pageResult, result);
        verify(swapiService, times(1)).findAll("/films", FilmDTO[].class, page, size, filters);
    }

    @Test
    public void testFindAllFilmsWithoutFiltersError() {
        // Mock objs
        Map<String, String> filters = new HashMap<>();
        int page = 1;
        int size = 10;
        // Comportamiento de mocks
        when(swapiService.findAll("/films", FilmDTO[].class, page, size, filters)).thenThrow(new SWAPIException("External API error"));

        // Ejecucion metodo a testear
        assertThrows(SWAPIException.class, () ->
                filmService.findAll(1, 10, null, null)
        );

        // Assertions
        verify(swapiService, times(1)).findAll("/films", FilmDTO[].class, page, size, filters);
    }

}
