package com.conexia.starwars.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseEntity {
    private Long id;
    private String url;

    @JsonProperty("uid")
    public void setUid(Long id) {
        this.id = id;
    }
}
