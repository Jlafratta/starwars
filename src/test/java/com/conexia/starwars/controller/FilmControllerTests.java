package com.conexia.starwars.controller;

import com.conexia.starwars.domain.dto.FilmDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.exception.SWAPIException;
import com.conexia.starwars.service.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    FilmController filmController;

    @BeforeEach
    public void setUp() {
        this.filmController = new FilmController(filmService);
    }

    @Test
    public void testGetFilmsWithoutFiltersOk() throws Exception, SWAPIException {
        // Mock objs
        List<FilmDTO> filmList = new ArrayList<>();
        filmList.add(getFristFilmDTO());
        filmList.add(getSecondFilmDTO());
        int page = 1;
        int size = 10;
        PageResult<FilmDTO> pageResult = new PageResult<>(filmList, filmList.size(), page, size);

        // Comportamiento de mocks
        when(filmService.findAll(page, size, null, null)).thenReturn(pageResult);

        // Ejecucion metodo a testear
        ResponseEntity<PageResult<FilmDTO>> result = this.filmController.findAll(page, size, null, null);

        // Assertions
        assertNotNull(result);
        assertEquals(pageResult, result.getBody());
        verify(filmService, times(1)).findAll(page, size, null, null);

        // Llamada al endpoint con HttpStatus esperado
        mockMvc.perform(get("/film")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetFilmsWithTitleAndIdFiltersOk() throws Exception, SWAPIException {
        // Mock objs
        List<FilmDTO> filmList = new ArrayList<>();
        FilmDTO film = getFristFilmDTO();
        filmList.add(film);
        int page = 1;
        int size = 10;
        long id = 1L;
        String title = "Attack";
        PageResult<FilmDTO> pageResult = new PageResult<>(filmList, filmList.size(), page, size);

        // Comportamiento de mocks
        when(filmService.findAll(page, size, id, title)).thenReturn(pageResult);

        // Ejecucion metodo a testear
        ResponseEntity<PageResult<FilmDTO>> result = this.filmController.findAll(page, size, id, title);

        // Assertions
        assertNotNull(result);
        assertEquals(pageResult, result.getBody());
        verify(filmService, times(1)).findAll(page, size, id, title);

        // Llamada al endpoint con HttpStatus esperado
        mockMvc.perform(get("/film")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("id", String.valueOf(id))
                .param("title", title)
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetFilmsWithoutFiltersInternalServerError() throws SWAPIException, Exception {
        // Mock objs
        int page = 1;
        int size = 10;

        // Comportamiento de mocks
        when(filmService.findAll(page, size, null, null))
                .thenThrow(new SWAPIException("External API error"));

        // Ejecucion metodo a testear
        assertThrows(SWAPIException.class, () ->
                filmController.findAll(1, 10, null, null)
        );

        // Assertions
        verify(filmService, times(1)).findAll(page, size, null, null);

        // Llamada al endpoint con HttpStatus esperado
        mockMvc.perform(get("/film")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'code':3312,'errorType':'EXTERNAL_API','message':'External API error'}"));
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
}
