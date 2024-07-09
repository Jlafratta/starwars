package com.conexia.starwars.domain.dto.swapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SWAPIResponse {
    private String message;
    private String next;
    private String previous;
    @JsonProperty("total_records")
    private long totalRecords;
    @JsonProperty("total_pages")
    private Integer totalPages;
    private List<Object> results;

    /*
    Obtengo las properties de las entidades a mapear independientemente de si vienen en results (listado sin filtros)
    o en result (listado filtrado). De esta forma abstraigo toda logica proveniente de la diferencia entre campos y hago
    un solo mapeo
     */
    @JsonProperty("result")
    public void setResult(List<SWAPIResult> result) {
        setResults(new ArrayList<>());
        getResults().addAll(result.stream().map(SWAPIResult::getProperties).collect(Collectors.toList()));
    }
}
