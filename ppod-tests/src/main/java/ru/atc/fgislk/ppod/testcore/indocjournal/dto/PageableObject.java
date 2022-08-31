package ru.atc.fgislk.ppod.testcore.indocjournal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * информация о постраничной выдаче
 */
@Getter
@Setter
@Builder
public class PageableObject {
    private Sort sort = null;
    private Integer pageNumber = null;
    private Integer pageSize = null;
    private Boolean paged = null;
    private Boolean unpaged = null;
    private Long offset = null;
}
