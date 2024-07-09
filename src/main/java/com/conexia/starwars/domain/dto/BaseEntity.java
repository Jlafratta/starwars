package com.conexia.starwars.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {
    private Long id;
    private String name;
    private String url;
}
