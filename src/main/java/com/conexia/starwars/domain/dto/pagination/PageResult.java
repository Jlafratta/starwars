package com.conexia.starwars.domain.dto.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResult <T>{
    Iterable<T> rows;
    long total;
    long currentPage;
    long totalPages;

    public PageResult(Iterable<T> rows, long total, int page, int pageSize) {
        setRows(rows);
        setTotal(total);
        setCurrentPage(page);
        setTotalPages((total + pageSize - 1) / pageSize);
    }
}
