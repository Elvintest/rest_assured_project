package ru.atc.fgislk.ppod.testcore.indocjournal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

/**
 * Страница выдачи информации
 */
@Getter
@Setter
@Builder
public class PageResponseRecord {
    @Singular("oneContent")
    private List<Record> content = null;
    private Long totalElements = null;
    private PageableObject pageable = null;
    private Sort sort = null;
    private Boolean first = null;
    private Boolean last = null;
    private Integer number = null;
    private Integer numberOfElements = null;
    private Integer totalPages = null;
    private Integer size = null;
    private Boolean empty = null;
}
