package com.conexia.starwars.service;

import com.conexia.starwars.domain.dto.PeopleDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.domain.dto.swapi.SWAPIResponse;
import com.conexia.starwars.exception.SWAPIException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-tests.yml")
public class SWAPIServiceTests {

    @MockBean
    @Qualifier("SWAPIRestTemplate")
    private RestTemplate restTemplate;

    @MockBean
    private ObjectMapper objectMapper;

    @Value("${swapi.url}")
    private String apiUrl;

    @InjectMocks
    private SWAPIService swapiService;

    @BeforeEach
    public void setUp() {
        this.swapiService = new SWAPIService(apiUrl, restTemplate, objectMapper);
    }

    @Test
    public void testFindAllOk() {
        // Mock obj api response
        HashMap<String, String> json = new LinkedHashMap<>();
        json.put("uid", "1");
        json.put("name", "name test");
        json.put("url", "url test");
        SWAPIResponse response = new SWAPIResponse();
        response.setResults(Collections.singletonList(json));
        response.setTotalRecords(1);

        // Mock restTemplate
        ResponseEntity<SWAPIResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(SWAPIResponse.class)))
                .thenReturn(responseEntity);

        // Mock objectMapper
        PeopleDTO entity = new PeopleDTO();
        entity.setUid(1L);
        entity.setName("name test");
        entity.setUrl("url test");
        PeopleDTO[] entities = new PeopleDTO[1];
        entities[0] = entity;
        when(objectMapper.convertValue(eq(response.getResults()), eq(PeopleDTO[].class)))
                .thenReturn(entities);

        // Ejecucion metodo a testear
        PageResult<PeopleDTO> result = swapiService.findAll("/people", PeopleDTO[].class, 1, 10, new HashMap<>());

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getCurrentPage());
        verify(restTemplate, times(1))
                .exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(SWAPIResponse.class));
        verify(objectMapper, times(1))
                .convertValue(eq(response.getResults()), eq(PeopleDTO[].class));
    }

    @Test
    public void testFindAllError() {
        // Mock restTemplate
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(SWAPIResponse.class)))
                .thenThrow(new SWAPIException("External API error"));

        // Ejecucion metodo a testear
        assertThrows(SWAPIException.class, () ->
                swapiService.findAll("/people", PeopleDTO[].class, 1, 10, new HashMap<>())
        );

        // Assertions
        verify(restTemplate, times(1))
                .exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(SWAPIResponse.class));
    }

    @Test
    public void testProcessUnsupportedFilters_WithFiltersOk() {
        // Mock objs
        List<PeopleDTO> rows = new ArrayList<>();
        PeopleDTO dto = new PeopleDTO();
        PeopleDTO dto2 = new PeopleDTO();
        PeopleDTO dto3 = new PeopleDTO();

        dto.setId(1L);
        dto2.setId(2L);
        dto3.setId(3L);

        rows.add(dto);
        rows.add(dto2);
        rows.add(dto3);
        // mock filters
        Map<String, String> filters = new HashMap<>();
        filters.put("id", "2");

        // Ejecucion metodo a testear
        List<PeopleDTO> result = swapiService.processUnsupportedFilters(rows, filters);

        // Assertions
        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getId());
    }

    @Test
    public void testProcessUnsupportedFilters_WithoutFiltersOk() {
        // Mock objs
        List<PeopleDTO> rows = new ArrayList<>();
        PeopleDTO dto = new PeopleDTO();
        PeopleDTO dto2 = new PeopleDTO();
        PeopleDTO dto3 = new PeopleDTO();

        dto.setId(1L);
        dto2.setId(2L);
        dto3.setId(3L);

        rows.add(dto);
        rows.add(dto2);
        rows.add(dto3);
        // mock filters
        Map<String, String> filters = new HashMap<>();

        // Ejecucion metodo a testear
        List<PeopleDTO> result = swapiService.processUnsupportedFilters(rows, filters);

        // Assertions
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
    }

}
