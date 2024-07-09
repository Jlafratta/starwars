package com.conexia.starwars.integration;

import com.conexia.starwars.domain.dto.PeopleDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.service.SWAPIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-tests.yml")
public class SWAPIServiceIntegrationTests {

    @Autowired
    private SWAPIService swapiService;

    @Test
    public void findAll() {
        // mocks
        String resource = "/people";
        int page = 1;
        int size = 10;
        Map<String, String> filters = new HashMap<>();

        // Ejecucion metodo a testear
        PageResult<PeopleDTO> result = swapiService.findAll(resource, PeopleDTO[].class, page, size, filters);

        // Verifica que el resultado no sea nulo y que contenga datos
        assertNotNull(result);
        assertNotNull(result.getRows());
        assertTrue(result.getTotal() > 0);
    }
}
