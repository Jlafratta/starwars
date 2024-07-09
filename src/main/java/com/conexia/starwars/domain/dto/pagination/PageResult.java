package com.conexia.starwars.domain.dto.pagination;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PageResult<T> {
    Iterable<T> rows;
    long total;
    long currentPage;
    long totalPages;

    /*
    Manejo la paginacion para los casos en los que los resultados de SWAPI no cuentan con la misma.
    De esta forma me aseguro que todas las requests salgan paginadas
     */
    public PageResult(List<T> rows, long total, int page, int pageSize) {
        if (total == 0) {
            int totalRows = rows.size();
            int start = (page - 1) * pageSize;
            int end = Math.min(pageSize + start, totalRows);
            List<T> paginatedRows = (start < end) ? rows.subList(start, end) : Collections.emptyList();
            setTotal(totalRows);
            setRows(paginatedRows);
        } else {
            setTotal(total);
            setRows(rows);
        }
        setCurrentPage(page);
        setTotalPages((getTotal() + pageSize - 1) / pageSize);
    }
}
