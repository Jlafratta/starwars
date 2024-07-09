package com.conexia.starwars.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean("SWAPIRestTemplate")
    public RestTemplate SWAPIRestTemplate() {
        return new RestTemplate();
    }

    @Bean()
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
