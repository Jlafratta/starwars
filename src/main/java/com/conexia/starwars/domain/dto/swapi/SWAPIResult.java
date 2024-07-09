package com.conexia.starwars.domain.dto.swapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
public class SWAPIResult {
    private LinkedHashMap<String, Object> properties;

    @JsonProperty("uid")
    public void setUid(String id) {
        getProperties().put("uid", id);
    }
}
