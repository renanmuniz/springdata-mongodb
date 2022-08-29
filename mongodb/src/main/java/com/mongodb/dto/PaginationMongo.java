package com.mongodb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.entity.Arrangement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationMongo {

    private Integer count;
    private Long total;
    private Long perPage;
    private Long pages;
    private Integer currentPage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Arrangement> arrangements;

    public static PaginationMongo createPage(final List<Arrangement> arrangements,
                                              final Integer page,
                                              final Integer pageSize,
                                              final Long totalOfElements) {
        final PaginationMongo pagination = new PaginationMongo();
        pagination.setPerPage(pageSize == 0 ? totalOfElements : Long.valueOf(pageSize));
        pagination.setPages(getTotalPages(pageSize, totalOfElements));
        pagination.setCurrentPage(page);
        pagination.setCount(arrangements.size());
        pagination.setTotal(totalOfElements);
        pagination.setArrangements(arrangements);
        return pagination;
    }

    private static Long getTotalPages(final Integer pageSize, final Long totalOfElements) {
        if (totalOfElements == 0) {
            return 0L;
        }
        if (pageSize == 0) {
            return 1L;
        }
        var result = totalOfElements % pageSize;
        if(result == 0) {
            return totalOfElements / pageSize;
        }
        return ((totalOfElements - result) / pageSize) + 1;
    }

}